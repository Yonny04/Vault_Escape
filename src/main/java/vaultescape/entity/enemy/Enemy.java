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

    protected Timer attackCooldown = new Timer(1);
    protected int range = 0;
    Random r = new Random();

    /**
     * Constructs an Enemy object associated with a specified game panel.
     *
     * @param gp the game panel associated with this entity
     */
    public Enemy(GamePanel gp, Vector start) {
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
     * Records the time of the last player collision event.
     * Attacks the player.
     */
    public void attack() {
        attackCooldown.start();
    }

    /**
     * Determines if the enemy can attack the player, 
     * based on the cooldown period.
     *
     * @return true if the cooldown period has passed, false otherwise
     */
    public boolean canAttack() {return attackCooldown.isTimeUp();}

    /**
     * Checks if the player is within the dog's chase range.
     *
     * @return true if the player is within range, false otherwise
     */
    public boolean isPlayerInRange() {
        Rect player = gp.getPlayer().getRect();
        return Math.abs(player.x - rect.x) < range && Math.abs(player.y - rect.y) <= range;
    }
    
}
