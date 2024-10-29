package vaultescape.map;

import java.awt.Rectangle;

import vaultescape.ui.Sprite;

public class Wall extends Sprite {

    private Rectangle bounds;
    // Constructor to initialize the wall with position and size
    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
}
