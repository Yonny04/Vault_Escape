package vaultescape.reward;

import java.util.Random;
import vaultescape.map.GamePanel;

/**
 * Represents a regular reward item in the game, providing a random point value from a set of possible values.
 * Regular rewards are simpler than bonus rewards, as they do not track spawn time.
 */
public class RegularReward extends Reward {

    private static final int[] POSSIBLE_VALUES = {10, 20, 30, 40, 50}; // Possible point values for regular rewards
    private static final Random random = new Random(); // Random generator for selecting point values

    /**
     * Constructs a RegularReward with a specified position and a randomly chosen point value.
     *
     * @param gp the game panel associated with this regular reward
     * @param x the x-coordinate of the regular reward's position
     * @param y the y-coordinate of the regular reward's position
     */
    public RegularReward(GamePanel gp, int x, int y) {
        super(gp, x, y, getRandomPoints());
    }

    /**
     * Chooses a random point value from the set of possible values for regular rewards.
     *
     * @return the randomly selected point value
     */
    public static int getRandomPoints() {
        return POSSIBLE_VALUES[random.nextInt(POSSIBLE_VALUES.length)];
    }
}
