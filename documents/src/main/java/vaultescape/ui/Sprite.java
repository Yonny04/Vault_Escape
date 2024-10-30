package vaultescape.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
/**
 * Base abstract class for any 2d image on the screen.
 */
public abstract class Sprite {
    protected int x, y, width, height;
    protected int hitboxWidth = 0, hitboxHeight = 0;

    private boolean _drawCollisions = true;
    public BufferedImage image;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void setX(int value){
        this.x = value;
    }
    public void setY(int value){
        this.y = value;
    }

    // Set the sprite hitbox for collision detection
    public void setHitbox(int width, int height) {
        hitboxWidth = width;
        hitboxHeight = height;
    }

    // Get the sprite bounding box for collision detection (centered on the sprite)
    public Rectangle getBounds() {
        return new Rectangle(x + (width - hitboxWidth)/2, y + (height - hitboxHeight)/2, hitboxWidth, hitboxHeight);
    }

    /**
     * Sets the sprite image to display when drawn. 
     * @param image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Draw the sprite with the given image
     * @param g2
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height, null);
        if (_drawCollisions){
            g2.setColor(new Color(0,(float)1.0,(float)1.0,(float)0.5));
            g2.drawRect(x + (width - hitboxWidth)/2, y + (height - hitboxHeight)/2, hitboxWidth, hitboxHeight);
        }
    }

    static public Sprite createSprite(int x, int y, int width, int height) {
        Sprite newSprite = new Sprite(){};
        newSprite.x = x;
        newSprite.y = y;
        newSprite.width = width;
        newSprite.height = height;
        return newSprite;
    }
}
