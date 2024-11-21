package game.utils;
import game.object.Vector;
import game.tile.entity.Entity;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
public class TestAnimationPlayer {
    private AnimationPlayer animationPlayer;
    private Animation animation;
    
    @BeforeEach
    public void setUp() {
        animationPlayer = new AnimationPlayer(null);
        animation = new Animation("test", new int[]{2,1}, 2, 0.5f, true);
        animationPlayer.setSpritesheet("player", 4, 4);
        animationPlayer.newAnimation(animation);
    }
    
    @Test
    public void testPlayAnimation() {
        animationPlayer.playAnimation("test");
        assertTrue(animationPlayer.isPlaying());
        assertTrue(animationPlayer.getAnimations().get("test").isPlaying);
    }

    @Test
    public void testPlayAnimationAlreadyPlaying() {
        animationPlayer.playAnimation("test");
        animationPlayer.playAnimation("test");
        assertTrue(animationPlayer.isPlaying());
        assertTrue(animationPlayer.getAnimations().get("test").isPlaying);
    }

    @Test
    public void testPlayAnimationNull() {
        animationPlayer.playAnimation("nonexistentAnimation");
        assertFalse(animationPlayer.isPlaying());
    }

    @Test
    public void testFinished() {
        animationPlayer.playAnimation("test");
        assertFalse(animationPlayer.finished());
    }

    @Test
    public void testFinishedNull() {
        animationPlayer.playAnimation("nonexistentAnimation");
        assertFalse(animationPlayer.finished());
    }

    @Test
    public void testSetFrameCoordsNullEntity() {
        animationPlayer.setFrame(1, 1);
    }
    
    @Test
    public void testSetFrameCoords() {
        animationPlayer.setEntity(new Entity(null,new Vector()));
        animationPlayer.setFrame(1, 1);
    }

    @Test
    public void testStopAnimation() {
        animationPlayer.playAnimation("test");
        animationPlayer.stopAnimation();
        assertFalse(animationPlayer.isPlaying());
        assertFalse(animationPlayer.getAnimations().get("test").isPlaying);
    }

    @Test
    public void testUpdatePlaying() {
        animationPlayer.playAnimation("test");
        animationPlayer.update();
        assertEquals(animation.frameInterval, animation.frame, 0.001f);
    }

    @Test 
    public void testUpdateNotPlaying() {
        animationPlayer.stopAnimation();
        animationPlayer.update();
        assertFalse(animationPlayer.isPlaying());
        assertEquals(0, animation.frame, 0.001f);
    }

    @Test 
    public void testSetAnimationSpeed() {
        animationPlayer.playAnimation("test");
        animationPlayer.setAnimationSpeed(1.0f);
        double expected = 1.0f / 2f / 3.0f;
        assertEquals(expected, animationPlayer.getAnimations().get("test").frameInterval, 0.001f);
    }

}
