package vaultescape.utils;

import vaultescape.entity.Entity;
import vaultescape.ui.GamePanel;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.util.*;

public class AnimationPlayer {

    GamePanel gp;
    Entity en;
    
    protected BufferedImage sheet;
    protected Vector sheetDim;
    protected Vector frameSize;
    
    public int currentFrame = 0;
    public int lastFrame = 0;

    private Animation currentAnimation = null;
    private Map<String,Animation> animations = new HashMap<String, Animation>();

    public AnimationPlayer(GamePanel gp, Entity en) {
        this.gp = gp;
        this.en = en;
    }

    public boolean isPlaying() {
        return (currentAnimation != null);
    }

    public boolean finished() {
        if (isPlaying()) {
            return currentAnimation.finished();
        }
        return false;
    }

    public void newAnimation(String name, int[] track, int frames, float duration, boolean loop) {
        Animation animation = new Animation(gp, name, track, frames, duration, loop);
        animations.put(name,animation);
    }

    public void playAnimation(String name) {
        if (isPlaying())
            if (currentAnimation.name == name) {
                return;
            }
            else currentAnimation.stop();
        this.currentAnimation = animations.get(name);
        this.currentAnimation.start();
    }

    public void stopAnimation() {
        if (isPlaying()) {
            this.currentAnimation.stop();
        }
        this.currentAnimation = null;
        
    }

    public void update() {
        this.lastFrame = this.currentFrame;
        if (isPlaying()) {
            if (currentAnimation.isPlaying)
            this.currentAnimation.update();
            this.currentFrame = getFrame();
            if (currentFrame != lastFrame) setFrame(currentFrame);
        }
    }

    public int getFrame() {
        if (isPlaying()) return currentAnimation.getFrame();
        return 0;
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
        } catch (Exception e) {}
    }

    /**
     * Sets the sprite's image to a specific tile based on a linear index.
     *
     * @param spriteNum the index of the tile in the spritesheet (counted left to right, top to bottom)
     */
    public void setFrame(int spriteNum) {
        int coordX = spriteNum % sheetDim.x;
        int coordY = spriteNum / sheetDim.y;
        if (sheetDim.y != 1) coordY = spriteNum / sheetDim.y;
        else coordY = 0;
        BufferedImage currentFrame = sheet.getSubimage(coordX * frameSize.x, 
            coordY * frameSize.y, frameSize.x, frameSize.y);
        en.setImage(currentFrame);
    }

    /**
     * Sets the sprite's image to a specific tile based on a linear index.
     *
     * @param spriteNum the index of the tile in the spritesheet (counted left to right, top to bottom)
     */
    public void setFrame(int x, int y) {
        BufferedImage currentFrame = sheet.getSubimage(x * frameSize.x, 
            y * frameSize.y, frameSize.x, frameSize.y);
        en.setImage(currentFrame);
    }
}
