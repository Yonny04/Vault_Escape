package game.tile;

import game.App;
import game.object.Vector;
import game.panel.GamePanel;
import game.tile.entity.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEntity {

    Entity entity;
    App app = new App(false);
    GamePanel gp;
    Exit exit;

    @BeforeEach
    public void setUp() {
        gp = new GamePanel(app,1);
        entity = new Entity(gp, new Vector());
        exit = new Exit(gp, new Vector());
    }

    @Test
    public void testHasShadow() {
        assertTrue(entity.hasShadow());
    }

    @Test
    public void testExitInit() {
        assertTrue(exit.getAnimationPlayer().getFrame() == 0);
    }

    @Test
    public void testExitUpdateOpen() {
        exit.update();
        assertTrue(exit.getAnimationPlayer().getFrame() == 1);
    }

    @Test
    public void testExitOpenExit() {
        exit.openExit();
        assertTrue(exit.getAnimationPlayer().getFrame() == 1);
    }
}
