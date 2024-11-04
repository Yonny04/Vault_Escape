// package vaultescape.map;

// import java.net.URL;

// import javax.sound.sampled.AudioInputStream;
// import javax.sound.sampled.AudioSystem;
// import javax.sound.sampled.Clip;

// public class BGM {
//     Clip clip;
//     URL musicURL[] = new URL[30];

//     public BGM() {
//         musicURL[0] = getClass().getResource("/map/bgm.mp3");
//     }

//     public void setFile(){
//         try {
//             AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[i]);
//             clip = AudioSystem.getClip();
//             clip.open(ais);

//         } catch (Exception ex) {
//         }
        
//     }
//     public void loop(){
//         clip.loop(Clip.LOOP_CONTINUOUSLY);
//     }
//     public void play(){
//         clip.start();
//     }
//     public void stop(){
//         clip.stop();
//     }
// }
