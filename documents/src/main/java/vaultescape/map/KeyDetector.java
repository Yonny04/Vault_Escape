package vaultescape.map;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Detects keyboard input for movement controls, specifically for 'W', 'A', 'S', and 'D' keys.
 * Updates the boolean values representing the current state (pressed or released) of each key.
 */
public class KeyDetector implements KeyListener {

    public boolean w, a, s, d; // Movement flags for 'W', 'A', 'S', and 'D' keys

    /**
     * This method is required by the KeyListener interface but is not used in this implementation.
     *
     * @param e the key event triggered when a key is typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    /**
     * Updates the movement flags when a key is pressed. Sets the corresponding flag to true
     * if the pressed key is 'W', 'A', 'S', or 'D'.
     *
     * @param e the key event triggered when a key is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) w = true;
        if (key == KeyEvent.VK_A) a = true;
        if (key == KeyEvent.VK_S) s = true;
        if (key == KeyEvent.VK_D) d = true;
    }

    /**
     * Updates the movement flags when a key is released. Sets the corresponding flag to false
     * if the released key is 'W', 'A', 'S', or 'D'.
     *
     * @param e the key event triggered when a key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) w = false;
        if (key == KeyEvent.VK_A) a = false;
        if (key == KeyEvent.VK_S) s = false;
        if (key == KeyEvent.VK_D) d = false;
    }
}
