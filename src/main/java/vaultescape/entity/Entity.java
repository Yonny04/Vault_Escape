package vaultescape.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import vaultescape.map.GamePanel;
import vaultescape.ui.Sprite2D;

/**
 * Represents a base entity in the game, with movement, collision detection, and
 * sprite management capabilities. Other entities, such as players or enemies, can
 * extend this class to inherit these common behaviors.
 */
public class Entity extends Sprite2D {
    protected double speed;
    private Sprite2D _shadow;
    protected int direction = 1;
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
    public Entity(GamePanel gp) {
        super(gp);
        this.width = gp.tilesize;
        this.height = gp.tilesize;
        setHitbox(width, height);
        _shadow = Sprite2D.createSprite2D(gp, x, y, 14 * 4, 6 * 4);
        try {
            _shadow.setImage(ImageIO.read(getClass().getResourceAsStream("/entity/shadow.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks whether this sprite is touching the specified sprite.
     *
     * @param sprite the sprite to check for collision
     * @return true if the entities are intersecting, false otherwise
     */
    public boolean isTouching(Sprite2D sprite) {
        return sprite.getBounds().intersects(this.getBounds());
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
     * Sets the position of the entity using an integer array representing coordinates.
     *
     * @param vector an integer array where the first element is the x-coordinate and the second is the y-coordinate
     */
    public void setPosition(int[] vector) {
        x = vector[0];
        y = vector[1];
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
        _shadow.setX(x + 4);
        _shadow.setY(y + 48);
        _shadow.draw(g2);
    }
}
