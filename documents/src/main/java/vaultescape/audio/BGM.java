package vaultescape.audio;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * The BGM class handles background music for an application.
 * It can load, play, loop, and stop audio clips specified by their URL paths.
 */
public class BGM {

    private Clip clip;
    URL musicURL[] = new URL[2];

    /**
     * Constructor for the BGM class.
     * Initializes the URL path for the background music.
     */
    public BGM() {
        musicURL[0] = getClass().getClassLoader().getResource("audio/music.wav");
        this.play(0);
        this.loop();
    }

    /**
     * Sets the audio file to be played.
     * @param i index of the music file to be set
     */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ex) {
            // Handle exception
        }
    }

    /**
     * Loops the currently set audio file continuously.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Plays the audio file corresponding to the given index.
     * @param i index of the music file to be played
     */
    public void play(int i) {
        setFile(i);
        clip.start();
    }

    /**
     * Stops the currently playing audio file.
     */
    public void stop() {
        clip.stop();
    }
}

