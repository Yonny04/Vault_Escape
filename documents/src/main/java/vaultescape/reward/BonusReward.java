package vaultescape.reward;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BonusReward extends Reward {
    private int points;
    private long spawnTime;

    public BonusReward(int x, int y, int points) {
        this.x = x;
        this.y = y;
        this.points = points;
        this.spawnTime = System.currentTimeMillis();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20); 
    }
    
    public long getSpawnTime() {
        return spawnTime;
    }

    // Draw method for bonus reward
    public void draw(Graphics2D g2) {
        g2.setColor(Color.green); // Color of the bonus reward
        g2.fillOval(x, y, 20, 20); // Drawing the bonus reward
    }

    // Getter for points
    public int getPoints() {
        return points;
    }
}
