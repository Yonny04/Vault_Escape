package vaultescape.reward;

import java.awt.Color;
import java.awt.Graphics2D;

public class RegularReward extends Reward {
    private int points;

    public RegularReward(int x, int y, int points) {
        this.x = x;
        this.y = y;
        this.points = points;
    }

    // Draw method for regular reward
    public void draw(Graphics2D g2) {
        g2.setColor(Color.orange); // Color of the regular reward
        g2.fillRect(x, y, 25, 25); // Drawing the regular reward
    }

    // Getter for points
    public int getPoints() {
        return points;
    }
}
