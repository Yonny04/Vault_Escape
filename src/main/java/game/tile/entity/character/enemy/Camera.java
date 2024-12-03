package game.tile.entity.character.enemy;

import game.object.*;
import game.panel.GamePanel;
import game.tile.Tile;
import game.tile.entity.Entity;
import game.utils.*;

import java.awt.*;
import java.util.Random;




/**
 * Represents a Camera enemy entity in the game, which detects the player within a specified range.
 * When the player enters the detection range, the camera can trigger a detection event, causing
 * specific actions in the game, such as speeding up gameplay.
 */
public class Camera extends Enemy {
    // constants
    private static final int DEFAULT_DETECTION_RANGE = 96;
    private static final double SPOTLIGHT_TIMER_DURATION = 1.25;
    private static final int SPOTLIGHT_WIDTH = 128;
    private static final int SPOTLIGHT_HEIGHT = 72;
    private static final int SPOTLIGHT_OFFSET_X = 32;
    private static final int SPOTLIGHT_OFFSET_Y = 16;
    private static final int CAMERA_SPEED = 3;
    
    private Entity spotlight;
    private Timer spotlightTimer = new Timer(SPOTLIGHT_TIMER_DURATION);
    private boolean spotlightOn = false;

    /**
     * Constructs a Camera object with specified game panel, position, and detection range.
     *
     * @param gp the game panel associated with this entity
     * @param start the camera's starting position
     */
    public Camera(GamePanel gp, Vector start) {
        super(gp, start);
        this.range = DEFAULT_DETECTION_RANGE;
        
        shadow.hide();
        attackLabel.setColor(ColorPalette.PURPLE);
        animationPlayer.setSpritesheet("camera",3,4);
        animationPlayer.setFrame(0);
        setLayer(Layer.TOP);
        setupSpotlight();
        setSpeed(CAMERA_SPEED);
    }
    
    /**
     * Sets up the spotlight for the camera entity, 
     * which is the area that the camera can detect the player within.
     */
    private void setupSpotlight() {
        spotlight = new Entity(gp,rect.getPosition());
        spotlight.getShadow().hide();
        spotlight.setLayer(Layer.ORDERED);
        spotlight.setImage(ResourceLoader.loadSpritesheet("camera_spotlight"));
        spotlight.setSize(new Vector(SPOTLIGHT_OFFSET_X ,SPOTLIGHT_OFFSET_Y).scale(Vector.SCALE));
        spotlight.setHitbox(new Rect(0,-16,SPOTLIGHT_WIDTH ,SPOTLIGHT_HEIGHT));
        spotlight.hide();
        moveSpotlight();
    }

    private void moveSpotlight() {
        Random random = new Random();
        Vector next = new Vector(random.nextInt(-3,3),random.nextInt(0,3)+2).toGlobal();
        next = next.add(spotlight.getSize().scale(0.5));
        spotlight.setPosition(rect.add(next));
    }

    public Tile getSpotlight() {return spotlight;}

    @Override
    public void update() {
        if (spotlightTimer.isTimeUp()) {
            moveSpotlight();
            toggleSpotlight();
            int frame = spotlightOn ? 1:0;
            getAnimationPlayer().setFrame(frame,direction.ordinal());
        }
        super.update();
    }
    
    private void toggleSpotlight() {
        spotlightOn = !spotlightOn;
        spotlightTimer.start();
        if (spotlight.getRect().x < rect.x) setDirection(Direction.LEFT);
        if (spotlight.getRect().x > rect.x) setDirection(Direction.RIGHT);
        if (Math.abs(spotlight.getRect().y-rect.y) > Math.abs(spotlight.getRect().x-rect.x-128)) setDirection(Direction.DOWN);
        if (spotlightOn) spotlight.show();
        else spotlight.hide();
        spotlightTimer.setCountdownTime((double)4/speed);
    }
    /**
     * Draws the camera entity, including a red detection range indicator.
     *
     * @param g2 the Graphics2D object used to draw the camera and its detection range
     */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (drawCollisions) {
            g2.setColor(Color.red);
            g2.drawOval(screen.x + rect.w / 2 - range, screen.y + rect.h / 2 - range, range * 2, range * 2);
        }
        if (gp.introFade > 0) return;
        if ((double)(attackCooldown.getTimeLeft() / 1000.0) > 0.5 && !canAttack()) {
            attackLabel.setText(String.format("+1 Patrol",timeReduction));
            attackLabel.draw(g2,gp.getPlayer().getScreenPosition().subtract(new Vector(32,0)));
        }
    }

    @Override
    public boolean canAttack() {
        return (spotlightOn && spotlight.isTouchingPlayer() && attackCooldown.isTimeUp());
    }
    /**
     * Executes the attack action for the entity. 
     * If the current animation frame is an odd number (indicating the red light is on), it plays an alarm sound,
     * increases the speed of all enemies, and then performs the superclass's attack behavior.
     */
    @Override
    public void attack() {
        gp.getSFX().play("alarm");
        spotlightTimer.start();
        getAnimationPlayer().setFrame(2,direction.ordinal());
        gp.getEnemyGenerator().addEnemySpeed(1);
        super.attack();
    }
}
