package vaultescape.audio;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SFX {

    private Clip clip;

    URL sfxURL[] = new URL[30];
    private Thread thread;
    private volatile boolean keepPlaying = true;

    public SFX() {
        sfxURL[0] = getClass().getClassLoader().getResource("audio/basic_collect.wav");
        sfxURL[1] = getClass().getClassLoader().getResource("audio/bonus_collect.wav");
        sfxURL[2] = getClass().getClassLoader().getResource("audio/game_complete.wav");
        sfxURL[3] = getClass().getClassLoader().getResource("audio/game_over.wav");
        sfxURL[4] = getClass().getClassLoader().getResource("audio/hit.wav");
        sfxURL[5] = getClass().getClassLoader().getResource("audio/new_record.wav");

        // Obtain audio input stream from the audio file and load the information
        // into main memory using the URL path retrieved from above.
    }
    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sfxURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ex) {
        }
            
    }
    public void loop(){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void play(int i){
        setFile(i);
        clip.start();
    }
    public void stop(){
        clip.stop();
    }
}