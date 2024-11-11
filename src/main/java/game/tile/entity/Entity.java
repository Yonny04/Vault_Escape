package game.tile.entity;

import game.object.Vector;
import game.panel.GamePanel;
import game.tile.Tile;
import game.utils.AnimationPlayer;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;

/**
 * Represents a base entity in the game, with movement, collision detection, and
 * sprite management capabilities. Other entities, such as players or enemies, can
 * extend this class to inherit these common behaviors.
 */
public class Entity extends Tile {
    protected Tile _shadow;

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
        animationPlayer = new AnimationPlayer(gp, this);
    }

    /**
     * Creates a shadow sprite for this entity.
     * Sets the size of the shadow and loads its image from resources.
     */
    private void createShadow() {
        _shadow = new Tile(gp);
        _shadow.setSize(new Vector(14, 6).scale(Vector.SCALE));
        try {
            _shadow.setImage(ImageIO.read(getClass().getResourceAsStream("/tile/entity/shadow.png")));
        } catch (Exception e) {
            e.printStackTrace();  // Consider adding this for debugging
        }
    }


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
        drawShadow(g2);
        super.draw(g2);
    }

    /**
     * Draws the shadow below the entity at a calculated offset.
     * This should be called before the main draw call to render beneath the entity.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    public void drawShadow(Graphics2D g2) {
        if (isVisible()) {
            _shadow.setPosition(rect.x + 4, rect.y + 4*12);
            _shadow.draw(g2);
        }
    }

    public AnimationPlayer getAnimationPlayer() {return animationPlayer;}

}
