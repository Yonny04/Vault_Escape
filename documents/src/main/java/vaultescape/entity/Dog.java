package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import vaultescape.map.GamePanel;

public class Dog extends Enemy {
    private int chaseRange; // The range within which the dog will chase the player
    private Random random;

    public Dog(GamePanel gp) {
        super(gp);
        this.chaseRange = 100; // Set the chase range
        this.random = new Random();
        setDefault();
    }

    // Sets default values
    @Override
    public void setDefault() {
        x = random.nextInt(gp.screenWidth); // Random initial x position
        y = random.nextInt(gp.screenHeight); // Random initial y position
        speed = 3; // Speed of the dog
    }

    // Update method for dog chasing the player
    @Override
    public void update() {
        if (isPlayerInRange()) {
            chasePlayer();
        }
    }

    // Check if the player is within chase range
    private boolean isPlayerInRange() {
        int playerX = gp.player.x; // Assuming player has public access to x and y
        int playerY = gp.player.y;
        return Math.abs(playerX - x) < chaseRange && Math.abs(playerY - y) < chaseRange;
    }

    // Logic for chasing the player
    private void chasePlayer() {
        if (gp.player.x > x) {
            x += speed; // Move right
        } else {
            x -= speed; // Move left
        }

        if (gp.player.y > y) {
            y += speed; // Move down
        } else {
            y -= speed; // Move up
        }

        // Ensure the dog stays within the bounds of the game panel
        if (x < 0) x = 0;
        if (x > gp.screenWidth - gp.tilesize) x = gp.screenWidth - gp.tilesize;
        if (y < 0) y = 0;
        if (y > gp.screenHeight - gp.tilesize) y = gp.screenHeight - gp.tilesize;
    }

    // Draw method for dog entity
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.brown); // Color of the dog
        g2.fillRect(x, y, gp.tilesize - 3, gp.tilesize - 3); // Drawing the dog
    }
}
