package game.utils;
import game.object.Vector;
import game.tile.entity.Entity;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
public class TestResourceSaver {
    Entity entity;

    @BeforeEach
    public void setUp() {
        entity = new Entity(null, new Vector());
        ResourceSaver rs = new ResourceSaver() {};
    }

    @Test
    public void testSaveAnimationPlayer() {
        AnimationPlayer animp = entity.getAnimationPlayer();
        assertTrue(ResourceSaver.saveAnimationPlayer(animp,"test"));
        

        ResourceLoader.loadAnimationPlayer(entity,"test");
        AnimationPlayer animp2 = entity.getAnimationPlayer();
        assertEquals(animp.sheetName, animp2.sheetName);
        assertEquals(animp.getAnimations().size(), animp2.getAnimations().size());
        File file = new File("src/main/resources/animation/test.animp");
        assertTrue(file.delete());
    }

    @Test
    public void testWriteAnimationToFile() {
        boolean result = false;
        AnimationPlayer animationPlayer = entity.getAnimationPlayer();
        animationPlayer.newAnimation("test", new int[]{0,1}, 2, 0.0f, true);
        try {
            String globalPath = String.format("src/main/resources/animation/test.animp");
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
                ResourceSaver.writeAnimationToFile(animationPlayer.getAnimations().get(key), file);
            }
            file.write("/.animp\n");
            file.close();
            result = true;
        } catch (Exception e) {result = false;}
        assertTrue(result);
        
        File file = new File("src/main/resources/animation/test.animp");
        assertTrue(file.delete());
    }

}
