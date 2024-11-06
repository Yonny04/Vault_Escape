package vaultescape.audio;

import javax.sound.sampled.*;

import java.net.URL;

/**
 * The SFX class handles sound effects for an application.
 * It can load, play, and stop audio clips specified by their names.
 */
public class SFX {
    
    private Clip clip; // Clip currently loaded

    /**
     * Default constructor for the SFX class.
     */
    public SFX() {}

    /**
     * Retrieves the URL for the specified sound effect name.
     * 
     * @param sfxName the name of the sound effect file (without extension)
     * @return the URL of the sound effect file
     */
    private URL getURL(String sfxName) {
        String path = String.format("audio/%s.wav", sfxName);
        return getClass().getClassLoader().getResource(path);
    }

    /**
     * Loads the sound effect specified by its name.
     * 
     * @param sfxName the name of the sound effect file (without extension)
     */
    public void loadSFX(String sfxName) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getURL(sfxName));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ex) {
            // Handle exception
        }
    }

    /**
     * Plays the sound effect specified by its name.
     * 
     * @param sfxName the name of the sound effect file (without extension)
     */
    public void play(String sfxName) {
        loadSFX(sfxName);
        clip.start();
    }

    /**
     * Stops the currently playing audio file.
     */
    public void stop() {
        clip.stop();
    }
}
