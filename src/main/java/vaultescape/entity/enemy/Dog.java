package vaultescape.entity.enemy;

import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

import java.awt.*;

/**
 * Represents a Dog enemy entity that can chase the player within a specific range.
 * The Dog can wander, chase the player, and be frozen for a certain period.
 */
public class Dog extends Enemy {

    private final int chaseRange;
    int timer = 0;
    private Vector2 next = new Vector2();
    private Timer biteCooldown = new Timer(0.3);

    /**
     * Constructs a Dog object with a specified game panel and start position.
     *
     * @param gp the game panel associated with this entity
     * @param start the starting position
     */
    public Dog(GamePanel gp, Vector2 start) {
        super(gp, start);
        this.chaseRange = 250;
        this.speed = 4;
        next.x = next.y = 0;
        setSpritesheet("/entity/enemy/dog/spritesheet.png", 4, 4);
    }

    /**
     * Updates the dog's logic, including movement, freezing state, and chasing the player.
     * If the dog is frozen, it remains in place until the freeze duration ends.
     */
    @Override
    public void update() {

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
     * Checks if the player is within the dog's chase range.
     *
     * @return true if the player is within range, false otherwise
     */
    private boolean isPlayerInRange() {
        Rect2 player = gp.getPlayer().getRect();
        return Math.abs(player.x - rect.x) < chaseRange && Math.abs(player.y - rect.y) <= chaseRange;
    }

    /**
     * Makes the dog chase the player if they are within range, moving in the direction of the player.
     * Adjusts the position based on obstacles and checks for collisions.
     */
    private void chasePlayer() {
        if (speed > 4) speed = 4;
        
        Vector2 player = gp.getPlayer().getRect();
        Vector2 delta = player.subtract(getPosition());

        if (delta.x > 0) move(Direction.RIGHT);
        else if (delta.x < 0) move(Direction.LEFT);
        if (delta.y > 0) move(Direction.DOWN);
        else if (delta.y < 0) move(Direction.UP);

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
        if (_drawCollisions) {
            g2.setColor(new Color(1.0f, 0.5f, 0.0f, 0.2f));
            g2.drawOval(screen.x + rect.w/2 - chaseRange, 
                screen.y + rect.h/2 - chaseRange, chaseRange * 2, chaseRange * 2);
        }
    }

    @Override
    public void recordCollision() {
        super.recordCollision();
        biteCooldown.start();
    }
}
