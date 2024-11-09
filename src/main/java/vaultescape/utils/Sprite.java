package vaultescape.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Base abstract class for any 2D image on the screen, providing methods for position,
 * size, hitbox management, and image display. Component adapter class for Rect2.
 */
public abstract class Sprite {
    protected Rect rect; // Draw Rect
    protected BufferedImage image; // Sprite Image
    protected boolean visible = true;

    /**
     * Sets the image to be displayed by the sprite.
     *
     * @param image the BufferedImage to display
     */
    public void setImage(BufferedImage image) {this.image = image;}

    public void update() {}
    /**
     * Draws the sprite on the screen with the given Graphics2D context.
     *
     * @param g2 the Graphics2D object used to render the sprite
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(image, rect.x, rect.y, rect.w, rect.h, null);
    }

    // ADAPTER METHODS

    /**
     * Gets the current rectangle.
     * 
     * @return the current Rect object
     */
    public Rect getRect() {return this.rect;}

    /**
     * Sets the rectangle.
     * 
     * @param rect the Rect object to set
     */
    public void setRect(Rect rect) {this.rect = rect;}

    /**
     * Gets the position as a Vector object.
     * 
     * @return a new Vector object representing the position (x, y)
     */
    public Vector getPosition() {
        return new Vector(rect.x, rect.y);
    }

    /**
     * Gets the size as a Vector object.
     * 
     * @return a new Vector object representing the size (w, h)
     */
    public Vector getSize() {
        return new Vector(rect.w, rect.h);
    }

    /**
     * Sets the position using a Vector object.
     * 
     * @param vector the Vector object containing the new position (x, y)
     */
    public void setPosition(Vector vector) {
        rect.setPosition(vector);
    }

    /**
     * Sets the position using x and y coordinates.
     * 
     * @param x the x coordinate to set
     * @param y the y coordinate to set
     */
    public void setPosition(int x, int y) {
        rect.setPosition(x, y);
    }

    /**
     * Sets the size using a Vector object.
     * 
     * @param vector the Vector object containing the new size (w, h)
     */
    public void setSize(Vector vector) {
        rect.setSize(vector);
    }

    /**
     * Sets the size using width and height values.
     * 
     * @param w the width to set
     * @param h the height to set
     */
    public void setSize(int w, int h) {
        rect.setSize(w, h);
    }
}
