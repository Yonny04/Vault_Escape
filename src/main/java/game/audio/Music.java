package game.audio;

import game.utils.ResourceLoader;
import game.utils.ResourceLoader.Resource;

import javax.sound.sampled.Clip;

/**
 * The Music class handles background music for an application.
 * It can load, play, loop, and stop audio clips specified by their URL paths.
 */
public class Music {

    private String clipName = "";
    private Clip clip;

    /**
     * Default constructor for the Music class.
     */
    public Music() {}

    /**
     * Plays the music specified by its name.
     * Loops the currently set music file continuously.
     * 
     * @param musicName the name of the music file (without extension)
     */
    public void play(String musicName) {
        if (clip != null) {clip.stop();clipName = "";}
        clip = ResourceLoader.loadAudio(Resource.MUSIC,musicName);
        clip.start();
        clipName = musicName;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the currently playing music file.
     */
    public void stop() {
        clip.stop();
        clipName = "";
    }

    /**
     * Checks whether the given music file is playing.
     * @param musicName the music name to check (no path and .wav)
     * @return true if the musicName matches what is currently playing
     */
    public boolean isPlaying(String musicName) {
        return (musicName == clipName);
    }
}

