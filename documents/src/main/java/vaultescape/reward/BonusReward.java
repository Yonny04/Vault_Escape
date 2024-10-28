package vaultescape.reward;

import java.awt.Color;
import java.awt.Graphics2D;

public class BonusReward extends Reward {
    private int points;

    public BonusReward(int x, int y, int points) {
        this.x = x;
        this.y = y;
        this.points = points;
    }

    // Draw method for bonus reward
    public void draw(Graphics2D g2) {
        g2.setColor(Color.green); // Color of the bonus reward
        g2.fillOval(x, y, 10,10); // Drawing the bonus reward
    }

    // Getter for points
    public int getPoints() {
        return points;
    }
}
