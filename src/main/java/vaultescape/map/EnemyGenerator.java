package vaultescape.map;

import vaultescape.entity.Player;
import vaultescape.entity.enemy.*;
import vaultescape.ui.GamePanel;

import java.util.List;

/**
 * Generates and manages all enemies within the game, including Dogs, Guards, and Cameras.
 * Provides methods to initialize, update, draw, and check interactions between enemies and the player.
 */
public class EnemyGenerator {
    GamePanel gp;
    TileGenerator tg;
    public Generator<Enemy> generator;

    /**
     * Constructs an EnemyGenerator with a specified game panel.
     *
     * @param gp the game panel associated with this generator
     */
    public EnemyGenerator(GamePanel gp) {
        this.gp = gp;
        this.tg = gp.getTileGenerator();
        this.generator = new Generator<>(gp);
    }

    /**
     * Spawn a specified number of basic rewards at random available tile positions.
     *
     * @param n the number of basic rewards to generate
     */
    public void spawn(Class<? extends Enemy> type, int count) {
        generator.spawn(type, count);
    }

    /**
     * Generates all types of enemies with specified counts for each type.
     *
     * @param guardsCount the number of Guards to generate
     * @param dogsCount the number of Dogs to generate
     * @param cameraCount the number of Cameras to generate
     */
    public void spawnAll(int guardsCount, int dogsCount, int cameraCount) {
        spawn(Guard.class, guardsCount);
        spawn(Dog.class, dogsCount);
        spawn(Camera.class, cameraCount);
    }

    /**
     * Updates each enemy's state and checks for collisions with the player.
     *
     * @param player the player entity to check collisions against
     */
    public void update(Player player) {
        generator.update();
        checkEnemyCollision();
    }

    /**
     * Adds the speed of certain enemies by a bonus value.
     *
     * @param value the value by which to add the speed of each applicable enemy
     */
    public void addEnemySpeed(int value) {
        for (Enemy enemy : generator.elements) {
            if (enemy instanceof Guard || enemy instanceof Dog) {
                enemy.addSpeed(value);
            }
        }
    }

    /**
     * Checks for collisions between the player and each enemy, applying penalties or effects as needed.
     *
     * @param player the player entity to check collisions against
     */
    public void checkEnemyCollision() {
        for (Enemy enemy : generator.elements) {
            if (enemy.isTouchingPlayer() && enemy.canAttack()) {
                enemy.attack();
            }
        }
    }

    /**
     * Retrieves the list of all generated enemies.
     *
     * @return a list of enemies
     */
    public List<Enemy> getEnemies() {
        return generator.elements;
    }
}
