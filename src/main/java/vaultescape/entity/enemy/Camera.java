package vaultescape.entity.enemy;

import vaultescape.ui.GamePanel;
import vaultescape.utils.Vector;

import java.awt.*;

/**
 * Represents a Camera enemy entity in the game, which detects the player within a specified range.
 * When the player enters the detection range, the camera can trigger a detection event, causing
 * specific actions in the game, such as speeding up gameplay.
 */
public class Camera extends Enemy {

    /**
     * Constructs a Camera object with specified game panel, position, and detection range.
     *
     * @param gp the game panel associated with this entity
     * @param start the camera's starting position
     * @param detectionRange the range within which the camera can detect the player
     */
    public Camera(GamePanel gp, Vector start) {
        super(gp, start);
        this.range = 50;
        setSpritesheet("/entity/enemy/camera/spritesheet.png", 2, 3);
        setFrame(0);
    }

    /**
     * Updates the camera logic, including sprite animation and player detection.
     * If the player is within detection range and detection cooldown has passed, the camera
     * triggers a detection event and updates the last detection time.
     */
    @Override
    public void update() {
        frame += 0.02f;
        if (frame > 5.98) frame = 0.0f;

        if (isPlayerInRange() && canAttack()) {
            attack(); // Update last detection time
        }

        setFrame((int) frame);
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
    }
    @Override
    public void attack() {
        gp.getSFX().play("alarm");
        gp.getEnemyGenerator().addEnemySpeed(1);
        super.attack();
    }
}
