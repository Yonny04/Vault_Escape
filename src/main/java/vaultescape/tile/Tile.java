package vaultescape.tile;

import vaultescape.ui.GamePanel;
import vaultescape.utils.Sprite2D;

/**
 * Represents a tile in the game, extending the Sprite2D class.
 * Tiles can be part of different layers and have collision properties.
 */
public class Tile extends Sprite2D {
    
    /**
     * Enum to define the layers a tile can be in: BOTTOM, ORDERED, or TOP.
     */
    public enum Layer {BOTTOM, ORDERED, TOP}
    
    public Layer layer = Layer.ORDERED; // Default layer is ORDERED
    public Layer collisionLayer = Layer.ORDERED;
    public boolean collisionMask = true;

    /**
     * Constructs a Tile with the specified game panel.
     *
     * @param gp The game panel associated with this tile.
     */
    public Tile(GamePanel gp) {
        super(gp);
    }

    /**
     * Sets the layer of this tile.
     *
     * @param layer The layer to set for this tile.
     */
    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    /**
     * Sets the collision mask state for this tile.
     *
     * @param state The collision mask state to set.
     */
    public void setCollisionMask(boolean state) {
        this.collisionMask = state;
    }
}
