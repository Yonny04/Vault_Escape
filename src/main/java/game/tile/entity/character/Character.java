package game.tile.entity.character;

import game.object.Vector;
import game.panel.GamePanel;
import game.tile.Tile;
import game.tile.entity.Entity;
import game.tile.entity.character.enemy.Enemy;
import game.utils.ResourceLoader;

/**
 * Represents a character in the game, extending the Entity class.
 * Characters can have directions and speed.
 */
public class Character extends Entity {

    /**
     * Enum to define the possible directions a character can face: LEFT, RIGHT, UP, or DOWN.
     */
    public enum Direction {LEFT, RIGHT, UP, DOWN}
    
    protected Direction direction = Direction.LEFT; // Default direction is LEFT
    protected int speed;
    protected int maxSpeed = 7;

    /**
     * Constructs a Character with the specified game panel and starting position.
     *
     * @param gp    The game panel associated with this character.
     * @param start The starting position of this character.
     */
    public Character(GamePanel gp, Vector start) {
        super(gp, start);
        ResourceLoader.loadAnimationPlayer(this, "character");
    }

    /**
     * Sets the direction of this entity.
     *
     * @param direction The new direction to set for this entity.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Determines if the entity can move without colliding with any walls.
     *
     * @return true if the entity is not touching any walls, false otherwise
     */
    public boolean canMove() {
        for (Tile wall : gp.getTileGenerator().wallTiles) {
            if (isTouching(wall) && wall.collisionMask) {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves the object in the specified direction at the given speed.
     * Saves the old position before moving, and reverts if the move is not allowed.
     * 
     * @param direction the direction to move (LEFT, RIGHT, UP, DOWN)
     */
    public void move(Direction direction) {
        if (gp.introFade > 0) {getAnimationPlayer().stopAnimation(); return;}
        Vector oldPosition = rect.getPosition();
        setDirection(direction);
        switch (direction) {
            case LEFT:
                rect.x -= speed;
                break;
            case RIGHT:
                rect.x += speed;
                break;
            case UP:
                rect.y -= speed;
                break;
            case DOWN:
                rect.y += speed;
                break;
            default:
                break;
        }
        if (!canMove()) rect.setPosition(oldPosition);
    }

    /**
     * Moves the object in the specified direction at the given speed.
     * DOES NOT CHECK FOR COLLISION.
     * 
     * @param direction the direction to move (LEFT, RIGHT, UP, DOWN)
     */
    public void moveUnsafe(Direction direction) {
        if (gp.introFade > 0 && this instanceof Enemy) {
            getAnimationPlayer().stopAnimation(); return;
        }
        setDirection(direction);
        switch (direction) {
            case LEFT:
                rect.x -= speed;
                break;
            case RIGHT:
                rect.x += speed;
                break;
            case UP:
                rect.y -= speed;
                break;
            case DOWN:
                rect.y += speed;
                break;
            default:
                break;
        }
    }

     /**
     * Adds the movement speed of the character.
     *
     * @param value the value to add to speed
     */
    public void addSpeed(int value) {
        this.speed += value;
        if (this.speed >= maxSpeed) {
            this.speed = maxSpeed;
        }
    }

    /**
     * Sets the speed of the character.
     * @param speed the new speed of the character
     */
    public void setSpeed(int speed) {
        if (speed >= maxSpeed) {
            this.speed = maxSpeed;
            return;
        }
        this.speed = speed;
    }
}
