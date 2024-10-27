package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

public class Guard extends Enemy {
    private int patrolRange;
    private boolean movingRight = true;

    public Guard(GamePanel gp) {
        super(gp);
        setDefault();
    }

    // Sets default values
    @Override
    public void setDefault() {
        x = 100;
        y = 100;
        speed = 2;
        patrolRange = 100; // Range for patrol
    }

    // Update method for guard movement
    @Override
    public void update() {
        if (movingRight) {
            x += speed;
            if (x > patrolRange) {
                movingRight = false; // Change direction
            }
        } else {
            x -= speed;
            if (x < 0) {
                movingRight = true; // Change direction
            }
        }
    }

    // Draw method for guard entity
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.yellow); // Color of the guard
        g2.fillRect(x, y, gp.tilesize - 3, gp.tilesize - 3); // Drawing the guard
    }
}