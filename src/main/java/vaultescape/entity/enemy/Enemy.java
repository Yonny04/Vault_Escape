package vaultescape.entity.enemy;

import vaultescape.entity.Entity;
import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

import java.util.Random;

/**
 * Represents a generic enemy entity in the game. 
 * Contains basic attributes and behaviors common to all enemies, such as movement speed and collision cooldown.
 */
public class Enemy extends Entity {

    protected Timer hitCooldown = new Timer(1);

    Random r = new Random();

    /**
     * Constructs an Enemy object associated with a specified game panel.
     *
     * @param gp the game panel associated with this entity
     */
    public Enemy(GamePanel gp, Vector2 start) {
        super(gp, start);
    }

    /**
     * Retrieves the current movement speed of the enemy.
     *
     * @return the speed of the enemy
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the movement speed of the enemy.
     *
     * @param value the new speed value to set
     */
    public void setSpeed(int value) {
        this.speed = value;
    }

    /**
     * Updates the enemy entity's behavior. This method is intended to be overridden by subclasses
     * with specific movement or attack logic for each type of enemy.
     */
    public void update() {
        // Update logic for the enemy would be implemented here
    }

    /**
     * Determines if the dog can collide, based on the cooldown period.
     *
     * @return true if the cooldown period has passed, false otherwise
     */
    public boolean canCollide() {return hitCooldown.isTimeUp();}

    /**
     * Records the time of the last collision event.
     */
    public void recordCollision() {hitCooldown.start();}
}
