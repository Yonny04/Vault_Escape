package vaultescape.entity.character.enemy;

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
        getAnimationPlayer().setSpritesheet("/entity/character/enemy/camera/spritesheet.png", 2, 3);
        getAnimationPlayer().setFrame(0);
        getAnimationPlayer().newAnimation("on", new int[]{0,1,2,3,4,5}, 6, 0.2f, true);
        getAnimationPlayer().playAnimation("on");
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
