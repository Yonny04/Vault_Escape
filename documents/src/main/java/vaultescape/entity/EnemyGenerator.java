package vaultescape.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import vaultescape.map.GamePanel;
import vaultescape.map.TileGenerator;

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
        List<int[]> availableTiles = new ArrayList<>(tg.availableTiles);

        for (int i = 0; i < count && !availableTiles.isEmpty(); i++) {
            int index = random.nextInt(availableTiles.size());
            int[] start = availableTiles.remove(index);

            try {
                Enemy enemy;
                if (enemyType == Guards.class) {
                    int range = 200 + random.nextInt(251);
                    boolean isHorizontal = random.nextBoolean();
                    int x2 = isHorizontal ? start[0] + range : start[0];
                    int y2 = isHorizontal ? start[1] : start[1] + range;
                    x2 = Math.min(x2, gp.mapWidth - gp.tilesize);
                    y2 = Math.min(y2, gp.mapHeight - gp.tilesize);
                    enemy = new Guards(gp, start[0], start[1], x2, y2);
                } else if (enemyType == Dog.class) {
                    enemy = new Dog(gp, start[0], start[1]);
                } else if (enemyType == Camera.class) {
                    enemy = new Camera(gp, start[0], start[1], 100);
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
        generateEnemies(Guards.class, guardsCount);
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
     * Increases the speed of certain enemies by a specified multiplier.
     *
     * @param multiplier the value by which to multiply the speed of each applicable enemy
     */
    public void increaseEnemySpeed(double multiplier) {
        for (Enemy enemy : enemies) {
            if (enemy instanceof Guards || enemy instanceof Dog) {
                enemy.setSpeed(enemy.getSpeed() * multiplier);
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
            if (enemy instanceof Guards guard) {
                if (guard.isTouching(player) && guard.canCollide()) {
                    gp.getTimer().decreaseTime(6);
                    guard.recordCollision();
                }
            }
            if (enemy instanceof Dog dog) {
                if (dog.isTouching(player) && dog.canCollide()) {
                    dog.freezeDog(1);
                    gp.getTimer().decreaseTime(4);
                    dog.recordCollision();
                }
            }
            if (enemy instanceof Camera camera) {
                if (camera.isPlayerInRange() && camera.camDetect()) {
                    increaseEnemySpeed(1.03);
                    camera.recordDetection();
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
