package game.level;

import game.panel.GamePanel;
import game.tile.entity.character.Player;
import game.tile.entity.character.enemy.Enemy;

import java.util.List;

/**
 * The EnemyGenerator class is responsible for generating and managing enemies in the game.
 */
public class EnemyGenerator {
    GamePanel gp;
    public Generator<Enemy> generator;

    /**
     * Constructs an EnemyGenerator with the specified game panel.
     *
     * @param gp The game panel associated with this enemy generator.
     */
    public EnemyGenerator(GamePanel gp) {
        this.gp = gp;
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
     * Updates the state of all enemies and checks for collisions with the player.
     *
     * @param player The player object to check for collisions.
     */
    public void update(Player player) {
        generator.update();
    }

    /**
     * Increases the speed of all guards and dogs by the specified value.
     *
     * @param value The value to add to the speed of each guard and dog.
     */
    public void addEnemySpeed(int value) {
        for (Enemy enemy : generator.elements) {
            enemy.addSpeed(value);
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
