package game.tile;

import game.App;
import game.object.Vector;
import game.panel.GamePanel;
import game.tile.entity.character.enemy.*;
import game.utils.Timer;
import org.junit.jupiter.api.*;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestEnemy {

    App app = new App(false);
    GamePanel gp;
    Enemy enemy;
    Guard guard;
    Dog dog;
    Camera camera;
    Laser laser;

    @BeforeEach
    public void setUp() throws Exception {
        gp = new GamePanel(app,1);
        Field timer = GamePanel.class.getDeclaredField("timer");
        timer.setAccessible(true);
        timer.set(gp, new Timer(40));
        guard = new Guard(gp, new Vector());
        dog = new Dog(gp, new Vector());
        camera = new Camera(gp, new Vector());
        laser = new Laser(gp, new Vector());
        enemy = new Enemy(gp, new Vector());
    }

    @Test
    public void testIsPlayerInRangeFalse(){
        assertFalse(enemy.isPlayerInRange());
    }

    @Test
    public void testIsPlayerInRangeTrue(){
        enemy.setPosition(0,0);
        gp.getPlayer().setPosition(0,0);
        assertTrue(enemy.isPlayerInRange());
    }

    @Test
    public void testDogCanAttack() {
        assertFalse(dog.canAttack());
    }

    @Test
    public void testDogWander() throws Exception {
        Method wander = Dog.class.getDeclaredMethod("wander");
        wander.setAccessible(true);
        wander.invoke(dog);
        assertEquals(4*3,dog.getAnimationPlayer().getFrame());
    }

    @Test
    public void testDogChase() throws Exception {
        Method chase = Dog.class.getDeclaredMethod("chasePlayer");
        chase.setAccessible(true);
        chase.invoke(dog);
        assertEquals(4,dog.getAnimationPlayer().getFrame());
    }

    @Test
    public void testDogUpdateCooldown() {
        dog.update();
        assertEquals(1+4*3,dog.getAnimationPlayer().getFrame());
    }

    @Test
    public void testDogUpdateWander() throws Exception {
        Field biteCooldown = Dog.class.getDeclaredField("biteCooldown");
        biteCooldown.setAccessible(true);
        biteCooldown.set(dog, new Timer(0.0));
        dog.update();
        assertEquals(4*3,dog.getAnimationPlayer().getFrame());
    }

    @Test
    public void testDogUpdateChase() throws Exception {
        Field biteCooldown = Dog.class.getDeclaredField("biteCooldown");
        biteCooldown.setAccessible(true);
        biteCooldown.set(dog, new Timer(0.0));
        dog.setPosition(gp.getPlayer().getPosition());
        dog.update();
        assertTrue(dog.isTouchingPlayer());
    }

    @Test
    public void testCameraGetSpotlight() {
        assertNotNull(camera.getSpotlight());
    }

    @Test
    public void testCameraUpdate() throws Exception {
        Field timer = Camera.class.getDeclaredField("spotlightTimer");
        timer.setAccessible(true);
        timer.set(camera, new Timer(0.0));
        camera.update();
        Field spotlightOn = camera.getClass().getDeclaredField("spotlightOn");
        spotlightOn.setAccessible(true);
        assertTrue((boolean) spotlightOn.get(camera));
    }
    @Test
    public void testCameraToggleSpotlightOn() throws Exception {
        Method toggleSpotlight = Camera.class.getDeclaredMethod("toggleSpotlight");
        toggleSpotlight.setAccessible(true);
        toggleSpotlight.invoke(camera);

        Field spotlightOn = camera.getClass().getDeclaredField("spotlightOn");
        spotlightOn.setAccessible(true);
        assertTrue((boolean) spotlightOn.get(camera));
    }

    @Test
    public void testCameraCanAttack() {
        assertFalse(camera.canAttack());
    }

    @Test
    public void testLaserCanAttack() {
        assertFalse(laser.canAttack());
    }
    @Test
    public void testGuardAttack() {
        guard.attack();
        assertFalse(guard.canAttack());
    }
    @Test
    public void testDogAttack() {
        dog.attack();
        assertFalse(dog.canAttack());
    }
    @Test
    public void testCameraAttack() {
        camera.attack();
        assertFalse(camera.canAttack());
    }
    @Test
    public void testLaserAttack() {
        laser.attack();
        assertFalse(laser.canAttack());
    }

    @Test
    public void testLaserUpdate() {
        laser.update();
        assertEquals(9,laser.getAnimationPlayer().getFrame());
    }
}
