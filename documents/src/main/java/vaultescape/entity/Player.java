package vaultescape.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import vaultescape.map.GamePanel;
import vaultescape.map.KeyDetector;
import vaultescape.ui.Sprite2D;

/**
 * Represents the player character in the game, allowing for movement, scoring, and interaction with the game environment.
 * The Player class utilizes input from a KeyDetector to manage movement and animations.
 */
public class Player extends Entity {
    KeyDetector keyh; // Key detector to manage player input
    public boolean alive = true;
    BufferedImage spritesheet;

    private int score = 0; // Player's current score

    /**
     * Constructs a Player entity with a specified game panel and key detector for movement input.
     *
     * @param gp the game panel associated with this player
     * @param keyh the key detector handling input for the player
     */
    public Player(GamePanel gp, KeyDetector keyh) {
        super(gp);
        setPosition(gp.getTileGenerator().getRandomAvailableTile());
        this.keyh = keyh;
        screenX = gp.screenWidth / 2 - (gp.tilesize / 2);
        screenY = gp.screenHeight / 2 - (gp.tilesize / 2);
        speed = 5;
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
        if (keyh.w || keyh.a || keyh.s || keyh.d) {
            if (keyh.a) {
                x -= speed;
                direction = 0;
            } else if (keyh.d) {
                x += speed;
                direction = 1;
            }
            // Win Condition (no more rewards)
            if (isTouchingExit() && gp.getRewardGenerator().getRegularRewardsSize() == 0) gp.completeGame(true);
            // Check for collisions with walls on the x-axis
            if (!canMove()) x = oldX;

            if (keyh.w) {
                y -= speed;
                direction = 2;
            } else if (keyh.s) {
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
     * Return the x coordinate of the player
     *
     * @return x coordinate
     */
    public int getX(){
        return x;
    }
    /**
     * Return the y coordinate of the player
     *
     * @return y coordinate
     */
    public int getY(){
        return y;
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
