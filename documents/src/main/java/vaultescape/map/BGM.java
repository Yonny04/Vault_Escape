package vaultescape.map;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BGM {
    Clip clip;
    URL musicURL[] = new URL[5];

    public BGM() {
        musicURL[0] = getClass().getResource("/map/BlueBoyAdventure.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play(){
        clip.start();
    }

    public void stop(){
        clip.stop();
    }
}
