package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import vaultescape.map.GamePanel;
import vaultescape.ui.Sprite;

public class Dog extends Enemy {

    private int chaseRange;      
    private boolean isChasing;   
    private Random random;

    public Dog(GamePanel gp, int startX, int startY) {
        super(gp);
        this.x = startX;
        this.y = startY;
        this.width = 32; 
        this.height = 32; 
        this.chaseRange = 250;
        this.speed = 2;
        isChasing = false;
        setHitbox(32, 32); 
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
            isChasing = true;
        }
        else isChasing = false;
    }

    // Check if the player is within chase range
    private boolean isPlayerInRange() {
        int playerX = gp.getPlayer().getX(); // Assuming player has public access to x and y
        int playerY = gp.getPlayer().getY();
        return Math.abs(playerX - x) < chaseRange && Math.abs(playerY - y) < chaseRange;
    }

    // Logic for chasing the player
    private void chasePlayer() {
        int playerX = gp.getPlayer().getX();
        int playerY = gp.getPlayer().getY();
        int nextX = x;
        int nextY = y;
        if (playerX > x) nextX += speed;
        else if (playerX < x) nextX -= speed;

        if (playerY > y) nextY += speed;
        else if (playerY < y) nextY -= speed;
        if (canMove(nextX, nextY)) {
            x = nextX;
            y = nextY;
        }
    }
    private boolean canMove(int x, int y) {
        for (Sprite wall : gp.getTileGenerator().walls) {
            if (wall.getBounds().intersects(new Rectangle(x, y, gp.tilesize - 3, gp.tilesize - 3))) {
                return false;
            }
        }
        return true;
    }

    // Draw method for dog entity
    @Override
    public void draw(Graphics2D g2) {
        if (image == null) {
            g2.setColor(Color.ORANGE); 
            g2.fillRect(x, y, width, height); 
        } else {
            super.draw(g2);
        }
        g2.setColor(new Color(1.0f, 0.5f, 0.0f, 0.2f));
        g2.drawOval(x + width / 2 - chaseRange, y + height / 2 - chaseRange, chaseRange * 2, chaseRange * 2);
    }
}
