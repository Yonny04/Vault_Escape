package vaultescape.entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import vaultescape.map.GamePanel;
import vaultescape.ui.Sprite;

public class Entity extends Sprite {
    protected int speed;
    protected GamePanel gp;
    
    int direction = 1; 
    protected BufferedImage spritesheet;
    protected int[] spritesheetDim = {0,0}; // Dimensions tiles on the sprite sheet

    // Constructor
    public Entity(GamePanel gp) {
        this.gp = gp;
        this.width = gp.tilesize;
        this.height = gp.tilesize;
        setHitbox(width, height);
    }

    // Abstract adapter method for collision detection
    public boolean isTouching(Sprite sprite) {
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
}
