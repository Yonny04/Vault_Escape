// package vaultescape.map;

// import java.io.File;

// import javax.sound.sampled.AudioInputStream;
// import javax.sound.sampled.AudioSystem;
// import javax.sound.sampled.Clip;

// public class BGM {
//     private Clip clip;

//     // Load music from a file
//     public void loadMusic(File musicFile) {
//         try {
//             if (clip != null && clip.isRunning()) {
//                 clip.stop(); // Stop the previous clip if it's running
//             }
//             AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
//             clip = AudioSystem.getClip();
//             clip.open(audioInput);
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//     }

//     public void playMusic() {
//         if (clip != null) {
//             clip.start();
//             clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop continuously
//         }
//     }

//     public void stopMusic() {
//         if (clip != null && clip.isRunning()) {
//             clip.stop();
//             clip.close();
//         }
//     }
// }
