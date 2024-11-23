package game.utils;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class KeyDetectorTest {

    private JPanel mockComponent = new JPanel();  

    @Test
    public void testKeyPressAndReleaseWASD() {
        KeyDetector keyh = new KeyDetector();
        mockComponent.addKeyListener(keyh);
        mockComponent.setFocusable(true); 

        KeyEvent pressW = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        KeyEvent releaseW = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');

        keyh.keyPressed(pressW);
        assertTrue(keyh.up);

        keyh.keyReleased(releaseW);
        assertFalse(keyh.up);

        KeyEvent pressA = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        KeyEvent releaseA = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');

        keyh.keyPressed(pressA);
        assertTrue(keyh.left);

        keyh.keyReleased(releaseA);
        assertFalse(keyh.left);

        KeyEvent pressS = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        KeyEvent releaseS = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');

        keyh.keyPressed(pressS);
        assertTrue(keyh.down);

        keyh.keyReleased(releaseS);
        assertFalse(keyh.down);

        KeyEvent pressD = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        KeyEvent releaseD = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');

        keyh.keyPressed(pressD);
        assertTrue(keyh.right);

        keyh.keyReleased(releaseD);
        assertFalse(keyh.right);
    }


    @Test
    public void testKeyPressAndReleaseArrow() {
        KeyDetector keyh = new KeyDetector();
        mockComponent.addKeyListener(keyh);
        mockComponent.setFocusable(true); 

        KeyEvent pressUp = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' ');
        KeyEvent releaseUp = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' ');

        keyh.keyPressed(pressUp);
        assertTrue(keyh.up);

        keyh.keyReleased(releaseUp);
        assertFalse(keyh.up);

        KeyEvent pressLeft = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, ' ');
        KeyEvent releaseLeft = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, ' ');

        keyh.keyPressed(pressLeft);
        assertTrue(keyh.left);

        keyh.keyReleased(releaseLeft);
        assertFalse(keyh.left);

        KeyEvent pressDown = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, ' ');
        KeyEvent releaseDown = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, ' ');

        keyh.keyPressed(pressDown);
        assertTrue(keyh.down);

        keyh.keyReleased(releaseDown);
        assertFalse(keyh.down);

        KeyEvent pressRight = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, ' ');
        KeyEvent releaseRight = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, ' ');

        keyh.keyPressed(pressRight);
        assertTrue(keyh.right);

        keyh.keyReleased(releaseRight);
        assertFalse(keyh.right);
    }
}
