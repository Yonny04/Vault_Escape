package game.level;

import game.object.Vector;
import game.panel.GamePanel;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestTileManager {
    TileManager tm;
    GamePanel gp;

    @BeforeEach
    public void setUp() {
        gp = new GamePanel(null,1);
        tm = gp.getTileManager();
    }

    @Test
    public void testGetEmptyTile() {
        assertNotNull(tm.getEmptyTile());
    }

    @Test
    public void testNextEmptyTile() {
        // should remove the tile from the list
        Vector v = tm.nextEmptyTile();
        assertNotNull(v);
        assertNull(tm.getWallTiles().get(v.getUnitString())); // should be null
    }

    @Test
    public void testNextCameraTile() {
        Vector v = tm.nextCameraTile();
        assertNotNull(v);
        assertNotNull(tm.getWallTiles().get(v.getUnitString()));
    }

    @Test
    public void testNextLaserTile() {
        Vector v = tm.nextLaserTile();
        assertNotNull(v);
        assertNotNull(tm.getWallTiles().get(v.getUnitString()));
    }

    @Test
    public void testGetScreenTiles() {
        assertNotNull(tm.getScreenTiles());
    }
    @Test
    public void testGetFloorTiles() {
        assertNotNull(tm.getFloorTiles());
    }
}
