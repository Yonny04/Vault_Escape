package game.level;

import game.panel.GamePanel;
import game.tile.entity.reward.*;
import game.utils.Timer;

import java.util.*;

/**
 * Generates and manages regular and bonus rewards in the game, including spawning, updating,
 * collecting, and removing expired rewards. Rewards appear at random positions on available tiles.
 */
public class RewardGenerator {
    public Generator<Reward> generator;

    private Timer bonusSpawnTimer = new Timer(8);

    /**
     * Constructs a RewardGenerator with a specified game panel and tile generator.
     *
     * @param gp the game panel associated with this generator
     */
    public RewardGenerator(GamePanel gp) {
        this.generator = new Generator<>(gp);
    }

    /**
     * Updates the state of rewards, including generating bonus rewards, removing expired ones,
     * and checking if the player has collected any rewards.
     *
     * @param player the player entity for checking reward collection
     */
    public void update() {
        generator.update();
        spawnDiamond();
        removeExpiredRewards();
    }

    /**
     * Spawn a specified number of basic rewards at random available tile positions.
     *
     * @param n the number of basic rewards to generate
     */
    public void spawn(Class<? extends Reward> type, int n) {
        generator.spawn(type, n);
    }

    /**
     * Spawns a diamond if the bonus spawn timer has expired.
     * Resets and starts the bonus spawn timer after spawning a diamond.
     */
    public void spawnDiamond() {
        if (bonusSpawnTimer.isTimeUp()) {
            spawn(Diamond.class, 1);
            bonusSpawnTimer.start();
        }
    }

    /**
     * Removes bonus rewards that have expired based on the set duration.
     */
    public void removeExpiredRewards() {
        for (Iterator<Reward> it = generator.elements.iterator(); it.hasNext();) {
            Reward reward = it.next();
            if (reward instanceof Diamond) {
                if (((Diamond)reward).getTimer().isTimeUp()) it.remove();
            }
            if (reward.getAnimationPlayer().finished()) it.remove();
        }
    }

    /**
     * Returns the count of valuable items managed by this generator.
     * This method counts the number of elements of type Valuable.
     *
     * @return The number of valuable items.
     */
    public int getValuableCount() {
        return generator.getCountByType(Valuable.class);
    }
    
    /**
     * Retrieves the count of regular rewards currently in the game.
     *
     * @return the size of the regular rewards list
     */
    public boolean hasValuablesLeft() {
        return getValuableCount() > 0;
    }

    /**
     * Retrieves the list of all generated rewards.
     *
     * @return a list of enemies
     */
    public List<Reward> getRewards() {
        return generator.elements;
    }

    /**
     * Returns the bonus spawn timer for this generator.
     * @return the bonus spawn timer
     */
    public Timer getBonusSpawnTimer() {return bonusSpawnTimer;}
}
