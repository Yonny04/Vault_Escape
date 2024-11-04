package vaultescape.reward;

import vaultescape.map.GamePanel;

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
     * @param x the x-coordinate of the bonus reward's position
     * @param y the y-coordinate of the bonus reward's position
     * @param points the number of points the bonus reward grants upon collection
     */
    public BonusReward(GamePanel gp, int x, int y, int points) {
        super(gp, x, y, points);
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
