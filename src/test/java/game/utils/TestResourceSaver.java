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
    public void testSaveAnimationPlayerFromEntity() {
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
    public void testSaveAnimationPlayerNull() {
        AnimationPlayer animp = entity.getAnimationPlayer();
        assertFalse(ResourceSaver.saveAnimationPlayer(null,"test2"));

        ResourceLoader.loadAnimationPlayer(entity,"test2");
        AnimationPlayer animp2 = entity.getAnimationPlayer();
        assertEquals(animp.sheetName, animp2.sheetName);
        assertEquals(animp.getAnimations().size(), animp2.getAnimations().size());
        File file = new File("src/main/resources/animation/test2.animp");
        assertTrue(file.delete());
    }

    @Test
    public void testSaveAnimationPlayerWithSheet() {
        AnimationPlayer animationPlayer = entity.getAnimationPlayer();
        animationPlayer.setSpritesheet("player", 3, 3);
        animationPlayer.newAnimation("test3", new int[]{0,1}, 2, 0.0f, true);
        assertTrue(ResourceSaver.saveAnimationPlayer(animationPlayer, "test3"));
        File file = new File("src/main/resources/animation/test3.animp");
        assertTrue(file.delete());
    }

    @Test
    public void testWriteAnimationToFile() {
        try {
            String globalPath = "src/main/resources/animation/test4.animp";
            BufferedWriter file = new BufferedWriter(new FileWriter(globalPath));
            Animation animation = new Animation("test4", new int[]{0,1}, 2, 0.0f, true);
            assertTrue(ResourceSaver.writeAnimationToFile(animation, file));
            file.close();
            File file2 = new File("src/main/resources/animation/test4.animp");
            assertTrue(file2.delete());
        }
        catch (Exception e) {fail();}
    }

}
