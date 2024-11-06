package vaultescape.utils;

import java.awt.event.*;

/**
 * Detects keyboard input for movement controls, specifically for 'W', 'A', 'S', and 'D' keys.
 * Updates the boolean values representing the current state (pressed or released) of each key.
 */
public class KeyDetector implements KeyListener {

    public boolean up, left, down, right; // Movement flags for 'W', 'A', 'S', and 'D' keys

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
        if (key == KeyEvent.VK_UP) up = true;
        if (key == KeyEvent.VK_LEFT) left = true;
        if (key == KeyEvent.VK_DOWN) down = true;
        if (key == KeyEvent.VK_RIGHT) right = true;

        if (key == KeyEvent.VK_W) up = true;
        if (key == KeyEvent.VK_A) left = true;
        if (key == KeyEvent.VK_S) down = true;
        if (key == KeyEvent.VK_D) right = true;
    }

    /**
     * Updates the movement flags when a key is released. Sets the corresponding flag to false
     * if the released key is 'up', 'left', 'down', or 'right'.
     *
     * @param e the key event triggered when a key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) up = false;
        if (key == KeyEvent.VK_LEFT) left = false;
        if (key == KeyEvent.VK_DOWN) down = false;
        if (key == KeyEvent.VK_RIGHT) right = false;

        if (key == KeyEvent.VK_W) up = false;
        if (key == KeyEvent.VK_A) left = false;
        if (key == KeyEvent.VK_S) down = false;
        if (key == KeyEvent.VK_D) right = false;
    }
}
