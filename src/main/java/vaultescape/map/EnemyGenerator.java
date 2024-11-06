package vaultescape.map;

import vaultescape.entity.Player;
import vaultescape.entity.enemy.*;
import vaultescape.ui.GamePanel;
import vaultescape.utils.Vector2;

import java.awt.Graphics2D;
import java.util.*;

/**
 * Generates and manages all enemies within the game, including Dogs, Guards, and Cameras.
 * Provides methods to initialize, update, draw, and check interactions between enemies and the player.
 */
public class EnemyGenerator {
    private final GamePanel gp;
    private final TileGenerator tg;
    private final List<Enemy> enemies;
    private final Random random;

    /**
     * Constructs an EnemyGenerator with a specified game panel.
     *
     * @param gp the game panel associated with this generator
     */
    public EnemyGenerator(GamePanel gp) {
        this.gp = gp;
        this.tg = gp.getTileGenerator();
        this.enemies = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Generates a specified number of enemies of a certain type and places them on available tiles.
     *
     * @param enemyType the class type of the enemy to generate
     * @param count the number of enemies to generate
     */
    private void generateEnemies(Class<? extends Enemy> enemyType, int count) {
        List<Vector2> availableTiles = new ArrayList<>(tg.availableTiles);

        for (int i = 0; i < count && !availableTiles.isEmpty(); i++) {
            int index = random.nextInt(availableTiles.size());
            Vector2 start = availableTiles.remove(index);

            try {
                Enemy enemy;
                if (enemyType == Guard.class) {
                    int range = gp.TILE_SIZE.x * 3 + random.nextInt(251);
                    boolean isHorizontal = random.nextBoolean();
                    Vector2 end = new Vector2(0, 0);
                    end.x = isHorizontal ? start.x + range : start.x;
                    end.y = isHorizontal ? start.y : start.y + range;
                    end.x = Math.min(end.x, gp.MAP_SIZE.x - gp.TILE_SIZE.x);
                    end.y = Math.min(end.y, gp.MAP_SIZE.y - gp.TILE_SIZE.y);
                    enemy = new Guard(gp, start, end);
                } else if (enemyType == Dog.class) {
                    enemy = new Dog(gp, start);
                } else if (enemyType == Camera.class) {
                    enemy = new Camera(gp, start, 100);
                } else {
                    continue;
                }
                enemies.add(enemy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Generates all types of enemies with specified counts for each type.
     *
     * @param guardsCount the number of Guards to generate
     * @param dogsCount the number of Dogs to generate
     * @param cameraCount the number of Cameras to generate
     */
    public void generateAllEnemies(int guardsCount, int dogsCount, int cameraCount) {
        generateEnemies(Guard.class, guardsCount);
        generateEnemies(Dog.class, dogsCount);
        generateEnemies(Camera.class, cameraCount);
    }

    /**
     * Updates each enemy's state and checks for collisions with the player.
     *
     * @param player the player entity to check collisions against
     */
    public void update(Player player) {
        checkEnemyCollision(player);
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    /**
     * Increases the speed of certain enemies by a bonus value.
     *
     * @param bonusSpeed the value by which to add the speed of each applicable enemy
     */
    public void increaseEnemySpeed(int bonusSpeed) {
        for (Enemy enemy : enemies) {
            if (enemy instanceof Guard || enemy instanceof Dog) {
                enemy.setSpeed(enemy.getSpeed() + bonusSpeed);
            }
        }
    }

    /**
     * Checks for collisions between the player and each enemy, applying penalties or effects as needed.
     *
     * @param player the player entity to check collisions against
     */
    public void checkEnemyCollision(Player player) {
        for (Enemy enemy : enemies) {
            if (enemy instanceof Guard guard) {
                if (guard.isTouching(player) && guard.canCollide()) {
                    gp.getTimer().decreaseTime(5);
                    gp.getSFX().play("hit");
                    guard.recordCollision();
                }
            }
            if (enemy instanceof Dog dog) {
                if (dog.isTouching(player) && dog.canCollide()) {
                    gp.getTimer().decreaseTime(3);
                    gp.getSFX().play("hit");
                    dog.recordCollision();
                }
            }
            if (enemy instanceof Camera camera) {
                if (camera.isPlayerInRange() && camera.canCollide()) {
                    increaseEnemySpeed(1);
                    gp.getSFX().play("alarm");
                    camera.recordCollision();
                }
            }
        }
    }

    /**
     * Draws each enemy on the screen.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    public void drawEnemies(Graphics2D g2) {
        for (Enemy enemy : enemies) {
            enemy.draw(g2);
        }
    }

    /**
     * Retrieves the list of all generated enemies.
     *
     * @return a list of enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
