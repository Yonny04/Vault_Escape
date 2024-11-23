package game.utils;


import game.object.Vector;
import game.tile.entity.Entity;
import game.utils.ResourceLoader.Resource;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestResourceLoader {
    private Entity entity;
    private static final String TEST_IMAGE_PATH = "/spritesheet/parallax.png";
    private static final String TEST_SPRITESHEET_NAME = "parallax";
    private static final String TEST_ANIMATION_PLAYER_NAME = "player";
    private static final String TEST_MUSIC_PATH = "music";
    private static final String TEST_SFX_PATH = "select";
    private static final String TEST_FILE_PATH = "/level/1.level";

    @BeforeEach
    public void setUp() {
        entity = new Entity(null, new Vector());
        entity.setAnimationPlayer(null);
        @SuppressWarnings("unused") ResourceLoader resourceLoader = new ResourceLoader() {};
        @SuppressWarnings("unused") ColorPalette colorPalette = new ColorPalette() {}; // for constructor
    }

    @Test
    public void testLoadImage() {
        assertNotNull(ResourceLoader.loadImage(TEST_IMAGE_PATH));
    }

    @Test
    public void testLoadSpritesheet() {
        assertNotNull(ResourceLoader.loadSpritesheet(TEST_SPRITESHEET_NAME));
    }

    @Test
    public void testLoadMusic() {
        assertNotNull(ResourceLoader.loadAudio(Resource.MUSIC,TEST_MUSIC_PATH));
    }

    @Test
    public void testLoadFile() {
        assertNotNull(ResourceLoader.loadFile(TEST_FILE_PATH));
    }

    @Test
    public void testIncorrectLoadFile() {
        assertNull(ResourceLoader.loadFile("incorrect"));
    }

    @Test
    public void testLoadSFX() {
        assertNotNull(ResourceLoader.loadAudio(Resource.SFX,TEST_SFX_PATH));
    }
    @Test
    public void testLoadAnimationPlayer() {
        ResourceLoader.loadAnimationPlayer(entity, TEST_ANIMATION_PLAYER_NAME);
        assertNotNull(entity.getAnimationPlayer());
    }

    @Test
    public void testLoadAnimationPlayerIncorrect() {
        entity.setAnimationPlayer(null);
        ResourceLoader.loadAnimationPlayer(entity, "p");
        assertNull(entity.getAnimationPlayer());
    }

    @Test
    public void testLoadFont() {
        int fontSize = 32;
        assertNotNull(ResourceLoader.loadFont(fontSize));
    }
}
