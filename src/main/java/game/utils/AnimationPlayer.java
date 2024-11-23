package game.utils;

import game.object.Vector;
import game.tile.entity.Entity;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Manages animations for an entity, including playing, stopping, and updating animations.
 */
public class AnimationPlayer {
    private Entity entity;
    
    protected BufferedImage sheet;
    public String sheetName;
    public Vector sheetDim;

    private Vector frameSize;

    public int currentFrame = 0;
    public int lastFrame = 0;

    private Animation currentAnimation = null;
    private Map<String, Animation> animations = new HashMap<>();

    /**
     * Constructs an AnimationPlayer with the specified game panel and entity.
     *
     */
    public AnimationPlayer(Entity entity) {
        setEntity(entity);
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
     * @param speed    The speed at which the animation should play.
     * @param loop     Whether the animation should loop.
     */
    public void newAnimation(String name, int[] track, int frames, float speed, boolean loop) {
        Animation animation = new Animation(name, track, frames, speed, loop);
        animations.put(name, animation);
    }

    public void newAnimation(Animation animation) {
        animations.put(animation.name, animation);
    }

    /**
     * Plays the specified animation by name. If an animation is currently playing, it will be stopped.
     *
     * @param name The name of the animation to play.
     */
    public void playAnimation(String name) {
        if (isPlaying()) {
            if (currentAnimation.name.equals(name)) return;
            else currentAnimation.stop();
        }
        this.currentAnimation = animations.get(name);
        if (this.currentAnimation == null) return;
        this.currentAnimation.start();
    }

    /**
     * Stops the currently playing animation.
     */
    public void stopAnimation() {
        if (isPlaying()) this.currentAnimation.stop();
        this.currentAnimation = null;
    }

    public void setAnimationSpeed(float speed) {
        if (isPlaying()) currentAnimation.setSpeed(speed);
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
        if (isPlaying()) return currentAnimation.getFrame();
        return this.currentFrame;
    }

    /**
     * Sets the sprite image to a spritesheet and defines its tile dimensions.
     *
     * @param name     The name of the spritesheet file in the spritesheet folder.
     * @param tilesX   The number of tiles horizontally on the spritesheet.
     * @param tilesY   The number of tiles vertically on the spritesheet.
     */
    public void setSpritesheet(String name, int tilesX, int tilesY) {
        try {
            sheet = ResourceLoader.loadSpritesheet(name);
            sheetName = name;
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
        this.currentFrame = frame;
        int coordX = frame % sheetDim.x;
        int coordY = frame / sheetDim.y;
        if (sheetDim.y != 1) {
            coordY = frame / sheetDim.y;
        } else {
            coordY = 0;
        }
        BufferedImage currentFrame = sheet.getSubimage(coordX * frameSize.x, 
                coordY * frameSize.y, frameSize.x, frameSize.y);
        if (entity != null) entity.setImage(currentFrame);
    }

    /**
     * Sets the sprite's image to a specific tile based on x and y coordinates in the spritesheet.
     *
     * @param frameX The x-coordinate of the tile in the spritesheet.
     * @param frameY The y-coordinate of the tile in the spritesheet.
     */
    public void setFrame(int frameX, int frameY) {
        this.currentFrame = frameX + frameY * sheetDim.x;
        BufferedImage currentFrame = sheet.getSubimage(frameX * frameSize.x, 
                frameY * frameSize.y, frameSize.x, frameSize.y);
        if (entity != null) entity.setImage(currentFrame);
    }

    /**
     * Sets the entity associated with this animation player.
     * @param entity the entity to set
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Map<String,Animation> getAnimations() {
        return animations;
    }
}
