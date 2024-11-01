package vaultescape.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import vaultescape.map.GamePanel;
import vaultescape.ui.Sprite2D;

public class Entity extends Sprite2D {
    protected double speed;
    
    private Sprite2D _shadow; 
    protected int direction = 1;
    protected float spriteCounter = 0.0f;
    protected BufferedImage spritesheet;
    protected int[] spritesheetDim = {0,0}; // Dimensions tiles on the sprite sheet

    // Constructor
    public Entity(GamePanel gp) {
        super(gp);
        this.width = gp.tilesize;
        this.height = gp.tilesize;
        setHitbox(width, height);
        _shadow = Sprite2D.createSprite2D(gp, x, y, 14*4, 6*4);
        try {
            _shadow.setImage(ImageIO.read(getClass().getResourceAsStream("/entity/shadow.png")));
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Checks whether this sprite is touching the given sprite.
     * @param sprite
     * @return true if they are intersecting
     */
    public boolean isTouching(Sprite2D sprite) {
        return (sprite.getBounds().intersects(this.getBounds()));
    }

    /**
     * Sets the sprite image to get subimages from. (16x16 = 1 tile).
     * NOTE: First tile index is 0, last tile is n-1 if n is the total amount of tiles.
     * @param pathString the resource stream path (e.x. "/map/spritesheet.png")
     * @param numTilesX the max number of tiles horizontally on the spreadsheet
     * @param numTilesY the max number of tiles vertically on the spreadsheet
     */
    public void setSpritesheet(String pathString, int numTilesX, int numTilesY) {
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream(pathString));
            spritesheetDim = new int[]{numTilesX,numTilesY};
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Sets the sprite's image from the coord on the spritesheet
     * spriteCoord is by tile, so don't worry about pixel counts.
     * @param spriteCoord the tile coord on the spritesheet (1 tile is 16x16)
     */
    public void setFrame(int coordX, int coordY) {
        if (spritesheetDim[1] == 0) return;
        BufferedImage currentFrame = spritesheet.getSubimage(coordX*16,coordY*16,16,16);
        setImage(currentFrame);
    }
    /**
     * Sets the sprite's image to the spriteNum on the spritesheet.
     * spriteNum starts from top left, reading right, 
     * then starting each row from the left again.
     * @param spriteNum
     */
    public void setFrame(int spriteNum) {
        if (spritesheetDim[1] == 0) return;
        int spriteCol = spriteNum % spritesheetDim[0];
        int spriteRow = spriteNum / spritesheetDim[1];
        BufferedImage currentFrame = spritesheet.getSubimage(spriteCol*16,spriteRow*16,16,16);
        setImage(currentFrame);
    }
    @Override
    public void draw(Graphics2D g2) {
        drawShadow(g2);
        super.draw(g2);
    }
    /**
     * Draw the shadow below the entity (call before the main call!)
     * @param g2
     */
    public void drawShadow(Graphics2D g2) {
        _shadow.setX(x+4);
        _shadow.setY(y+48);
        _shadow.draw(g2);
    }
}
