package vaultescape.audio;

import javax.sound.sampled.*;

import java.net.URL;

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
     * Loads the audio file into clip to be played.
     * @param i index of the music file to be set
     */
    public void loadBGM(int i) {
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
        loadBGM(i);
        clip.start();
    }

    /**
     * Stops the currently playing audio file.
     */
    public void stop() {
        clip.stop();
    }
}

