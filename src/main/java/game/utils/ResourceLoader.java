package game.utils;

import game.tile.entity.Entity;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.*;
/**
 * Utility class for loading resources such as images.
 * 
 * Inspired by Godot's ResourceLoader class.
 */
public class ResourceLoader {

    /**
     * Loads an image from the given path.
     * @param path the path to the image file
     * @return the loaded image or null if the image could not be loaded
     */
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ResourceLoader.class.getResourceAsStream(path));
        } catch (Exception e) {return null;}
    }

    /**
     * Loads a spritesheet from the given path.
     * @param name the name of the spritesheet file
     * @return the loaded spritesheet or null if the spritesheet could not be loaded
     */
    public static BufferedImage loadSpritesheet(String name) {
        try {
            String path = String.format("/spritesheet/%s.png",name);
            BufferedImage sheet = ImageIO.read(ResourceLoader.class.getResourceAsStream(path));
            return sheet;
        } catch (Exception e) {return null;}
    }

    /**
     * Loads an audio clip from the given path.
     * @param audioType the type of audio to load
     * @param audioName the name of the audio file
     * @return the loaded audio clip or null if the audio could not be loaded
     */
    public static Clip loadAudio(Resource audioType,String audioName) {
        try {
            String audioTypeString = audioType.toString().toLowerCase();
            String globalPath = String.format("/audio/%s/%s.wav", 
                audioTypeString, audioName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                ResourceLoader.class.getResource(globalPath));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        } catch (Exception ex) {return null;}
    }

    /**
     * Loads a game file to read from the given path.
     * WARNING: This method loads .level and .animp files ONLY.
     * @param path the path to the file (including file format)
     * @return the loaded file to read, or null if the file could not be loaded
     */
    public static BufferedReader loadFile(String path) {
        try {
            InputStream stream = ResourceLoader.class.getResourceAsStream(path);
            BufferedReader file = new BufferedReader(new InputStreamReader(stream));
            return file;
        } catch (Exception e) {return null;}
    }

    /**
     * Loads the game font from the given path.
     * @return the loaded font or null if the font could not be loaded
     */
    public static Font loadFont(int size) {
        try {
            String path = "/ui/royal-intonation.ttf";
            InputStream stream = ResourceLoader.class.getResourceAsStream(path);
            Font font = Font.createFont(Font.TRUETYPE_FONT,stream).deriveFont(Font.PLAIN,size);
            return font;
        } catch (Exception e) {return null;}
    }

    /**
     * Loads an AnimationPlayer from the given path and configures the given entity.
     * @param entity the entity to load the AnimationPlayer for
     * @param name the name of the AnimationPlayer file
     */
    public static void loadAnimationPlayer(Entity entity, String name) {
        try {
            String globalPath = String.format("/animation/%s.animp",name);
            InputStream stream = ResourceLoader.class.getResourceAsStream(globalPath);
            BufferedReader file = new BufferedReader(new InputStreamReader(stream));

            AnimationPlayer animationPlayer = new AnimationPlayer(entity);
            file.readLine();
            boolean hasSheet = Boolean.parseBoolean(file.readLine().split("::")[1]);
            if (hasSheet) {
                String sheetPath = file.readLine();
                String[] sheetDim = file.readLine().split(",");
                animationPlayer.setSpritesheet(sheetPath,
                Integer.parseInt(sheetDim[0]),Integer.parseInt(sheetDim[1]));
            }
            int animationCount = Integer.parseInt(file.readLine());
            for (int i = 0; i < animationCount; i++) {
                Animation animation = readAnimationFromFile(file);
                animationPlayer.newAnimation(animation);
            }
            
            file.readLine();
            file.close();
            entity.setAnimationPlayer(animationPlayer);
            
        } catch (Exception e) {}
    }

    /**
     * Reads an Animation from the given file.
     * @param file the file to read the Animation from
     * @return the Animation read from the file or null if the Animation could not be read
     */
    private static Animation readAnimationFromFile(BufferedReader file) {
        try {
            file.readLine();
            String name = file.readLine().split("::")[1];
            int frames = Integer.parseInt(file.readLine());
            int[] track = new int[frames];
            String[] trackString = file.readLine().split(",");
                for (int i = 0; i < frames; i++) {
                    track[i] = Integer.parseInt(trackString[i]);
                }
            float duration = Float.parseFloat(file.readLine());
            boolean loop = Boolean.parseBoolean(file.readLine());
            file.readLine();
            Animation animation = new Animation(name,track,frames,duration,loop);
            return animation;
        } catch (Exception e) {return null;}
    }

    /**
     * Enum for specifying the type of resource to load.
     */
    public enum Resource {SFX,MUSIC};
}