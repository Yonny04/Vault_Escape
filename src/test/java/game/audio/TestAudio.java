package game.audio;

import game.audio.Music;
import game.audio.SFX;
import game.utils.ResourceLoader;
import game.utils.ResourceLoader.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Music and SFX classes.
 */
class TestAudio {

    private Music music;
    private SFX sfx;

    @BeforeEach
    void setUp() {
        music = new Music();
        sfx = new SFX();
    }

    @Test
    void testMusicPlay() {
        String musicName = "music";
        Clip clip = ResourceLoader.loadAudio(Resource.MUSIC, musicName);
        assertNotNull(clip);
        music.play(musicName);
        assertTrue(music.isPlaying(musicName));
    }
    
    @Test
    void testMusicStop() {
        String musicName = "music";
        music.play(musicName);
        music.stop();
        assertFalse(music.isPlaying(musicName));
    }

    @Test
    void testSFXPlay() throws InterruptedException {
        String sfxName = "alarm";
        Clip clip = ResourceLoader.loadAudio(Resource.SFX, sfxName);
        assertNotNull(clip);

        sfx.play(sfxName);
        Thread.sleep(100);
        assertTrue(sfx.isPlaying(sfxName));
    }
    @Test
    void testSFXLoop() {
        String sfxName = "alarm";
        sfx.play(sfxName);
        assertDoesNotThrow(() -> sfx.loop(3), "break");
    }

    @Test
    void testSFXStop() {
        String sfxName = "alarm";
        sfx.play(sfxName);
        sfx.stop();
        assertFalse(sfx.isPlaying(sfxName));
    }
}
