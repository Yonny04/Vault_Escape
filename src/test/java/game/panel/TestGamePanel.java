package game.panel;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestGamePanel {
    
    GamePanel gp;

    @BeforeEach 
    public void setUp() {
        gp = new GamePanel(null,1);
    }

    @Test
    public void testGetTimer() {
        assertNull(gp.getTimer());
    }

    @Test
    public void testIsRunning() {
        gp.startGameThread();
        assertTrue(gp.isRunning());
    }

    @Test
    public void testGetRewardGenerator() {
        assertNotNull(gp.getRewardGenerator());
    }

    @Test
    public void testGetEnemyGenerator() {
        assertNotNull(gp.getEnemyGenerator());
    }

    @Test
    public void testGetTotalScore() {
        gp.startGameThread();
        gp.getTimer().start();
        assertEquals(((gp.level.TIME_LIMIT)*30+100)+0, gp.getTotalScore());
    }

    @Test
    public void testAddLevelScore() {
        gp.startGameThread();
        gp.getTimer().start();
        gp.addLevelScore(100);
        assertEquals(((gp.level.TIME_LIMIT)*30+100)+100, gp.getTotalScore());
    }

    @Test
    public void testGetPlayer() {
        assertNotNull(gp.getPlayer());
    }

}
