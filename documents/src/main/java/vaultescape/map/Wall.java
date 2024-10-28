package vaultescape.map;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Wall {
    public BufferedImage image;  
    private int x, y, width, height;  

    // Constructor to initialize the wall with position and size
    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Get the bounding box for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Draw the wall image
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    // Set the wall's image
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
