package vaultescape.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Base abstract class for any 2D image on the screen, providing methods for position,
 * size, hitbox management, and image display. Component adapter class for Rect2.
 */
public abstract class Sprite {
    public Rect2 rect; // Draw Rect
    protected BufferedImage image; // Sprite Image

    /**
     * Sets the image to be displayed by the sprite.
     *
     * @param image the BufferedImage to display
     */
    public void setImage(BufferedImage image) {this.image = image;}

    /**
     * Draws the sprite on the screen with the given Graphics2D context.
     *
     * @param g2 the Graphics2D object used to render the sprite
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(image, rect.x, rect.y, rect.w, rect.h, null);
    }

    // ADAPTER METHODS

    public Rect2 getRect() {return rect;}
    public void setRect(Rect2 rect) {this.rect = rect;}

    public Vector2 getPosition() {return new Vector2(rect.x, rect.y);}
    public Vector2 getDimension() {return new Vector2(rect.w, rect.h);}

    public void setPosition(Vector2 vector) {rect.setPosition(vector);}
    public void setPosition(int x, int y) {rect.setPosition(x, y);}

    public void setDimension(Vector2 vector) {rect.setDimension(vector);}
    public void setDimension(int w, int h) {rect.setDimension(w, h);}

}
