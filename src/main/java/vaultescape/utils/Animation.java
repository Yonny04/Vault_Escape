package vaultescape.utils;

import vaultescape.ui.GamePanel;

public class Animation {
    int[] track;
    int frames;
    float duration = 1.0f;

    String name;
    float frame = 0.0f;
    float frameInterval;
    private boolean loop = false;

    boolean isPlaying = false;

    public Animation(GamePanel gp, String name, int[] track, int frames, float duration, boolean loop) {
        this.name = name;
        this.track = track;
        this.frames = frames;
        this.duration = duration;
        this.loop = loop;
        this.frameInterval = duration / (float)frames / 3.0f;
    }

    public int getFrame() {
        int nextFrame = (int)Math.floor((double)frame);
        return track[nextFrame];
    }

    public void start() {
        isPlaying = true;
        frame = 0.0f;
    }
    
    public void update() {
        if (isPlaying) {
            frame += frameInterval;
            if (finished()) { 
                if (loop) frame = 0.0f;
                else isPlaying = false;
            }
        }
    }

    public boolean finished() {
        return (frame + frameInterval >= (float)frames);
    }

    public void stop() {
        isPlaying = false;
        frame = 0.0f;
    }

    
}
