package game.utils;

import game.object.Vector;
import game.panel.GamePanel;
import game.tile.entity.Entity;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Manages animations for an entity, including playing, stopping, and updating animations.
 */
public class AnimationPlayer {

    GamePanel gp;
    Entity en;
    
    protected BufferedImage sheet;
    protected Vector sheetDim;
    protected Vector frameSize;
    
    public int currentFrame = 0;
    public int lastFrame = 0;

    private Animation currentAnimation = null;
    private Map<String, Animation> animations = new HashMap<>();

    /**
     * Constructs an AnimationPlayer with the specified game panel and entity.
     *
     * @param gp The game panel associated with this animation player.
     * @param en The entity associated with this animation player.
     */
    public AnimationPlayer(GamePanel gp, Entity en) {
        this.gp = gp;
        this.en = en;
    }

    /**
     * Checks if an animation is currently playing.
     *
     * @return true if an animation is playing, false otherwise.
     */
    public boolean isPlaying() {
        return (currentAnimation != null);
    }

    /**
     * Checks if the current animation has finished playing.
     *
     * @return true if the current animation has finished, false otherwise.
     */
    public boolean finished() {
        if (isPlaying()) {
            return currentAnimation.finished();
        }
        return false;
    }

    /**
     * Creates a new animation and adds it to the list of animations.
     *
     * @param name     The name of the animation.
     * @param track    An array representing the frame sequence of the animation.
     * @param frames   The number of frames in the animation.
     * @param duration The duration of the animation in seconds.
     * @param loop     Whether the animation should loop.
     */
    public void newAnimation(String name, int[] track, int frames, float duration, boolean loop) {
        Animation animation = new Animation(gp, name, track, frames, duration, loop);
        animations.put(name, animation);
    }

    /**
     * Plays the specified animation by name. If an animation is currently playing, it will be stopped.
     *
     * @param name The name of the animation to play.
     */
    public void playAnimation(String name) {
        if (isPlaying()) {
            if (currentAnimation.name.equals(name)) {
                return;
            } else {
                currentAnimation.stop();
            }
        }
        this.currentAnimation = animations.get(name);
        this.currentAnimation.start();
    }

    /**
     * Stops the currently playing animation.
     */
    public void stopAnimation() {
        if (isPlaying()) {
            this.currentAnimation.stop();
        }
        this.currentAnimation = null;
    }

    /**
     * Updates the current animation, advancing the frame if the animation is playing.
     */
    public void update() {
        this.lastFrame = this.currentFrame;
        if (isPlaying()) {
            if (currentAnimation.isPlaying) {
                this.currentAnimation.update();
            }
            this.currentFrame = getFrame();
            if (currentFrame != lastFrame) {
                setFrame(currentFrame);
            }
        }
    }

    /**
     * Returns the current frame of the animation.
     *
     * @return The index of the current frame in the track.
     */
    public int getFrame() {
        if (isPlaying()) {
            return currentAnimation.getFrame();
        }
        return 0;
    }

    /**
     * Sets the sprite image to a spritesheet and defines its tile dimensions.
     *
     * @param path     The path to the spritesheet resource (e.g., "/map/spritesheet.png").
     * @param tilesX   The number of tiles horizontally on the spritesheet.
     * @param tilesY   The number of tiles vertically on the spritesheet.
     */
    public void setSpritesheet(String path, int tilesX, int tilesY) {
        try {
            sheet = ImageIO.read(getClass().getResourceAsStream(path));
            sheetDim = new Vector(tilesX, tilesY);
            frameSize = new Vector(sheet.getWidth() / sheetDim.x, sheet.getHeight() / sheetDim.y);
        } catch (Exception e) {
            e.printStackTrace();  // Consider adding this for debugging
        }
    }

    /**
     * Sets the sprite's image to a specific tile based on a linear index.
     *
     * @param frame The index of the tile in the spritesheet (counted left to right, top to bottom).
     */
    public void setFrame(int frame) {
        int coordX = frame % sheetDim.x;
        int coordY = frame / sheetDim.y;
        if (sheetDim.y != 1) {
            coordY = frame / sheetDim.y;
        } else {
            coordY = 0;
        }
        BufferedImage currentFrame = sheet.getSubimage(coordX * frameSize.x, 
                coordY * frameSize.y, frameSize.x, frameSize.y);
        en.setImage(currentFrame);
    }

    /**
     * Sets the sprite's image to a specific tile based on x and y coordinates in the spritesheet.
     *
     * @param frameX The x-coordinate of the tile in the spritesheet.
     * @param frameY The y-coordinate of the tile in the spritesheet.
     */
    public void setFrame(int frameX, int frameY) {
        BufferedImage currentFrame = sheet.getSubimage(frameX * frameSize.x, 
                frameY * frameSize.y, frameSize.x, frameSize.y);
        en.setImage(currentFrame);
    }
}
