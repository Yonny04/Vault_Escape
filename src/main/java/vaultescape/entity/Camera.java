package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

/**
 * Represents a Camera enemy entity in the game, which detects the player within a specified range.
 * When the player enters the detection range, the camera can trigger a detection event, causing
 * specific actions in the game, such as speeding up gameplay.
 */
public class Camera extends Enemy {
    private final int detectionRange;
    private long lastDetectionTime = 0;
    private static final long SPEEDUP_COOLDOWN = 3000;

    /**
     * Constructs a Camera object with specified game panel, position, and detection range.
     *
     * @param gp the game panel associated with this entity
     * @param x the x-coordinate of the camera's position
     * @param y the y-coordinate of the camera's position
     * @param detectionRange the range within which the camera can detect the player
     */
    public Camera(GamePanel gp, int x, int y, int detectionRange) {
        super(gp);
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
        this.detectionRange = detectionRange;
        setHitbox(16, 16);
        setSpritesheet("/entity/camera/spritesheet.png", 2, 3);
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

        if (isPlayerInRange() && camDetect()) {
            gp.setPlayerDetected(true);  // Set the flag in GamePanel
            recordDetection();           // Update last detection time
        }

        setFrame((int) spriteCounter);
    }

    /**
     * Checks if the player is within the camera's detection range.
     *
     * @return true if the player is within the detection range, false otherwise
     */
    public boolean isPlayerInRange() {
        int playerX = gp.getPlayer().getX();
        int playerY = gp.getPlayer().getY();
        return Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2)) <= detectionRange;
    }

    /**
     * Determines if the camera can trigger a detection event based on the cooldown timer.
     *
     * @return true if the cooldown period has passed since the last detection, false otherwise
     */
    public boolean camDetect() {
        long currentTime = System.currentTimeMillis();
        System.out.println("Player detected");
        return (currentTime - lastDetectionTime) >= SPEEDUP_COOLDOWN;
    }

    /**
     * Records the time of the last detection event, used to manage the detection cooldown period.
     */
    public void recordDetection() {
        lastDetectionTime = System.currentTimeMillis();
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
            g2.drawOval(screenX + width / 2 - detectionRange, screenY + height / 2 - detectionRange, detectionRange * 2, detectionRange * 2);
        }
    }
}
