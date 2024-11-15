package game.tile.entity.character.enemy;

import game.object.Vector;
import game.panel.GamePanel;
import game.utils.*;

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
     */
    public Camera(GamePanel gp, Vector start) {
        super(gp, start);
        this.range = 128;
        getAnimationPlayer().setSpritesheet("camera", 2, 3);
        getAnimationPlayer().newAnimation(ResourceLoader.loadAnimation("camera"));
        getAnimationPlayer().playAnimation("move");
        getAnimationPlayer().setFrame(0);
        attackLabel.setColor(ColorPalette.PURPLE);
    }

    @Override
    public void update() {
        if (isPlayerInRange() && canAttack()) {
            attack();
        }
        super.update();
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
        if ((double)(attackCooldown.getTimeLeft() / 1000.0) > 0.5 && !canAttack()) {
            attackLabel.setText(String.format("+1 Patrol",timeReduction));
            attackLabel.draw(g2,gp.getPlayer().getScreenPosition().subtract(new Vector(32,0)));
        }
    }
   
    /**
     * Executes the attack action for the entity. 
     * If the current animation frame is an odd number (indicating the red light is on), it plays an alarm sound,
     * increases the speed of all enemies, and then performs the superclass's attack behavior.
     */
    @Override
    public void attack() {
        if (getAnimationPlayer().getFrame() % 2 == 1) { // When red light on
            gp.getSFX().play("alarm");
            gp.getEnemyGenerator().addEnemySpeed(1);
            super.attack();
        }
    }

}
