package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

public class Camera extends Enemy {
    private int detectionRange;
    private long lastDetectionTime = 0;
    private static final long SPEEDUP_COOLDOWN = 3000;
    

    public Camera(GamePanel gp, int x, int y, int detectionRange) {
        super(gp);
        this.x = x;
        this.y = y;
        this.width = 64; 
        this.height = 64; 
        this.detectionRange = detectionRange;
        setHitbox(16, 16);
        setSpritesheet("/entity/camera/spritesheet.png", 2, 3);
        setFrame(0);
    }

    // Update method for camera logic
    @Override
    public void update() {
        spriteCounter += 0.02f;
        if (spriteCounter > 5.98) spriteCounter = 0.0f;
        setFrame((int)spriteCounter);
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
        super.draw(g2);
        g2.setColor(Color.red);
        g2.drawOval(screenX  + width/2 - detectionRange, screenY + height/2 - detectionRange, detectionRange * 2, detectionRange * 2);
    }
}
