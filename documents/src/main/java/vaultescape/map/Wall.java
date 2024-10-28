package vaultescape.map;

import vaultescape.ui.Sprite;

public class Wall extends Sprite {

    // Constructor to initialize the wall with position and size
    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
