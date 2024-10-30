package vaultescape.reward;

public class BonusReward extends Reward {
    private long spawnTime;

    public BonusReward(int x, int y, int points) {
        super(x, y, points);
        this.spawnTime = System.currentTimeMillis();
    }
    
    public long getSpawnTime() {
        return spawnTime;
    }
}
