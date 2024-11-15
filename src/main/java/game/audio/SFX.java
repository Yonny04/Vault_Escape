package game.audio;

import game.utils.ResourceLoader;
import game.utils.ResourceLoader.Resource;

import javax.sound.sampled.Clip;

/**
 * The SFX class handles sound effects for an application.
 * It can load, play, and stop audio clips specified by their names.
 */
public class SFX {
    
    private Clip clip; // Clip currently loaded
    private String clipName = "";

    /**
     * Default constructor for the SFX class.
     */
    public SFX() {}
    

    /**
     * Plays the sound effect specified by its name.
     * 
     * @param sfxName the name of the sound effect file (without extension)
     */
    public void play(String sfxName) {
        clip = ResourceLoader.loadAudio(Resource.SFX,sfxName);
        clip.start();
        clipName = sfxName;
    }

    /**
     * Loop the sound effect (count) amount of times
     * @param count the number of times to loop the sound effect file
     */
    public void loop(int count) {
        clip.loop(count);
    }

    /**
     * Stops the currently playing audio file.
     */
    public void stop() {clip.stop();}

    /**
     * Checks whether the given music file is playing.
     * @param sfxName the music name to check (no path and .wav)
     * @return true if the musicName matches what is currently playing
     */
    public boolean isPlaying(String sfxName) {
        return (sfxName == clipName && clip.isRunning());
    }
}
