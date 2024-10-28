package vaultescape.entity;

import vaultescape.map.GamePanel;
import vaultescape.ui.Sprite;

public class Entity extends Sprite{
    protected int speed;
    protected GamePanel gp;

    // Constructor
    public Entity(GamePanel gp) {
        this.gp = gp;
        this.width = gp.tilesize;
        this.height = gp.tilesize;
    }

    // Abstract adapter method for collision detection
    public boolean isTouching(Sprite sprite) {
        return (sprite.getBounds().intersects(this.getBounds()));
    }
}
