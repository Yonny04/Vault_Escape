package vaultescape.entity.enemy;

import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

import java.awt.*;

/**
 * Represents a Camera enemy entity in the game, which detects the player within a specified range.
 * When the player enters the detection range, the camera can trigger a detection event, causing
 * specific actions in the game, such as speeding up gameplay.
 */
public class Camera extends Enemy {
    private int detectionRange;

    /**
     * Constructs a Camera object with specified game panel, position, and detection range.
     *
     * @param gp the game panel associated with this entity
     * @param start the camera's starting position
     * @param detectionRange the range within which the camera can detect the player
     */
    public Camera(GamePanel gp, Vector2 start, int detectionRange) {
        super(gp,start);
        this.detectionRange = detectionRange;
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
        spriteCounter += 0.02f;
        if (spriteCounter > 5.98) spriteCounter = 0.0f;

        if (isPlayerInRange() && canCollide()) {
            recordCollision(); // Update last detection time
        }

        setFrame((int) spriteCounter);
    }

    /**
     * Checks if the player is within the camera's detection range.
     *
     * @return true if the player is within the detection range, false otherwise
     */
    public boolean isPlayerInRange() {
        Rect2 player = gp.getPlayer().getRect();
        if ((int)Math.floor(spriteCounter) == 0) return false;
        return Math.sqrt(Math.pow(player.x - rect.x, 2) + Math.pow(player.y - rect.y, 2)) <= detectionRange;
    }

    /**
     * Draws the camera entity, including a red detection range indicator.
     *
     * @param g2 the Graphics2D object used to draw the camera and its detection range
     */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (_drawCollisions) {
            g2.setColor(Color.red);
            g2.drawOval(screen.x + rect.w / 2 - detectionRange, screen.y + rect.h / 2 - detectionRange, detectionRange * 2, detectionRange * 2);
        }
    }
}
