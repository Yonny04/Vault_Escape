package vaultescape.entity.enemy;

import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

import java.awt.*;

/**
 * Represents a Dog enemy entity that can chase the player within a specific range.
 * The Dog can wander, chase the player, and be frozen for a certain period.
 */
public class Dog extends Enemy {

    int timer = 0;
    private Vector next = new Vector();
    private Timer biteCooldown = new Timer(0.3);

    /**
     * Constructs a Dog object with a specified game panel and start position.
     *
     * @param gp the game panel associated with this entity
     * @param start the starting position
     */
    public Dog(GamePanel gp, Vector start) {
        super(gp, start);
        this.speed = 4;
        this.range = 250;
        next.x = next.y = 0;
        setSpritesheet("/entity/enemy/dog/spritesheet.png", 4, 4);
    }

    /**
     * Updates the dog's logic, including movement, freezing state, and chasing the player.
     * If the dog is frozen, it remains in place until the freeze duration ends.
     */
    @Override
    public void update() {
        if (speed > 5) speed = 5;

        if (!biteCooldown.isTimeUp()) {
            stopAnimation(); 
            return;
        }
        if (isPlayerInRange()) {
            chasePlayer();
            timer = 81;
        } else {
            wander();
            timer++;
        }
    }

    /**
     * Causes the dog to wander when the player is out of range.
     * The dog moves randomly within a defined interval and direction.
     */
    private void wander() {
        if (timer > 180) {
            next.x = r.nextInt(3) - 1;
            next.y = r.nextInt(3) - 1;
            timer = 0;
        }
        if (timer < 80) {
            if (next.y > 0) move(Direction.DOWN);
            else if (next.y < 0) move(Direction.UP);
            if (next.x > 0) move(Direction.RIGHT);
            else if (next.x < 0) move(Direction.LEFT);
            playAnimation();
        } else stopAnimation();
    }

    /**
     * Makes the dog chase the player if they are within range, moving in the direction of the player.
     * Adjusts the position based on obstacles and checks for collisions.
     */
    private void chasePlayer() {
        
        Vector player = gp.getPlayer().getRect();
        Vector delta = player.subtract(getPosition());

        
        if (delta.x > 0) move(Direction.RIGHT);
        else if (delta.x < 0) move(Direction.LEFT);
        if (delta.y > 0) move(Direction.DOWN);
        else if (delta.y < 0) move(Direction.UP);

        if (Math.abs(delta.x) > Math.abs(delta.y)) {
            if (delta.x > 0) setDirection(Direction.RIGHT);
            else setDirection(Direction.LEFT);
        } else {
            if (delta.y > 0) setDirection(Direction.DOWN);
            else if (delta.y < 0) setDirection(Direction.UP);   
        }
        playAnimation();
    }

    /**
     * Draws the dog entity, including a semi-transparent oval indicating its chase range.
     *
     * @param g2 the Graphics2D object used for rendering the dog and its chase range
     */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (drawCollisions) {
            g2.setColor(new Color(1.0f, 0.5f, 0.0f, 0.2f));
            g2.drawOval(screen.x + rect.w/2 - range, 
                screen.y + rect.h/2 - range, range * 2, range * 2);
        }
    }

    @Override
    public void attack() {
        gp.getSFX().play("bite");
        gp.getTimer().decreaseTime(3);
        biteCooldown.start();
        super.attack();
    }
}
