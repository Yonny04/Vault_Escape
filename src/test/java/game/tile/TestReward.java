package game.tile;

import game.object.Vector;
import game.panel.GamePanel;
import game.tile.entity.reward.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestReward {

    Valuable valuable;
    Diamond diamond;
    GamePanel gp = new GamePanel(null, 1);

    @BeforeEach
    public void setUp() {
        valuable = new Valuable(gp,new Vector());
        diamond = new Diamond(gp,new Vector());
    }

    @Test
    public void testGetScore() {
        assertEquals(25, valuable.getPoints());
        assertEquals(200, diamond.getPoints());
    }
}
