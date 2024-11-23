package game.tile;

import game.App;
import game.object.Vector;
import game.panel.GamePanel;
import game.tile.entity.Exit;
import game.tile.entity.character.Character.Direction;
import game.tile.entity.character.Player;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class TestCharacter {

    static App app = new App(false);
    Player player;
    Exit exit;

    GamePanel gp;

    @BeforeEach
    public void setUp() {
        gp = new GamePanel(app, 1);
        player = gp.getPlayer();
        exit = gp.getTileManager().exit;
    }

    @Test
    public void testPlayerCanEscapeTrue() {
        exit.update();
        player.setPosition(exit.getPosition());
        assertTrue(player.canEscape());
    }

    @Test
    public void testPlayerCanEscapeFalse() {
        assertFalse(player.canEscape());
    }

    @Test
    public void testPlayerShakeUpdateCamera() throws Exception {
        Field introFade = GamePanel.class.getDeclaredField("introFade");
        introFade.setAccessible(true);
        introFade.set(gp, 0);

        player.shakeCamera();
        player.update();
    }

    @Test
    public void testPlayerMove() {
        player.setPosition(new Vector());
        player.move(Direction.DOWN);
        assertEquals(0, player.getPosition().x);
        assertEquals(player.getSpeed(), player.getPosition().y);
    }
}
