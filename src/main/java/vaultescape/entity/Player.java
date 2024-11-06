package vaultescape.entity;

import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

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
     * @param start the starting position
     * @param keyh the key detector handling input for the player
     */
    public Player(GamePanel gp, Vector2 start, KeyDetector keyh) {
        super(gp,start);
        this.keyh = keyh;
        this.screen.setPosition(gp.SCREEN_SIZE.subtract(gp.TILE_SIZE).scale(0.5));
        this.speed = 5;
        this.direction = Direction.DOWN;
        setSpritesheet("/entity/player/spritesheet.png", 4, 4);
    }

    /**
     * Retrieves the current score of the player.
     *
     * @return the player's score
     */
    public int getScore() {return score;}

    /**
     * Adds a specified number of points to the player's score.
     *
     * @param points the number of points to add to the score
     */
    public void addScore(int points) {score += points;}

    /**
     * Checks if the current sprite is touching an exit tile.
     * Iterates through all the wall tiles and checks for collisions.
     * If a wall tile is an instance of Exit, returns true.
     * 
     * @return true if the current sprite is touching an exit, false otherwise
     */
    public boolean canEscape() {
        if (gp.getRewardGenerator().getRegularRewardsSize() > 0) return false;
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
        Vector2 old = rect.getPosition();
        // Handle movement input from the key detector
        if (keyh.up || keyh.left || keyh.down || keyh.right) {
            if (keyh.left) {
                rect.x -= speed;
                setDirection(Direction.LEFT);
            } else if (keyh.right) {
                rect.x += speed;
                setDirection(Direction.RIGHT);
            }
            if (canEscape()) gp.completeGame(true); // Win Condition
            if (!canMove()) rect.x = old.x; // Check for collisions with walls on the x-axis

            if (keyh.up) {
                rect.y -= speed;
                setDirection(Direction.UP);
            } else if (keyh.down) {
                rect.y += speed;
                setDirection(Direction.DOWN);
            }
            if (canEscape()) gp.completeGame(true); // Win Condition
            if (!canMove()) rect.y = old.y; // Second check for collisions with walls on the y-axis

            playAnimation();
        } else stopAnimation(); 

    }

    /**
     * Draws the player character on the screen and renders the hitbox for debugging purposes.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, screen.x, screen.y, rect.w, rect.h, null);
        super.drawHitbox(g2);
    }
}
