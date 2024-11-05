package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

/**
 * Represents a Dog enemy entity that can chase the player within a specific range.
 * The Dog can wander, chase the player, and be frozen for a certain period.
 */
public class Dog extends Enemy {

    private final int chaseRange;
    int timer = 0;
    int vectorX;
    int vectorY;
    private boolean isFrozen = false;
    private long frozenTime = 0;

    /**
     * Constructs a Dog object with a specified game panel and start position.
     *
     * @param gp the game panel associated with this entity
     * @param startX the starting x-coordinate of the dog
     * @param startY the starting y-coordinate of the dog
     */
    public Dog(GamePanel gp, int startX, int startY) {
        super(gp);
        this.x = startX;
        this.y = startY;
        this.width = 64;
        this.height = 64;
        this.chaseRange = 250;
        vectorX = 0;
        vectorY = 0;
        this.speed = 3;
        setHitbox(40, 32);
        setSpritesheet("/entity/dog/spritesheet.png", 4, 4);
    }

    /**
     * Updates the dog's logic, including movement, freezing state, and chasing the player.
     * If the dog is frozen, it remains in place until the freeze duration ends.
     */
    @Override
    public void update() {
        if (isFrozen) {
            if (System.currentTimeMillis() > frozenTime) {
                isFrozen = false; // Unfreeze
            }
            return; // Skip movement if frozen
        }

        if (isPlayerInRange()) {
            chasePlayer();
            timer = 81;
        } else {
            wander();
            timer++;
        }
        setFrame((int) Math.floor(spriteCounter), direction);
    }

    /**
     * Causes the dog to wander when the player is out of range.
     * The dog moves randomly within a defined interval and direction.
     */
    private void wander() {
        if (timer > 180) {
            vectorX = r.nextInt(3) - 1;
            vectorY = r.nextInt(3) - 1;
            timer = 0;
        }
        if (timer < 80) {
            int oldX = x;
            int oldY = y;
            if (vectorY > 0) {
                y += speed * vectorY;
                direction = 3;
            } else if (vectorY < 0) {
                y += speed * vectorY;
                direction = 2;
            }
            if (!canMove()) {
                y = oldY;
                timer = 100;
            }
            if (vectorX > 0) {
                x += speed * vectorX;
                direction = 1;
            } else if (vectorX < 0) {
                x -= speed * vectorX;
                direction = 0;
            }
            if (!canMove()) {
                x = oldX;
                timer = 100;
            }
            spriteCounter += 0.1f;
            if (spriteCounter > 3.9f) spriteCounter = 0.0f;
        } else {
            spriteCounter = 1.0f;
        }
    }

    /**
     * Checks if the player is within the dog's chase range.
     *
     * @return true if the player is within range, false otherwise
     */
    private boolean isPlayerInRange() {
        int playerX = gp.getPlayer().getX();
        int playerY = gp.getPlayer().getY();
        return Math.abs(playerX - x) < chaseRange && Math.abs(playerY - y) <= chaseRange;
    }

    /**
     * Freezes the dog for a specified duration, during which it cannot move.
     *
     * @param duration the duration of the freeze in milliseconds
     */
    public void freezeDog(long duration) {
        isFrozen = true;
        frozenTime = System.currentTimeMillis() + duration * 100;
    }

    /**
     * Determines if the dog can collide, based on the cooldown period.
     *
     * @return true if the cooldown period has passed, false otherwise
     */
    public boolean canCollide() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastCollisionTime) >= COOLDOWN;
    }

    /**
     * Records the time of the last collision event.
     */
    public void recordCollision() {
        lastCollisionTime = System.currentTimeMillis();
    }

    /**
     * Makes the dog chase the player if they are within range, moving in the direction of the player.
     * Adjusts the position based on obstacles and checks for collisions.
     */
    private void chasePlayer() {
        int playerX = gp.getPlayer().getX();
        int playerY = gp.getPlayer().getY();
        int oldX = x;
        int oldY = y;

        int deltaX = playerX - x;
        int deltaY = playerY - y;

        if (deltaX > 0) {
            x += speed;
            direction = 1;
        } else if (deltaX < 0) {
            x -= speed;
            direction = 0;
        }
        if (!canMove()) {
            x = oldX;
        }

        if (deltaY > 0) {
            y += speed;
            direction = 3;
        } else if (deltaY < 0) {
            y -= speed;
            direction = 2;
        }
        if (!canMove()) {
            y = oldY;
        }

        spriteCounter += 0.1f;
        if (spriteCounter > 3.9f) spriteCounter = 0.0f;
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
            g2.drawOval(screenX + width / 2 - chaseRange, screenY + height / 2 - chaseRange, chaseRange * 2, chaseRange * 2);
        }
    }
}
