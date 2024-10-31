package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

public class Camera extends Enemy {
    private int detectionRange;
    private boolean isPlayerInRange;
    private long lastDetectionTime = 0;
    private static final long SPEEDUP_COOLDOWN = 3000;
    

    public Camera(GamePanel gp, int x, int y, int detectionRange) {
        super(gp);
        this.x = x;
        this.y = y;
        this.width = 16; 
        this.height = 16; 
        setDefault();
    }

    // Sets default values
    @Override
    public void setDefault() {
        detectionRange = 100;
        isPlayerInRange = false;
        setHitbox(16, 16);
    }

    // Update method for camera logic
    @Override
    public void update() {
        // Should the logic be here instead ?
    }

    public boolean isPlayerInRange() {
        int playerX = gp.getPlayer().getX();
        int playerY = gp.getPlayer().getY();
        return Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2)) <= detectionRange;
    }

    public boolean canDetect() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastDetectionTime) >= SPEEDUP_COOLDOWN;
    }

    public void recordDetection(){
        lastDetectionTime = System.currentTimeMillis();
    }

    // Draw method for camera entity
    @Override
    public void draw(Graphics2D g2) {
        if (image == null) {
            g2.setColor(Color.ORANGE); 
            g2.fillRect(x, y, width, height); 
        } else {
            super.draw(g2);
        }

        g2.setColor(Color.red);
        g2.drawOval(x  + width/2 - detectionRange, y + height/2 - detectionRange, detectionRange * 2, detectionRange * 2);
    }
}
