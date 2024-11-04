package vaultescape.entity;

import vaultescape.map.GamePanel;

/**
 * Represents a Guard enemy entity that patrols between two points in either a horizontal or vertical direction.
 * The Guard can reverse direction upon reaching its patrol endpoints or if obstructed.
 */
public class Guards extends Enemy {
    private final int x1, y1; // Starting position
    private final int x2, y2; // Ending position
    private boolean goingEnd = true; // Direction of movement: true means moving towards the end position
    private final boolean horizontal; // Direction type: true if movement is horizontal, false if vertical

    /**
     * Constructs a Guard entity with a specified patrol range.
     *
     * @param gp the game panel associated with this guard
     * @param x1 the starting x-coordinate of the guard's patrol path
     * @param y1 the starting y-coordinate of the guard's patrol path
     * @param x2 the ending x-coordinate of the guard's patrol path
     * @param y2 the ending y-coordinate of the guard's patrol path
     */
    public Guards(GamePanel gp, int x1, int y1, int x2, int y2) {
        super(gp);
        this.speed = 2;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x = x1;
        this.y = y1;
        this.horizontal = (x1 != x2);
        setSpritesheet("/entity/guard/spritesheet.png", 4, 4);
        setHitbox(32, 32);
    }

    /**
     * Updates the guard's position along its patrol path. If the guard reaches the end of the path,
     * it reverses direction. Additionally, if the guard's speed exceeds the set maximum, it is capped.
     */
    @Override
    public void update() {
        if (speed > 5) {
            speed = 5;
        }

        if (horizontal) {
            if (goingEnd) {
                x += speed;
                direction = 1;
            } else {
                x -= speed;
                direction = 0;
            }
            if (x >= x2 || x <= x1) reverse();
        } else {
            if (goingEnd) {
                y += speed;
                direction = 3;
            } else {
                y -= speed;
                direction = 2;
            }
            if (y >= y2 || y <= y1) reverse();
        }

        if (!canMove()) {
            reverse();
        }

        spriteCounter += 0.1f;
        if (spriteCounter > 3.9f) spriteCounter = 0.0f;
        setFrame((int) Math.floor(spriteCounter), direction);
    }

    /**
     * Reverses the guard's direction along its patrol path.
     */
    private void reverse() {
        goingEnd = !goingEnd;
    }

    /**
     * Determines if the guard can collide with the player, based on a cooldown timer.
     *
     * @return true if the cooldown period has passed since the last collision, false otherwise
     */
    public boolean canCollide() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastCollisionTime) >= COOLDOWN;
    }

    /**
     * Records the time of the last collision event, used to manage the cooldown period for collisions.
     */
    public void recordCollision() {
        lastCollisionTime = System.currentTimeMillis();
    }
}
