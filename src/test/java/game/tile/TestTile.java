package game.tile;

import game.object.Vector;
import game.panel.GamePanel;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTile {

    GamePanel gp;
    Tile tile;

    @BeforeEach
    public void setUp() {
        new GamePanel(null,1);
        tile = new Tile(gp);
    }

    @Test
    public void testGetScreenPosition() {
        tile.getScreenPosition().setPosition(new Vector(1,1));
        assertTrue(tile.getScreenPosition().equals(new Vector(1,1)));
    }

    @Test
    public void testGetCenter() {
        Vector center = tile.getCenter();
        assertTrue(tile.getRect().add(tile.getSize().scale(0.5)).equals(center));
    }

    @Test
    public void testIsAbove() {
        Tile tile2 = new Tile(gp);
        tile.setPosition(1,0); //above tile2
        tile2.setPosition(1,1);
        assertTrue(tile.isAbove(tile2));
    }
}
