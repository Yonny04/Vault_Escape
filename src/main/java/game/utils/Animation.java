package game.utils;

import game.panel.GamePanel;

/**
 * Represents an animation in the game, managing the frames and playback of the animation.
 */
public class Animation {
    int[] track;
    int frames;
    float duration = 1.0f;

    String name;
    float frame = 0.0f;
    float frameInterval;
    private boolean loop = false;

    boolean isPlaying = false;

    /**
     * Constructs an Animation with the specified parameters.
     *
     * @param gp        The game panel associated with this animation.
     * @param name      The name of the animation.
     * @param track     An array representing the frame sequence of the animation.
     * @param frames    The number of frames in the animation.
     * @param duration  The duration of the animation in seconds.
     * @param loop      Whether the animation should loop.
     */
    public Animation(GamePanel gp, String name, int[] track, int frames, float duration, boolean loop) {
        this.name = name;
        this.track = track;
        this.frames = frames;
        this.duration = duration;
        this.loop = loop;
        this.frameInterval = duration / (float)frames / 3.0f;
    }

    /**
     * Returns the current frame of the animation.
     *
     * @return The index of the current frame in the track.
     */
    public int getFrame() {
        int nextFrame = (int)Math.floor((double)frame);
        return track[nextFrame];
    }

    /**
     * Starts the animation, resetting the frame counter.
     */
    public void start() {
        isPlaying = true;
        frame = 0.0f;
    }

    /**
     * Updates the animation, advancing the frame based on the frame interval.
     * If the animation is finished and looping is enabled, it resets the frame counter.
     * If looping is not enabled, it stops the animation.
     */
    public void update() {
        if (!isPlaying) return;
        frame += frameInterval;
        if (!finished()) return;
        if (loop) frame = 0.0f;
        else isPlaying = false;
    }

    /**
     * Checks if the animation has finished playing all frames.
     *
     * @return true if the animation has finished, false otherwise.
     */
    public boolean finished() {
        return (frame + frameInterval >= (float)frames);
    }

    /**
     * Stops the animation and resets the frame counter.
     */
    public void stop() {
        isPlaying = false;
        frame = 0.0f;
    }
}
