package vaultescape.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Base abstract class for any 2d image on the screen.
 */
public abstract class Sprite {
    protected int x, y, width, height;
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

    // Get the sprite bounding box for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    // Draw the sprite image
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height, null);
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
