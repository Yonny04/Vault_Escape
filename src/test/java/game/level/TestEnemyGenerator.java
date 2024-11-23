package game.level;

import game.panel.GamePanel;
import game.tile.entity.character.enemy.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EnemyGenerator
 */
class TestEnemyGenerator {

    private EnemyGenerator enemyGenerator;
    private GamePanel gamePanel;

    @BeforeEach
    void reset() {
        gamePanel = new GamePanel(null, 1);
        enemyGenerator = new EnemyGenerator(gamePanel);
    }

    @Test
    void testSpawnEnemies() {
        enemyGenerator.spawn(Guard.class, 5);
        List<Enemy> enemies = enemyGenerator.getEnemies();
        assertEquals(5, enemies.size());
        for (Enemy enemy : enemies) {
            assertTrue(enemy instanceof Guard);
        }
    }

    @Test
    void testUpdateEnemies() {
        enemyGenerator.spawn(Guard.class, 3);
        enemyGenerator.update();
        List<Enemy> enemies = enemyGenerator.getEnemies();
        for (Enemy enemy : enemies) {
            assertNotNull(enemy);
        }
    }

    @Test
    void testAddSpeed() {
        enemyGenerator.spawn(Guard.class, 3);
        enemyGenerator.addEnemySpeed(3);
        for (Enemy enemy : enemyGenerator.getEnemies()) {
            assertEquals(5, enemy.getSpeed());
        }
    }

    @Test
    void testGetEnemies() {
        enemyGenerator.spawn(Guard.class, 2);
        List<Enemy> enemies = enemyGenerator.getEnemies();
        assertEquals(2, enemies.size());
        for (Enemy enemy : enemies) {
            assertNotNull(enemy);
        }
    }
}
