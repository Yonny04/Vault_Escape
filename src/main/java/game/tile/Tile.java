package game.tile;

import game.object.*;
import game.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a 2D sprite in the game with a specified screen position. The Sprite2D class manages
 * drawing, screen positioning relative to the player, and optional collision hitbox display.
 */
public class Tile {
    protected GamePanel gp; // Reference to the game panel for player positioning
    protected BufferedImage image; // Sprite Image

    protected Rect rect; // Draw Rect
    protected Rect hitbox; // Hitbox Rect
    protected Vector screen; // Screen coordinates for rendering

    protected boolean visible = true;
    protected boolean drawCollisions = false; // Flag to toggle collision hitbox rendering

    public enum Layer {BOTTOM, ORDERED, TOP}
    public Layer layer = Layer.ORDERED; // Default layer is ORDERED
    public boolean collisionMask = true;

    /**
     * Constructs a Sprite2D associated with a specified game panel.
     *
     * @param gp the game panel associated with this sprite
     */
    public Tile(GamePanel gp, Vector start) {
        this.gp = gp;
        this.rect = new Rect();
        this.hitbox = new Rect();
        this.screen = new Vector();
        setPosition(start);
        hitbox.setSize(getSize().scale(0.6));
        hitbox.setPosition(hitbox.getSize().scale(0.4));
    }

    public Tile(GamePanel gp) {
        this.gp = gp;
        this.rect = new Rect();
        this.hitbox = new Rect();
        this.screen = new Vector();
    }

    /**
     * Retrieves the screen position of the sprite.
     *
     * @return the Vector2 screen position. 
     */
    public Vector getScreenPosition() {return screen;}

    /**
     * Updates the state of the Sprite.
     * This method is to be implemented by inherited classes.
     */
    public void update() {}
    
    /**
     * Draws the sprite at its calculated screen location if it is within the viewable area,
     * and optionally draws the collision hitbox.
     *
     * @param g2 the Graphics2D object used to render the sprite
     */
    public void draw(Graphics2D g2) {
        Vector offset = gp.getPlayer().getCameraOffset();
        Vector camera = gp.getPlayer().getCameraPosition();
        screen.setPosition(rect.subtract(camera).add(offset));
        if (isVisibleOnScreen() && isVisible()) {
            g2.drawImage(image, screen.x, screen.y, rect.w, rect.h, null);
            if (drawCollisions) drawHitbox(g2);
        }
    }

    /**
     * Draws the hitbox of the sprite for debugging purposes, centered on the sprite image.
     *
     * @param g2 the Graphics2D object used to render the hitbox
     */
    public void drawHitbox(Graphics2D g2) {
        if (drawCollisions && !hitbox.getSize().isZero() && isVisible()) {
            g2.setColor(new Color(0,1.0f,1.0f,0.5f));
            g2.drawRect(screen.x + hitbox.x,screen.y + hitbox.y, hitbox.w, hitbox.h);
        }
    }

    /**
     * Determines if this tile is visible within the player's camera view.
     * The visibility is based on the tile's position relative to the player's camera offset and position.
     *
     * @return true if the tile is within the visible range of the player's camera, false otherwise.
     */
    public boolean isVisibleOnScreen() {
        Vector offset = gp.getPlayer().getCameraOffset();
        Vector camera = gp.getPlayer().getCameraPosition();
        return (rect.x + 2*Vector.TILE_SIZE.x > camera.x - offset.x && 
                rect.x - 2*Vector.TILE_SIZE.x < camera.x + offset.x && 
                rect.y + 2*Vector.TILE_SIZE.y > camera.y - offset.y && 
                rect.y - 2*Vector.TILE_SIZE.y < camera.y + offset.y);
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

    /**
     * Returns the center point of this object.
     * The center is calculated by adding half of its size to its current position.
     *
     * @return A Vector representing the center point of this object.
     */
    public Vector getCenter() {
        return getRect().add(getSize().scale(0.5));
    }

    /**
     * Determines if this tile is visually above the specified tile.
     * This is determined by comparing the y-coordinates of their center points.
     *
     * @param tile The sprite to compare against.
     * @return true if this object is above the specified tile, false otherwise.
     */
    public boolean isAbove(Tile tile) {
        return getCenter().y > tile.getCenter().y;
    }

    /**
     * Checks if this tile is touching another tile.
     * Adapter design pattern
     *
     * @param tile the tile to check against
     * @return true if the tiles are touching, otherwise false
     */
    public boolean isTouching(Tile tile) {
        Rect r1 = new Rect(hitbox.add(getPosition()),hitbox.getSize());
        Rect r2 = new Rect(tile.hitbox.add(tile.getPosition()),tile.hitbox.getSize());
        return (r1.isTouching(r2));
    }

    /**
     * Sets the image to be displayed by this tile.
     *
     * @param image the BufferedImage to display
     */
    public void setImage(BufferedImage image) {this.image = image;}

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

    /**
     * Checks if this entity is currently visible.
     *
     * @return true if the entity is visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Hides this entity by setting its visibility to false.
     */
    public void hide() {
        visible = false;
    }

    /**
     * Shows this entity by setting its visibility to true.
     */
    public void show() {
        visible = true;
    }

    /**
     * Returns the hitbox of this entity.
     *
     * @return The Rect object representing the hitbox of the entity.
     */
    public Rect getHitbox() {
        return hitbox;
    }

    /**
     * Sets the hitbox of this entity to the specified rectangular bounds.
     *
     * @param rect The Rect object to set as the hitbox.
     */
    public void setHitbox(Rect rect) {
        hitbox = rect;
    }
}
