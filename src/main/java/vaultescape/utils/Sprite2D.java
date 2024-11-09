package vaultescape.utils;

import vaultescape.ui.GamePanel;

import java.awt.*;

/**
 * Represents a 2D sprite in the game with a specified screen position. The Sprite2D class manages
 * drawing, screen positioning relative to the player, and optional collision hitbox display.
 */
public class Sprite2D extends Sprite {
    protected GamePanel gp; // Reference to the game panel for player positioning
    public Rect hitbox; // Hitbox Rect
    protected Vector screen; // Screen coordinates for rendering
    protected boolean drawCollisions = false; // Flag to toggle collision hitbox rendering

    /**
     * Constructs a Sprite2D associated with a specified game panel.
     *
     * @param gp the game panel associated with this sprite
     */
    public Sprite2D(GamePanel gp) {
        this.gp = gp;
        rect = new Rect();
        hitbox = new Rect();
        screen = new Vector();
    }

    /**
     * Retrieves the screen position of the sprite.
     *
     * @return the Vector2 screen position. 
     */
    public Vector getScreenPosition() {return screen;}

    /**
     * Draws the sprite at its calculated screen location if it is within the viewable area,
     * and optionally draws the collision hitbox.
     *
     * @param g2 the Graphics2D object used to render the sprite
     */
    @Override
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
     * Returns the center point of this object.
     * The center is calculated by adding half of its size to its current position.
     *
     * @return A Vector representing the center point of this object.
     */
    public Vector getCenter() {
        return getRect().add(getSize().scale(0.5));
    }

    /**
     * Determines if this sprite2D is visually above the specified sprite2D.
     * This is determined by comparing the y-coordinates of their center points.
     *
     * @param rect The rectangle to compare against.
     * @return true if this object is above the specified rectangle, false otherwise.
     */
    public boolean isAbove(Sprite2D sprite) {
        return getCenter().y > sprite.getCenter().y;
    }

    /**
     * Checks if the current Sprite2D is touching another Sprite2D.
     * Adapter design pattern
     *
     * @param sprite the sprite2D to check against
     * @return {@code true} if the rectangles are touching, otherwise {@code false}
     */
    public boolean isTouching(Sprite2D sprite) {
        Rect r1 = new Rect(hitbox.add(getPosition()),hitbox.getSize());
        Rect r2 = new Rect(sprite.hitbox.add(sprite.getPosition()),sprite.hitbox.getSize());
        return (r1.isTouching(r2));
    }
}
