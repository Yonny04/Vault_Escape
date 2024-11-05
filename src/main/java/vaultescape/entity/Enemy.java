package vaultescape.entity;

import java.util.Random;
import vaultescape.map.GamePanel;

/**
 * Represents a generic enemy entity in the game. 
 * Contains basic attributes and behaviors common to all enemies, such as movement speed and collision cooldown.
 */
public class Enemy extends Entity {

    public long lastCollisionTime = 0;
    public static final long COOLDOWN = 500;

    Random r = new Random();

    /**
     * Constructs an Enemy object associated with a specified game panel.
     *
     * @param gp the game panel associated with this entity
     */
    public Enemy(GamePanel gp) {
        super(gp);
    }

    /**
     * Retrieves the current movement speed of the enemy.
     *
     * @return the speed of the enemy
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the movement speed of the enemy.
     *
     * @param value the new speed value to set
     */
    public void setSpeed(double value) {
        this.speed = value;
    }

    /**
     * Updates the enemy entity's behavior. This method is intended to be overridden by subclasses
     * with specific movement or attack logic for each type of enemy.
     */
    public void update() {
        // Update logic for the enemy would be implemented here
    }
}
