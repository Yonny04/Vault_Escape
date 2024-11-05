package vaultescape.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Base abstract class for any 2D image on the screen, providing methods for position,
 * size, hitbox management, and image display.
 */
public abstract class Sprite {
    protected int x, y, width, height; // Position and dimensions of the sprite
    protected int hitboxWidth = 0, hitboxHeight = 0; // Dimensions of the hitbox
    public BufferedImage image; // Image displayed by the sprite

    /**
     * Retrieves the x-coordinate of the sprite.
     *
     * @return the x-coordinate of the sprite
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate of the sprite.
     *
     * @return the y-coordinate of the sprite
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the sprite.
     *
     * @param value the x-coordinate to set
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * Sets the y-coordinate of the sprite.
     *
     * @param value the y-coordinate to set
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * Sets the dimensions of the sprite's hitbox for collision detection.
     *
     * @param width  the width of the hitbox
     * @param height the height of the hitbox
     */
    public void setHitbox(int width, int height) {
        hitboxWidth = width;
        hitboxHeight = height;
    }

    /**
     * Gets the sprite's bounding box, centered on the sprite, for collision detection.
     *
     * @return a Rectangle representing the bounding box
     */
    public Rectangle getBounds() {
        return new Rectangle(x + (width - hitboxWidth) / 2, y + (height - hitboxHeight) / 2, hitboxWidth, hitboxHeight);
    }

    /**
     * Sets the image to be displayed by the sprite.
     *
     * @param image the BufferedImage to display
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Draws the sprite on the screen with the given Graphics2D context.
     *
     * @param g2 the Graphics2D object used to render the sprite
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height, null);
    }
}
