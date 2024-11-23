package game.level;

import game.panel.GamePanel;
import game.tile.entity.reward.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RewardGenerator
 */
class TestRewardGenerator {

    private RewardGenerator rewardGenerator;
    private GamePanel gamePanel;

    @BeforeEach
    public void reset() {
        gamePanel = new GamePanel(null, 1);
        rewardGenerator = new RewardGenerator(gamePanel);
    }

    @Test
    public void testSpawnRewards() {
        rewardGenerator.spawn(Valuable.class, 5);
        List<Reward> rewards = rewardGenerator.getRewards();
        assertEquals(5, rewards.size());
        for (Reward reward : rewards) {
            assertTrue(reward instanceof Valuable);
        }
    }

    @Test 
    public void testSpawnDiamond() {
        rewardGenerator.getBonusSpawnTimer().setCountdownTime(0.0);
        rewardGenerator.spawnDiamond();
        List<Reward> rewards = rewardGenerator.getRewards();
        assertEquals(1, rewards.size());
        assertTrue(rewards.get(0) instanceof Diamond);
    }

    @Test
    public void testRemoveExpiredRewardsPickup() {
        rewardGenerator.spawn(Valuable.class, 1);
        Valuable valuable = (Valuable)rewardGenerator.getRewards().get(0);
        valuable.getAnimationPlayer().playAnimation("pickup");
        while (!valuable.getAnimationPlayer().finished()) {
            valuable.update();
        }
        rewardGenerator.removeExpiredRewards();
        assertEquals(0, rewardGenerator.getRewards().size());
    }

    @Test
    public void testRemoveExpiredRewardsTimeUp() {
        rewardGenerator.spawn(Diamond.class, 1);
        Diamond diamond = (Diamond)rewardGenerator.getRewards().get(0);
        diamond.getTimer().setCountdownTime(0.0);
        rewardGenerator.removeExpiredRewards();
        assertEquals(0, rewardGenerator.getRewards().size());
    }

    @Test
    public void testUpdateRewards() {
        rewardGenerator.spawn(Valuable.class, 1);
        rewardGenerator.update();
        assertFalse(rewardGenerator.getRewards().isEmpty());
    }

    @Test
    public void testGetValuableCount() {
        rewardGenerator.spawn(Diamond.class, 1);
        rewardGenerator.spawn(Valuable.class, 2);
        assertEquals(2, rewardGenerator.getValuableCount());
    }

    @Test
    public void testHasValuablesLeft() {
        rewardGenerator.spawn(Valuable.class, 2);
        assertTrue(rewardGenerator.hasValuablesLeft());
    }

    @Test
    public void testHasNoValuablesLeftEmpty() {
        assertFalse(rewardGenerator.hasValuablesLeft());
    }

    @Test
    public void testHasNoValuablesLeftNonEmpty() {
        rewardGenerator.spawn(Diamond.class, 2);
        rewardGenerator.getRewards().clear();
        assertFalse(rewardGenerator.hasValuablesLeft());
    }

    @Test
    public void testGetRewards() {
        rewardGenerator.spawn(Valuable.class, 1);
        rewardGenerator.spawn(Diamond.class, 1);
        List<Reward> rewards = rewardGenerator.getRewards();
        assertEquals(2, rewards.size());
        assertTrue(rewards.get(0) instanceof Valuable);
        assertTrue(rewards.get(1) instanceof Diamond);
    }
}
