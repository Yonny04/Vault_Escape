package vaultescape.map;

import vaultescape.entity.character.Player;
import vaultescape.entity.reward.*;
import vaultescape.ui.GamePanel;
import vaultescape.utils.Timer;

import java.util.*;

/**
 * Generates and manages regular and bonus rewards in the game, including spawning, updating,
 * collecting, and removing expired rewards. Rewards appear at random positions on available tiles.
 */
public class RewardGenerator {
    public Generator<Reward> generator;

    private Timer bonusSpawnTimer = new Timer(10);

    /**
     * Constructs a RewardGenerator with a specified game panel and tile generator.
     *
     * @param gp the game panel associated with this generator
     * @param tg the tile generator used for determining available tiles
     */
    public RewardGenerator(GamePanel gp, TileGenerator tg) {
        this.generator = new Generator<>(gp);
    }

    /**
     * Updates the state of rewards, including generating bonus rewards, removing expired ones,
     * and checking if the player has collected any rewards.
     *
     * @param player the player entity for checking reward collection
     */
    public void update(Player player) {
        generator.update();
        spawnDiamond();
        checkRewardCollection(player);
        removeExpiredRewards();
    }

    /**
     * Spawn a specified number of basic rewards at random available tile positions.
     *
     * @param n the number of basic rewards to generate
     */
    public void spawnAll(int valuables,int diamonds) {
        generator.spawn(Valuable.class, valuables);
        generator.spawn(Diamond.class, diamonds);
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
                if (reward.getAnimationPlayer().finished()) {
                    it.remove();
                }
        }
    }

    /**
     * Checks if the player has collected any rewards by touching them, and adds points
     * to the player's score if a bonus reward is collected.
     *
     * @param player the player entity for checking reward collection
     */
    public void checkRewardCollection(Player player) {
        for (Iterator<Reward> it = generator.elements.iterator(); it.hasNext();) {
            Reward reward = it.next();
            if (reward.isTouchingPlayer()) {
                if (!reward.getAnimationPlayer().isPlaying()) reward.pickup();
            }
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
        for (Reward reward : generator.elements) {
            if (reward instanceof Valuable) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the list of all generated rewards.
     *
     * @return a list of enemies
     */
    public List<Reward> getRewards() {
        return generator.elements;
    }
}
