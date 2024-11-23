package game.panel;

import game.App;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestGamePanel {
    
    static App app = new App(false);
    GamePanel gp = new GamePanel(app,1);

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
