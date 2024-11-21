package game.utils;

import java.io.*;

/**
 * Utility class for saving resources such as animations.
 */
public class ResourceSaver {

    /**
     * Writes an animation to a file.
     * @param animation the animation to write
     * @param file the file to write to, does not close it
     * @return true if the animation was written successfully, false otherwise
     */
    public static boolean writeAnimationToFile(Animation animation, BufferedWriter file) {
        try {
            file.write(String.format("[ANIMATION]\n.anim::%s",animation.name));
            file.write(String.format("\n%d\n",animation.frames));
            for (int i = 0; i < animation.frames; i++) {
                file.write(String.format("%d",animation.track[i]));
                if (i < animation.frames - 1) file.write(",");
            }
            file.write(String.format("\n%f",animation.duration));
            file.write(String.format("\n%b",animation.loop));
            file.write("\n/.anim\n");
            return true;
        } catch (Exception e) {return false;}
    }

    /**
     * Saves an animation player to the given path. (custom file format .ani)
     * @param animationPlayer the animation player to save
     * @param name the name of the animation player
     * @return true if the animation player was saved successfully, false otherwise
     */
    public static boolean saveAnimationPlayer(AnimationPlayer animationPlayer, String name) {
        try {
            String globalPath = String.format("src/main/resources/animation/%s.animp",name);
            BufferedWriter file = new BufferedWriter(new FileWriter(globalPath));
            
            boolean hasSheet = animationPlayer.sheetName != null;
            file.write(String.format("[ANIMATION_PLAYER]\n.animp::%b",hasSheet));
            if (hasSheet) {
                file.write(String.format("\n%s",animationPlayer.sheetName));
                file.write(String.format("\n%d,%d",
                animationPlayer.sheetDim.x,animationPlayer.sheetDim.y));
            }
            
            file.write(String.format("\n%d\n",animationPlayer.getAnimations().size()));
            for (String key : animationPlayer.getAnimations().keySet()) {
                writeAnimationToFile(animationPlayer.getAnimations().get(key), file);
            }
            file.write("/.animp\n");
            file.close();
            return true;
        } catch (Exception e) {return false;}
    }
}
