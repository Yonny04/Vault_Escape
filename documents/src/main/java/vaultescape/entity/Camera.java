package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

public class Camera extends Enemy {
    private int detectionRange;

    public Camera(GamePanel gp) {
        super(gp);
        setDefault();
    }

    // Sets default values
    @Override
    public void setDefault() {
        x = 200;
        y = 200;
        detectionRange = 50; // Detection range of the camera
    }

    // Update method for camera logic
    @Override
    public void update() {
        // Static camera, no movement
        // Implement detection logic here if the player comes within the range
    }

    // Draw method for camera entity
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.blue); // Color of the camera
        g2.fillRect(x, y, gp.tilesize - 3, gp.tilesize - 3); // Drawing the camera
        // Optionally, draw detection range
        g2.setColor(Color.red);
        g2.drawOval(x - detectionRange, y - detectionRange, detectionRange * 2, detectionRange * 2);
    }
}
