package vaultescape.entity;

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
public class Entity extends Sprite2D {
    protected int speed;
    private Sprite2D _shadow;

    protected enum Direction {LEFT,RIGHT,UP,DOWN};
    protected Direction direction = Direction.LEFT;
    protected float spriteCounter = 0.0f;
    protected BufferedImage spritesheet;
    protected int[] spritesheetDim = {0, 0}; // Dimensions of tiles on the sprite sheet
    protected int spritesheetTileSize = 16; // size of the tile

    /**
     * Constructs an Entity with a specified game panel, setting up basic
     * attributes such as dimensions and hitbox.
     *
     * @param gp the game panel associated with this entity
     */
    public Entity(GamePanel gp, Vector2 start) {
        super(gp);
        setPosition(start);
        setHitbox(getDimension().scale(0.7));
        createShadow();
    }

    private void createShadow() {
        _shadow = new Sprite2D(gp);
        _shadow.getRect().setDimension(14*4,6*4);
        try {
            _shadow.setImage(ImageIO.read(getClass().getResourceAsStream("/entity/shadow.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines if the entity can move without colliding with any walls.
     *
     * @return true if the entity is not touching any walls, false otherwise
     */
    public boolean canMove() {
        for (Sprite2D wall : gp.getTileGenerator().walls) {
            if (isTouching(wall)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Plays the animation by incrementing the sprite counter.
     * If the sprite counter exceeds 3.9, it is reset to 0.0.
     * Sets the current frame based on the sprite counter and direction.
     */
    public void playAnimation() {
        spriteCounter += 0.1f;
        if (spriteCounter > 3.9f) spriteCounter = 0.0f;
        setFrame((int)Math.floor(spriteCounter), direction.ordinal());
    }

    /**
     * Stops the animation by setting the sprite counter to 1.0.
     * Sets the current frame based on the sprite counter and direction.
     */
    public void stopAnimation() {
        spriteCounter = 1.0f;
        setFrame((int)Math.floor(spriteCounter), direction.ordinal());
    }

    /**
     * Moves the object in the specified direction at the given speed.
     * Saves the old position before moving, and reverts if the move is not allowed.
     * 
     * @param direction the direction to move (LEFT, RIGHT, UP, DOWN)
     */
    public void move(Direction direction) {
        Vector2 oldPosition = rect.getPosition();
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

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Sets the sprite image to a spritesheet and defines its tile dimensions.
     * 
     * @param pathString the path to the spritesheet resource (e.g., "/map/spritesheet.png")
     * @param numTilesX the number of tiles horizontally on the spritesheet
     * @param numTilesY the number of tiles vertically on the spritesheet
     */
    public void setSpritesheet(String pathString, int numTilesX, int numTilesY) {
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream(pathString));
            spritesheetDim = new int[] { numTilesX, numTilesY };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the sprite's image to a specific tile on the spritesheet using tile coordinates.
     *
     * @param coordX the x-coordinate (column) on the spritesheet
     * @param coordY the y-coordinate (row) on the spritesheet
     */
    public void setFrame(int coordX, int coordY) {
        if (spritesheetDim[1] == 0) return;
        BufferedImage currentFrame = spritesheet.getSubimage(coordX * spritesheetTileSize, 
            coordY * spritesheetTileSize, spritesheetTileSize, spritesheetTileSize);
        setImage(currentFrame);
    }

    /**
     * Sets the sprite's image to a specific tile based on a linear index.
     *
     * @param spriteNum the index of the tile in the spritesheet (counted left to right, top to bottom)
     */
    public void setFrame(int spriteNum) {
        if (spritesheetDim[1] == 0) return;
        int spriteCol = spriteNum % spritesheetDim[0];
        int spriteRow = spriteNum / spritesheetDim[0];
        BufferedImage currentFrame = spritesheet.getSubimage(spriteCol * spritesheetTileSize, 
            spriteRow * spritesheetTileSize, spritesheetTileSize, spritesheetTileSize);
        setImage(currentFrame);
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
        _shadow.getRect().setPosition(rect.x + 4, rect.y + 4*12);
        _shadow.draw(g2);
    }
}
