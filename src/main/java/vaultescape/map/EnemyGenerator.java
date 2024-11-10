package vaultescape.map;

import vaultescape.entity.character.Player;
import vaultescape.entity.character.enemy.*;
import vaultescape.ui.GamePanel;

import java.util.List;

/**
 * The EnemyGenerator class is responsible for generating and managing enemies in the game.
 */
public class EnemyGenerator {
    GamePanel gp;
    TileGenerator tg;
    public Generator<Enemy> generator;

    /**
     * Constructs an EnemyGenerator with the specified game panel.
     *
     * @param gp The game panel associated with this enemy generator.
     */
    public EnemyGenerator(GamePanel gp) {
        this.gp = gp;
        this.tg = gp.getTileGenerator();
        this.generator = new Generator<>(gp);
    }

    /**
     * Spawns a specified number of enemies of the given type.
     *
     * @param type  The class type of the enemies to spawn.
     * @param count The number of enemies to spawn.
     */
    public void spawn(Class<? extends Enemy> type, int count) {
        generator.spawn(type, count);
    }

    /**
     * Spawns the specified number of guards, dogs, and cameras.
     *
     * @param guardsCount The number of guards to spawn.
     * @param dogsCount   The number of dogs to spawn.
     * @param cameraCount The number of cameras to spawn.
     */
    public void spawnAll(int guardsCount, int dogsCount, int cameraCount) {
        spawn(Guard.class, guardsCount);
        spawn(Dog.class, dogsCount);
        spawn(Camera.class, cameraCount);
    }

    /**
     * Updates the state of all enemies and checks for collisions with the player.
     *
     * @param player The player object to check for collisions.
     */
    public void update(Player player) {
        generator.update();
        checkEnemyCollision();
    }

    /**
     * Increases the speed of all guards and dogs by the specified value.
     *
     * @param value The value to add to the speed of each guard and dog.
     */
    public void addEnemySpeed(int value) {
        for (Enemy enemy : generator.elements) {
            if (enemy instanceof Guard || enemy instanceof Dog) {
                enemy.addSpeed(value);
            }
        }
    }

    /**
     * Checks if any enemies are touching the player and, if so, allows them to attack.
     */
    public void checkEnemyCollision() {
        for (Enemy enemy : generator.elements) {
            if (enemy.isTouchingPlayer() && enemy.canAttack()) {
                enemy.attack();
            }
        }
    }

    /**
     * Returns a list of all enemies managed by this generator.
     *
     * @return A list of all enemies.
     */
    public List<Enemy> getEnemies() {
        return generator.elements;
    }
}
