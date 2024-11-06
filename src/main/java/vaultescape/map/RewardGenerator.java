package vaultescape.map;

import vaultescape.entity.Player;
import vaultescape.entity.reward.*;
import vaultescape.ui.GamePanel;
import vaultescape.utils.Vector2;

import java.awt.Graphics2D;
import java.util.*;

/**
 * Generates and manages regular and bonus rewards in the game, including spawning, updating,
 * collecting, and removing expired rewards. Rewards appear at random positions on available tiles.
 */
public class RewardGenerator {
    private GamePanel gp;
    private List<Reward> regularRewards;
    private List<Reward> bonusRewards;
    private Random random;
    private TileGenerator tg;

    private long bonusRewardSpawnInterval = 5000;
    private long bonusRewardDuration = 7000;
    private long bonusSpawnTime;

    /**
     * Constructs a RewardGenerator with a specified game panel and tile generator.
     *
     * @param gpp the game panel associated with this generator
     * @param tgg the tile generator used for determining available tiles
     */
    public RewardGenerator(GamePanel gpp, TileGenerator tgg) {
        this.gp = gpp;
        this.tg = tgg;
        this.regularRewards = new ArrayList<>();
        this.bonusRewards = new ArrayList<>();
        this.random = new Random();
        this.bonusSpawnTime = System.currentTimeMillis();
    }

    /**
     * Updates the state of rewards, including generating bonus rewards, removing expired ones,
     * and checking if the player has collected any rewards.
     *
     * @param player the player entity for checking reward collection
     */
    public void update(Player player) {
        generateBonusRewards();
        removeExpiredBonusRewards();
        checkRewardCollection(player);
    }

    /**
     * Generates a specified number of regular rewards at random available tile positions.
     *
     * @param n the number of regular rewards to generate
     */
    public void generateRegularRewards(int n) {
        regularRewards.clear();

        for (int i = 0; i < n && !tg.availableTiles.isEmpty(); i++) {
            int index = random.nextInt(tg.availableTiles.size());
            Vector2 start = tg.availableTiles.remove(index);
            regularRewards.add(new RegularReward(gp, start));
        }
    }

    /**
     * Generates bonus rewards at random positions if the bonus spawn interval has passed.
     * Bonus rewards provide a higher point value and appear temporarily.
     */
    public void generateBonusRewards() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - bonusSpawnTime >= bonusRewardSpawnInterval) {
            List<Vector2> availableTiles = tg.availableTiles;
            if (!availableTiles.isEmpty()) {
                int index = random.nextInt(availableTiles.size());
                Vector2 start = availableTiles.remove(index);
                bonusRewards.add(new BonusReward(gp, start, 100));
                bonusSpawnTime = currentTime;
            }
        }
    }

    /**
     * Removes bonus rewards that have expired based on the set duration.
     */
    public void removeExpiredBonusRewards() {
        long currentTime = System.currentTimeMillis();
        bonusRewards.removeIf(reward -> (currentTime - ((BonusReward) reward).getSpawnTime()) >= bonusRewardDuration);
    }

    /**
     * Checks if the player has collected any rewards by touching them, and adds points
     * to the player's score if a bonus reward is collected.
     *
     * @param player the player entity for checking reward collection
     */
    public void checkRewardCollection(Player player) {
        for (int i = 0; i < regularRewards.size(); i++) {
            Reward reward = regularRewards.get(i);
            if (player.isTouching(reward)) {
                player.addScore(20);
                regularRewards.remove(i);
                gp.getSFX().play("basic_collect");
                i--;
            }
        }

        for (int i = 0; i < bonusRewards.size(); i++) {
            Reward reward = bonusRewards.get(i);
            if (player.isTouching(reward)) {
                player.addScore(((BonusReward) reward).getPoints());
                bonusRewards.remove(i);
                gp.getSFX().play("bonus_collect");
                i--;
            }
        }
    }

    /**
     * Draws all regular and bonus rewards on the screen.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    public void drawRewards(Graphics2D g2) {
        for (Reward reward : regularRewards) {
            reward.draw(g2);
        }
        for (Reward reward : bonusRewards) {
            reward.draw(g2);
        }
    }

    /**
     * Retrieves the list of regular rewards.
     *
     * @return a list of regular rewards
     */
    public List<Reward> getRegularRewards() {
        return regularRewards;
    }

    /**
     * Retrieves the list of bonus rewards.
     *
     * @return a list of bonus rewards
     */
    public List<Reward> getBonusRewards() {
        return bonusRewards;
    }

    /**
     * Retrieves the count of regular rewards currently in the game.
     *
     * @return the size of the regular rewards list
     */
    public int getRegularRewardsSize() {
        return regularRewards.size();
    }

    /**
     * Retrieves the count of bonus rewards currently in the game.
     *
     * @return the size of the bonus rewards list
     */
    public int getBonusRewardsSize() {
        return bonusRewards.size();
    }
}
