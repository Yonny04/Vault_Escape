package game.tile.entity.character;

import game.object.Vector;
import game.panel.GamePanel;
import game.tile.Tile;
import game.tile.entity.Exit;
import game.utils.*;

import java.util.Random;
/**
 * Represents the player character in the game, allowing for movement, scoring, and interaction with the game environment.
 * The Player class utilizes input from a KeyDetector to manage movement and animations.
 */
public class Player extends Character {
    private KeyDetector keyh; // Key detector to manage player input
    private int score = 0; // Player's current score
    private Vector camera = new Vector();
    private Vector cameraOffset = new Vector();
    private double cameraLerp = 0.064;

    /**
     * Constructs a Player entity with a specified game panel and key detector for movement input.
     *
     * @param gp the game panel associated with this player
     * @param start the starting position
     * @param keyh the key detector handling input for the player
     */
    public Player(GamePanel gp, Vector start, KeyDetector keyh) {
        super(gp,start);
        this.keyh = keyh;
        this.speed = 5;
        setPosition(start);
        ResourceLoader.loadAnimationPlayer(this, "player");
        setDirection(Direction.DOWN);
        camera.setPosition(rect);
        cameraOffset.setPosition(gp.SCREEN_SIZE.subtract(Vector.TILE_SIZE).scale(0.5));
    }

    /**
     * Gets the current position of the camera.
     * 
     * @return a Vector2 object representing the camera's position
     */
    public Vector getCameraPosition() {return camera;}

    /**
     * Gets the current offset of the camera.
     * 
     * @return a Vector2 object representing the camera's offset
     */
    public Vector getCameraOffset() {return cameraOffset;}
    
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
        if (gp.getRewardGenerator().hasValuablesLeft()) return false;
        for (Tile wall : gp.getTileGenerator().wallTiles.values()) {
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
    @Override
    public void update() {
        // Handle movement input from the key detector
        if (keyh.up || keyh.left || keyh.down || keyh.right) {
            if (keyh.left) move(Direction.LEFT);
            else if (keyh.right) move(Direction.RIGHT);
            if (keyh.up) move(Direction.UP);
            else if (keyh.down) move(Direction.DOWN);

            // Footstep sound effect
            int oldFrame = getAnimationPlayer().getFrame();
            super.update();
            int newFrame = getAnimationPlayer().getFrame();
            if (oldFrame != newFrame && newFrame % 2 == 0) gp.getSFX().play("footstep");
            getAnimationPlayer().playAnimation(direction.name());

        } else idle();
        updateCamera();
    }

    @Override
    public void move(Direction direction) {
        Vector oldPosition = rect.getPosition();
        moveUnsafe(direction);
        if (canEscape()) gp.completeGame(true);
        if (!canMove()) setPosition(oldPosition);
    }

    /**
     * Updates the camera's position based on the current game state.
     * If a "bite" or "hit" sound effect is playing, the camera shakes randomly.
     * Otherwise, the camera smoothly follows the target rectangle using linear interpolation.
     */
    private void updateCamera() {
        if (gp.getSFX().isPlaying("bite") || gp.getSFX().isPlaying("hit")) {
            Random r = new Random();
            int d1 = r.nextInt(2);
            int d2 = r.nextInt(2);
            if (d1 == 0) d1 = -1;
            if (d2 == 0) d2 = -1;
            Vector shakeOffset = new Vector(d1,d2).scale(r.nextInt(8,10));
            camera = camera.add(shakeOffset);
        } else {
            Vector deltaCamera = rect.subtract(camera).scale(cameraLerp);
            camera = camera.add(deltaCamera);
        }
    }
    
}
