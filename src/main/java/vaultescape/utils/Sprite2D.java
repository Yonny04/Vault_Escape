package vaultescape.utils;

import vaultescape.ui.GamePanel;

import java.awt.*;

/**
 * Represents a 2D sprite in the game with a specified screen position. The Sprite2D class manages
 * drawing, screen positioning relative to the player, and optional collision hitbox display.
 */
public class Sprite2D extends Sprite {
    protected GamePanel gp; // Reference to the game panel for player positioning
    public Rect2 hitbox; // Hitbox Rect
    protected Vector2 screen = new Vector2(); // Screen coordinates for rendering
    protected boolean _drawCollisions = false; // Flag to toggle collision hitbox rendering

    /**
     * Constructs a Sprite2D associated with a specified game panel.
     *
     * @param gp the game panel associated with this sprite
     */
    public Sprite2D(GamePanel gp) {
        this.gp = gp;
        hitbox = new Rect2();
        rect = new Rect2();
    }

    public Rect2 getHitbox() {return this.hitbox;}
    /**
     * Sets the dimensions of the sprite's hitbox for collision detection.
     *
     * @param rect rect to set the hitbox to
     */
    public void setHitbox(Rect2 rect) {
        hitbox.setRect(rect);
    }
    /**
     * Sets the dimensions of the sprite's hitbox for collision detection.
     *
     * @param vector dimension of the width and height of the hitbox
     */
    public void setHitbox(Vector2 vector) {
        hitbox.setDimension(vector);
    }

    /**
     * Retrieves the screen position of the sprite.
     *
     * @return the Vector2 screen position. 
     */
    public Vector2 getScreenPosition() {return screen;}

    /**
     * Draws the sprite at its calculated screen location if it is within the viewable area,
     * and optionally draws the collision hitbox.
     *
     * @param g2 the Graphics2D object used to render the sprite
     */
    @Override
    public void draw(Graphics2D g2) {
        Vector2 player = gp.getPlayer().getRect().getPosition();
        Vector2 playerScreen = gp.getPlayer().getScreenPosition();
        screen.x = rect.x - player.x + playerScreen.x;
        screen.y = rect.y - player.y + playerScreen.y;
        if (rect.x + 2*gp.TILE_SIZE.x > player.x - playerScreen.x && 
            rect.x - 2*gp.TILE_SIZE.x < player.x + playerScreen.x && 
            rect.y + 2*gp.TILE_SIZE.y > player.y - playerScreen.y && 
            rect.y - 2*gp.TILE_SIZE.y < player.y + playerScreen.y) {
            g2.drawImage(image, screen.x, screen.y, rect.w, rect.h, null);
            if (_drawCollisions) drawHitbox(g2);
        }
    }

    /**
     * Draws the hitbox of the sprite for debugging purposes, centered on the sprite image.
     *
     * @param g2 the Graphics2D object used to render the hitbox
     */
    public void drawHitbox(Graphics2D g2) {
        if (_drawCollisions && !hitbox.isZero()) {
            g2.setColor(new Color(0,1.0f,1.0f,0.5f));
            g2.drawRect(screen.x + (rect.w - hitbox.w)/2, 
                screen.y + (rect.h - hitbox.h)/2, hitbox.w, hitbox.h);
        }
    }

    /**
     * Checks if the current Sprite2D is touching another Sprite2D.
     * Adapter design pattern
     *
     * @param sprite the sprite2D to check against
     * @return {@code true} if the rectangles are touching, otherwise {@code false}
     */
    public boolean isTouching(Sprite2D sprite) {
        getHitbox().setPosition(getPosition());
        Rect2 thisRect = getHitbox();
        sprite.getHitbox().setPosition(sprite.getPosition());
        Rect2 spriteRect = sprite.getHitbox();
        return (thisRect.isTouching(spriteRect));
    }
}
