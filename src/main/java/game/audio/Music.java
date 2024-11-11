package game.audio;

import javax.sound.sampled.*;

import java.net.URL;

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
     * Retrieves the URL for the specified music name.
     * 
     * @param musicName the name of the music file (without extension)
     * @return the URL of the music file
     */
    private URL getURL(String musicName) {
        String path = String.format("audio/music/%s.wav", musicName);
        return getClass().getClassLoader().getResource(path);
    }

    /**
     * Loads the music specified by its name.
     * 
     * @param musicName the name of the music file (without extension and path)
     */
    public void loadMusic(String musicName) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getURL(musicName));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ex) {}
    }

    /**
     * Plays the music specified by its name.
     * Loops the currently set music file continuously.
     * 
     * @param musicName the name of the music file (without extension)
     */
    public void play(String musicName) {
        if (clip != null) {clip.stop();clipName = "";}
        loadMusic(musicName);
        clip.start();
        clipName = musicName;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the currently playing music file.
     */
    public void stop() {
        clip.stop();
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

