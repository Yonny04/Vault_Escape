package vaultescape.entity;

import vaultescape.map.*;
import vaultescape.ui.Sprite2D;

import java.awt.Graphics2D;

/**
 * Represents the player character in the game, allowing for movement, scoring, and interaction with the game environment.
 * The Player class utilizes input from a KeyDetector to manage movement and animations.
 */
public class Player extends Entity {
    KeyDetector keyh; // Key detector to manage player input

    private int score = 0; // Player's current score

    /**
     * Constructs a Player entity with a specified game panel and key detector for movement input.
     *
     * @param gp the game panel associated with this player
     * @param keyh the key detector handling input for the player
     */
    public Player(GamePanel gp, KeyDetector keyh) {
        super(gp);
        setPosition(new int[]{2112,192});
        this.keyh = keyh;
        this.screenX = gp.screenWidth / 2 - (gp.tilesize / 2);
        this.screenY = gp.screenHeight / 2 - (gp.tilesize / 2);
        this.speed = 5;
        this.direction = 3;
        setHitbox(42, 36);
        setSpritesheet("/entity/player/spritesheet.png", 4, 4);
    }

    /**
     * Retrieves the current score of the player.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds a specified number of points to the player's score.
     *
     * @param points the number of points to add to the score
     */
    public void addScore(int points) {
        score += points;
    }

    public boolean isTouchingExit() {
        for (Sprite2D wall : gp.getTileGenerator().walls) {
            if (isTouching(wall)) {
                if (wall instanceof Exit) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates the player's state, including position based on input, collision detection, and animation frame updates.
     * Handles movement along both x and y axes, resetting position if collisions are detected.
     */
    public void update() {
        int oldX = x;
        int oldY = y;

        // Handle movement input from the key detector
        if (keyh.up || keyh.left || keyh.down || keyh.right) {
            if (keyh.left) {
                x -= speed;
                direction = 0;
            } else if (keyh.right) {
                x += speed;
                direction = 1;
            }
            // Win Condition (no more rewards)
            if (isTouchingExit() && gp.getRewardGenerator().getRegularRewardsSize() == 0) gp.completeGame(true);
            // Check for collisions with walls on the x-axis
            if (!canMove()) x = oldX;

            if (keyh.up) {
                y -= speed;
                direction = 2;
            } else if (keyh.down) {
                y += speed;
                direction = 3;
            }
            // Win Condition (no more rewards)
            if (isTouchingExit() && gp.getRewardGenerator().getRegularRewardsSize() == 0) gp.completeGame(true);
            // Second check for collisions with walls on the y-axis
            if (!canMove()) y = oldY;

            // Increment sprite animation counter
            spriteCounter += 0.1f;
            if (spriteCounter > 3.9f) spriteCounter = 0.0f;

        } else spriteCounter = 1.0f;  // Reset sprite counter when idle

        // Set player animation frame from the floored spriteCounter
        setFrame((int) Math.floor(spriteCounter), direction);

    }

    /**
     * Draws the player character on the screen and renders the hitbox for debugging purposes.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, screenX, screenY, width, height, null);
        super.drawHitbox(g2);
    }
}
