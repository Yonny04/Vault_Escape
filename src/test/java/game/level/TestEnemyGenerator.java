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
    private GamePanel gp;

    @BeforeEach
    public void reset() {
        gp = new GamePanel(null, 1);
        enemyGenerator = new EnemyGenerator(gp);
    }

    @Test
    public void testSpawnGuards() {
        enemyGenerator.spawn(Guard.class, 5);
        List<Enemy> enemies = enemyGenerator.getEnemies();
        assertEquals(5, enemies.size());
        for (Enemy enemy : enemies) {
            assertTrue(enemy instanceof Guard);
        }
    }

    @Test
    public void testSpawnDogs() {
        enemyGenerator.spawn(Dog.class, 2);
        List<Enemy> enemies = enemyGenerator.getEnemies();
        assertEquals(2, enemies.size());
        for (Enemy enemy : enemies) {
            assertTrue(enemy instanceof Dog);
        }
    }

    @Test
    public void testSpawnCameras() {
        enemyGenerator.spawn(Camera.class, 3);
        List<Enemy> enemies = enemyGenerator.getEnemies();
        assertEquals(3, enemies.size());
        for (Enemy enemy : enemies) {
            assertTrue(enemy instanceof Camera);
        }
    }

    @Test
    public void testSpawnLasers() {
        enemyGenerator.spawn(Laser.class, 4);
        List<Enemy> enemies = enemyGenerator.getEnemies();
        assertEquals(4, enemies.size());
        for (Enemy enemy : enemies) {
            assertTrue(enemy instanceof Laser);
        }
    }

    @Test
    public void testUpdateEnemies() {
        enemyGenerator.spawn(Guard.class, 3);
        enemyGenerator.update();
        List<Enemy> enemies = enemyGenerator.getEnemies();
        for (Enemy enemy : enemies) {
            assertNotNull(enemy);
        }
    }

    @Test
    public void testAddSpeed() {
        enemyGenerator.spawn(Guard.class, 3);
        enemyGenerator.addEnemySpeed(3);
        for (Enemy enemy : enemyGenerator.getEnemies()) {
            assertEquals(5, enemy.getSpeed());
        }
    }

    @Test
    public void testGetEnemies() {
        enemyGenerator.spawn(Guard.class, 2);
        List<Enemy> enemies = enemyGenerator.getEnemies();
        assertEquals(2, enemies.size());
        for (Enemy enemy : enemies) {
            assertNotNull(enemy);
        }
    }
}
