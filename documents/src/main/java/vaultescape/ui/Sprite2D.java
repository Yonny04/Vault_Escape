package vaultescape.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

/**
 * Represents a 2D sprite in the game with a specified screen position. The Sprite2D class manages
 * drawing, screen positioning relative to the player, and optional collision hitbox display.
 */
public class Sprite2D extends Sprite {
    protected GamePanel gp; // Reference to the game panel for player positioning
    protected int screenX, screenY; // Screen coordinates for rendering
    private final boolean _drawCollisions = false; // Flag to toggle collision hitbox rendering

    /**
     * Constructs a Sprite2D associated with a specified game panel.
     *
     * @param gp the game panel associated with this sprite
     */
    public Sprite2D(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Retrieves the screen X-coordinate of the sprite.
     *
     * @return the screen X-coordinate
     */
    public int getScreenX() {
        return screenX;
    }

    /**
     * Retrieves the screen Y-coordinate of the sprite.
     *
     * @return the screen Y-coordinate
     */
    public int getScreenY() {
        return screenY;
    }

    /**
     * Draws the sprite at its calculated screen location if it is within the viewable area,
     * and optionally draws the collision hitbox.
     *
     * @param g2 the Graphics2D object used to render the sprite
     */
    @Override
    public void draw(Graphics2D g2) {
        int playerX = gp.getPlayer().getX();
        int playerScreenX = gp.getPlayer().getScreenX();
        int playerY = gp.getPlayer().getY();
        int playerScreenY = gp.getPlayer().getScreenY();
        screenX = x - playerX + playerScreenX;
        screenY = y - playerY + playerScreenY;
        if (x + 2*gp.tilesize > playerX - playerScreenX && 
            x - 2*gp.tilesize < playerX + playerScreenX && 
            y + 2*gp.tilesize > playerY - playerScreenY && 
            y - 2*gp.tilesize < playerY + playerScreenY) {
            g2.drawImage(image, screenX, screenY, width, height, null);
            if (_drawCollisions) drawHitbox(g2);
        }
    }

    /**
     * Draws the hitbox of the sprite for debugging purposes, centered on the sprite image.
     *
     * @param g2 the Graphics2D object used to render the hitbox
     */
    public void drawHitbox(Graphics2D g2) {
        if (_drawCollisions) {
            g2.setColor(new Color(0, (float) 1.0, (float) 1.0, (float) 0.5));
            g2.drawRect(screenX + (width - hitboxWidth) / 2, screenY + (height - hitboxHeight) / 2, hitboxWidth, hitboxHeight);
        }
    }

    /**
     * Factory method to create a new Sprite2D object with specified position and size.
     *
     * @param gp the game panel associated with the sprite
     * @param x the x-coordinate of the sprite
     * @param y the y-coordinate of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @return a new Sprite2D instance
     */
    public static Sprite2D createSprite2D(GamePanel gp, int x, int y, int width, int height) {
        Sprite2D newSprite = new Sprite2D(gp);
        newSprite.x = x;
        newSprite.y = y;
        newSprite.width = width;
        newSprite.height = height;
        return newSprite;
    }
}
