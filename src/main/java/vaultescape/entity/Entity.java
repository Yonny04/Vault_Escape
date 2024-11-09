package vaultescape.entity;

import vaultescape.tile.Tile;
import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents a base entity in the game, with movement, collision detection, and
 * sprite management capabilities. Other entities, such as players or enemies, can
 * extend this class to inherit these common behaviors.
 */
public class Entity extends Tile {
    protected int speed;
    protected Sprite2D _shadow;

    public enum Direction {LEFT,RIGHT,UP,DOWN;}
    protected Direction direction = Direction.LEFT;

    protected BufferedImage sheet;
    protected Vector sheetDim;
    protected float frame = 0.0f;
    protected Vector frameSize;

    /**
     * Constructs an Entity with a specified game panel, setting up basic
     * attributes such as dimensions and hitbox.
     *
     * @param gp the game panel associated with this entity
     */
    public Entity(GamePanel gp, Vector start) {
        super(gp);
        setPosition(start);
        hitbox.setSize(getSize().scale(0.6));
        hitbox.setPosition(hitbox.getSize().scale(0.4));
        createShadow();
    }

    /**
     * Creates a shadow sprite for this entity.
     * Sets the size of the shadow and loads its image from resources.
     */
    private void createShadow() {
        _shadow = new Sprite2D(gp);
        _shadow.setSize(14*4, 6*4);
        try {
            _shadow.setImage(ImageIO.read(getClass().getResourceAsStream("/entity/shadow.png")));
        } catch (Exception e) {
            e.printStackTrace();  // Consider adding this for debugging
        }
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
     * Checks if this entity is touching the player. 
     * If this entity is the player itself, it returns false.
     *
     * @return true if this entity is touching the player, false otherwise.
     */
    public boolean isTouchingPlayer() {
        if (this == gp.getPlayer()) return false;
        return isTouching(gp.getPlayer());
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
    
    /**
     * Sets the sprite image to a spritesheet and defines its tile dimensions.
     * 
     * @param path the path to the spritesheet resource (e.g., "/map/spritesheet.png")
     * @param numTilesX the number of tiles horizontally on the spritesheet
     * @param numTilesY the number of tiles vertically on the spritesheet
     */
    public void setSpritesheet(String path, int tilesX, int tilesY) {
        try {
            sheet = ImageIO.read(getClass().getResourceAsStream(path));
            sheetDim = new Vector(tilesX,tilesY);
            frameSize = new Vector(sheet.getWidth()/sheetDim.x,sheet.getHeight()/sheetDim.y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAnimation() {}

    /**
     * Sets the sprite's image to a specific tile on the spritesheet using tile coordinates.
     *
     * @param coordX the x-coordinate (column) on the spritesheet
     * @param coordY the y-coordinate (row) on the spritesheet
     */
    public void setFrame(int coordX, int coordY) {
        if (sheetDim.x == 0) return;
        BufferedImage currentFrame = sheet.getSubimage(coordX * frameSize.x, 
            coordY * frameSize.y, frameSize.x, frameSize.y);
        setImage(currentFrame);
    }

    /**
     * Sets the sprite's image to a specific tile based on a linear index.
     *
     * @param spriteNum the index of the tile in the spritesheet (counted left to right, top to bottom)
     */
    public void setFrame(int spriteNum) {
        int coordX = spriteNum % sheetDim.x;
        int coordY = spriteNum / sheetDim.y;
        if (sheetDim.y == 1) setFrame(coordX, 0);
        else setFrame(coordX, coordY);
    }

    public float getFrame() {
        return frame;
    }

    public int getNextFrame() {
        return (int)Math.floor(frame);
    }

    /**
     * Plays the animation by incrementing the sprite counter.
     * If the sprite counter exceeds 3.9, it is reset to 0.0.
     * Sets the current frame based on the sprite counter and direction.
     */
    public void playAnimation() {
        frame += 0.1f;
        if (frame > 3.9f) frame = 0.0f;
        setFrame((int)Math.floor(frame), direction.ordinal());
    }

    /**
     * Stops the animation by setting the sprite counter to 1.0.
     * Sets the current frame based on the sprite counter and direction.
     */
    public void stopAnimation() {
        frame = 1.0f;
        setFrame((int)Math.floor(frame), direction.ordinal());
    }

     /**
     * Adds the movement speed of the enemy.
     *
     * @param value the value to add to speed
     */
    public void addSpeed(int value) {
        this.speed += value;
    }

}
