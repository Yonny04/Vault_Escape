package vaultescape.tile;

import vaultescape.ui.GamePanel;
import vaultescape.utils.Sprite2D;

public class Tile extends Sprite2D {
    public enum Layer {BOTTOM,ORDERED,TOP}
    public Layer layer = Layer.ORDERED; // 0 = Bottom, 1 = Ordered, 2 = Top
    public Layer collisionLayer = Layer.ORDERED;
    public boolean collisionMask = true;

    public Tile(GamePanel gp) {
        super(gp);
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public void setCollisionMask(boolean state) {
        this.collisionMask = state;
    }
    
}
