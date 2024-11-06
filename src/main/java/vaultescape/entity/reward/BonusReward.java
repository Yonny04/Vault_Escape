package vaultescape.entity.reward;

import vaultescape.ui.GamePanel;
import vaultescape.utils.Vector2;

/**
 * Represents a bonus reward item in the game, which has a spawn time attribute to track when it appears.
 * Bonus rewards provide additional points to the player when collected.
 */
public class BonusReward extends Reward {
    private long spawnTime; // The time when the bonus reward was created

    /**
     * Constructs a BonusReward with a specified position, point value, and spawn time.
     *
     * @param gp the game panel associated with this bonus reward
     * @param start the starting position
     * @param points the number of points the bonus reward grants upon collection
     */
    public BonusReward(GamePanel gp, Vector2 start, int points) {
        super(gp, start, points);
        this.spawnTime = System.currentTimeMillis();
    }

    /**
     * Retrieves the spawn time of the bonus reward.
     *
     * @return the spawn time in milliseconds since the epoch
     */
    public long getSpawnTime() {
        return spawnTime;
    }
}
