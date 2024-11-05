package vaultescape.audio;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * The SFX class handles sound effects for an application.
 * It can load, play, and stop audio clips specified by their URL paths.
 */
public class SFX {

    private Clip clip;
    URL sfxURL[] = new URL[8];
    public boolean is_playing = false;
    /**
     * Constructor for the SFX class.
     * Initializes the URL paths for different sound effects.
     */
    public SFX() {
        sfxURL[0] = getClass().getClassLoader().getResource("audio/basic_collect.wav");
        sfxURL[1] = getClass().getClassLoader().getResource("audio/bonus_collect.wav");
        sfxURL[2] = getClass().getClassLoader().getResource("audio/exit_open.wav");
        sfxURL[3] = getClass().getClassLoader().getResource("audio/game_complete.wav");
        sfxURL[4] = getClass().getClassLoader().getResource("audio/game_over.wav");
        sfxURL[5] = getClass().getClassLoader().getResource("audio/hit.wav");
        sfxURL[6] = getClass().getClassLoader().getResource("audio/new_record.wav");
        sfxURL[7] = getClass().getClassLoader().getResource("audio/music.wav");
    }

    /**
     * Sets the audio file to be played.
     * @param i index of the sound effect to be set
     */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sfxURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ex) {}
    }

    /**
     * Plays the audio file corresponding to the given index.
     * @param i index of the sound effect to be played
     */
    public void play(int i) {
        setFile(i);
        clip.start();
        is_playing = true;
    }

    /**
     * Stops the currently playing audio file.
     */
    public void stop() {
        clip.stop();
        is_playing = false;
    }
}
