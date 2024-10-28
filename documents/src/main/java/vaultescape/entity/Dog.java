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
        int playerX = gp.getPlayer().getX(); // Assuming player has public access to x and y
        int playerY = gp.getPlayer().getY();
        return Math.abs(playerX - x) < chaseRange && Math.abs(playerY - y) < chaseRange;
    }

    // Logic for chasing the player
    private void chasePlayer() {
        //
    }

    // Draw method for dog entity
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.orange); // Color of the dog
        g2.fillRect(x, y, gp.tilesize - 3, gp.tilesize - 3); // Drawing the dog
    }
}
