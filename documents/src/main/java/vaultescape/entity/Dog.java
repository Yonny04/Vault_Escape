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
    private Random random = new Random();

    public Dog(GamePanel gp, int startX, int startY) {
        super(gp);
        this.x = startX;
        this.y = startY;
        this.width = 64; 
        this.height = 64; 
        this.chaseRange = 250;
        this.speed = 2;
        isChasing = false;
        setHitbox(32, 32); 
        setSpritesheet("/entity/dog/spritesheet.png", 4, 4);
        setDefault();
    }
    // Sets default values
    @Override
    public void setDefault() {
        x = random.nextInt(gp.screenWidth); // Random initial x position
        y = random.nextInt(gp.screenHeight); // Random initial y position
        speed = 3; // Speed of the dog
        direction = 0;
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
        if (playerY > y) {nextY += speed; direction = 3;}
        else if (playerY < y) {nextY -= speed; direction = 2;}

        if (playerX > x) {nextX += speed; direction = 1;}
        else if (playerX < x) {nextX -= speed; direction = 0;}
        
        if (canMove(nextX, nextY)) {
            x = nextX;
            y = nextY;
            // Increment sprite animation counter
            spriteCounter += 0.1f;
            if (spriteCounter > 3.9f) spriteCounter = 0.0f;
        } else spriteCounter = 1.0f;
        setFrame((int)Math.floor(spriteCounter),direction);


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
        super.draw(g2);
        g2.setColor(new Color(1.0f, 0.5f, 0.0f, 0.2f));
        g2.drawOval(screenX + width / 2 - chaseRange, screenY + height / 2 - chaseRange, chaseRange * 2, chaseRange * 2);
    }
}
