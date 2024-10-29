package vaultescape.reward;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vaultescape.map.GamePanel;
import vaultescape.map.TileGenerator;


public class RewardGenerator {
    private GamePanel gp;
    private List<Reward> regularRewards;
    private List<Reward> bonusRewards;
    private Random random;
    private TileGenerator tg;

    private long bonusRewardSpawnInterval = 10000;
    private long bonusRewardDuration = 7000;
    private long bonusSpawnTime;

    public RewardGenerator(GamePanel gpp, TileGenerator tgg) {
        this.gp = gpp;
        this.tg = tgg;
        this.regularRewards = new ArrayList<>();
        this.bonusRewards = new ArrayList<>();
        this.random = new Random();
        this.bonusSpawnTime = System.currentTimeMillis();
    }

    public void generateRegularRewards(int n){
        regularRewards.clear();

        for (int i = 0; i < n && !tg.availableTiles.isEmpty(); i++) {
            int index = random.nextInt(tg.availableTiles.size());
            int[] position = tg.availableTiles.remove(index);
            int x = position[0];
            int y = position[1];

            regularRewards.add(new RegularReward(x, y, 10));
        }
    }

    public void generateBonusRewards() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - bonusSpawnTime >= bonusRewardSpawnInterval) {
            List<int[]> availableTiles = tg.availableTiles;  
            if (!availableTiles.isEmpty()) {
                int index = random.nextInt(availableTiles.size());
                int[] position = availableTiles.remove(index);
                int x = position[0];
                int y = position[1];

                bonusRewards.add(new BonusReward(x, y, 20));
                bonusSpawnTime = currentTime;  
            }
        }
    }
    public void removeExpiredBonusRewards() {
        long currentTime = System.currentTimeMillis();
        bonusRewards.removeIf(reward -> (currentTime - ((BonusReward) reward).getSpawnTime()) >= bonusRewardDuration);
    }
    public void drawRewards(Graphics2D g2) {
        for (Reward reward : regularRewards) {
            reward.draw(g2);
        }
        for (Reward reward : bonusRewards) {
            reward.draw(g2);
        }
    }
    public List<Reward> getRegularRewards() {
        return regularRewards;
    }
    public List<Reward> getBonusRewards() {
        return bonusRewards;
    }
}
