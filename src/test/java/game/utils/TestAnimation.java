package game.utils;

import org.junit.jupiter.api.*;

import static org.junit.Assert.*;

public class TestAnimation {
    private Animation animation;
    
    @BeforeEach
    public void setUp() {
        animation = new Animation("test", new int[]{2,1}, 
            2, 0.5f, true);
    }

    @Test
    public void testGetFrame() {
        animation.frame = 1.5f; // floored
        assertEquals(1, animation.getFrame());
    }
    @Test
    public void testStart() {
        animation.start();
        assertTrue(animation.isPlaying);
        assertEquals(2, animation.getFrame());
    }

    @Test
    public void testUpdateNotPlaying() {
        animation.stop();
        animation.update();
        assertEquals(0.0f, animation.frame,0.001f);
    }

    @Test
    public void testUpdateLoop() {
        animation.start();
        animation.frame = 2.0f - animation.frameInterval;
        animation.update();
        assertEquals(0.0f, animation.frame,0.001f);
    }

    @Test 
    public void testUpdateNoLoop() {
        animation.loop = false;
        animation.start();
        animation.frame = 2.0f - animation.frameInterval;
        animation.update();
        assertEquals(2.0f, animation.frame,0.001f);
    }
    
    @Test
    public void testUpdatePlaying() {
        animation.start();
        animation.update();
        assertEquals(animation.frameInterval, animation.frame,0.001f);
    }

    @Test
    public void testPlay() {
        animation.frame = 2.1f;
        assertTrue(animation.finished());
    }

    @Test
    public void testStop() {
        animation.stop();
        assertFalse(animation.isPlaying);
        assertEquals(2, animation.getFrame());
    }

    @Test
    public void testSetSpeed() {
        animation.setSpeed(0f);
        assertEquals(0f, animation.frameInterval,0.001f);
    }
    
}
