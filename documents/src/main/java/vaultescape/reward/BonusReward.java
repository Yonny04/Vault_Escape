package vaultescape.reward;

import vaultescape.map.GamePanel;

public class BonusReward extends Reward {
    private long spawnTime;

    public BonusReward(GamePanel gp, int x, int y, int points) {
        super(gp, x, y, points);
        this.spawnTime = System.currentTimeMillis();
    }
    
    public long getSpawnTime() {
        return spawnTime;
    }
}
