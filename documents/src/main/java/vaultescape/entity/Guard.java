package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import vaultescape.map.GamePanel;
import vaultescape.map.Wall;
import java.awt.Rectangle;

public class Guard extends Enemy {
    private int x1, y1; 
    private int x2, y2; 
    private boolean goingEnd = true; 
    private boolean horizontal; 

    private long lastCollisionTime = 0;  
    private static final long COOLDOWN = 500;

    public Guard(GamePanel gp, int x1, int y1, int x2, int y2) {
        super(gp);
        setDefault();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x = x1;
        this.y = y1;
        this.horizontal = (x1 != x2);
    }

    @Override
    public void setDefault() {
        speed = 2; 
    }

    @Override
    public void update() {
        if (horizontal) {
            if (goingEnd) x += speed;
            else x -= speed;
            if (x >= x2 || x <= x1) reverse();
        } else {
            if (goingEnd) y += speed;
            else y -= speed;
            if (y >= y2 || y <= y1) reverse();
        }
        if (!canMove(x, y)) {
            reverse(); 
        }
    }

    private void reverse() {
        goingEnd = !goingEnd;
    }

    private boolean canMove(int x, int y) {
        for (Wall wall : gp.getTileGenerator().walls) {
            if (wall.getBounds().intersects(
                    new Rectangle(x, y, gp.tilesize - 3, gp.tilesize - 3))) {
                return false;
            }
        }
        return true;
    }

    public boolean canCollide() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastCollisionTime) >= COOLDOWN;
    }

    public void recordCollision() {
        lastCollisionTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW); 
        g2.fillRect(x, y, gp.tilesize - 3, gp.tilesize - 3); 
    }
}
