package game.tile.entity;

import game.object.Vector;
import game.panel.GamePanel;
import game.tile.Tile;
import game.utils.*;

import java.awt.Graphics2D;
import java.util.Map;

/**
 * Represents a base entity in the game, with movement, collision detection, and
 * sprite management capabilities. Other entities, such as players or enemies, can
 * extend this class to inherit these common behaviors.
 */
public class Entity extends Tile {
    protected Tile shadow;

    protected AnimationPlayer animationPlayer;

    /**
     * Constructs an Entity with a specified game panel, setting up basic
     * attributes such as dimensions and hitbox.
     *
     * @param gp the game panel associated with this entity
     * @param start the starting position
     */
    public Entity(GamePanel gp, Vector start) {
        super(gp, start);
        createShadow();
        animationPlayer = new AnimationPlayer(this);
        animationPlayer.setEntity(this);
    }

    /**
     * Creates a shadow sprite for this entity.
     * Sets the size of the shadow and loads its image from resources.
     */
    private void createShadow() {
        shadow = new Tile(gp);
        shadow.setSize(new Vector(14, 6).scale(Vector.SCALE));
        shadow.setImage(ResourceLoader.loadSpritesheet("shadow"));
        shadow.setLayer(Layer.BOTTOM);
    }

    /**
     * Checks if this entity has a shadow.
     * @return true if this entity has a shadow, false otherwise
     */
    public boolean hasShadow() {return shadow != null;}
    /**
     * Returns the shadow tile for this entity.
     * For drawing purposes, the shadow is rendered beneath the entity.
     * @return the shadow tile for this entity
     */
    public Tile getShadow() {return shadow;}

    /**
     * Checks if this entity is touching the player. 
     * If this entity is the player itself, it returns false.
     *
     * @return true if this entity is touching the player, false otherwise.
     */
    public boolean isTouchingPlayer() {
        if (this == gp.getPlayer()) return false;
        return isTouching(gp.getPlayer());
    }

    public boolean isTouchingWall() {
        Map<String, Tile> walls = gp.getTileGenerator().wallTiles;
        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                String unit = rect.add(new Vector(i*64, j*64)).getUnitString();
                if (walls.containsKey(unit)) {
                    Tile wall = walls.get(unit);
                    if (isTouching(wall) && wall.collisionMask) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public void update() {
        animationPlayer.update();
        super.update();
    }
    
    /**
     * Draws the entity and its shadow.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }

    /**
     * Draws the shadow below the entity at a calculated offset.
     * This should be called before the main draw call to render beneath the entity.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    public void drawShadow(Graphics2D g2) {
        if (isVisible() && shadow != null) {
            shadow.setPosition(rect.x + 4, rect.y + 4*12);
            shadow.draw(g2);
        }
    }

    /**
     * Returns the AnimationPlayer for this entity.
     * @return the AnimationPlayer for this entity
     */
    public AnimationPlayer getAnimationPlayer() {return animationPlayer;}

    /**
     * Sets the AnimationPlayer for this entity.
     * @param animationPlayer the AnimationPlayer to set to
     */
    public void setAnimationPlayer(AnimationPlayer animationPlayer) {
        this.animationPlayer = animationPlayer;
    }

}
