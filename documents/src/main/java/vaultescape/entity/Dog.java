package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

public class Dog extends Enemy {

    private int chaseRange;
    int timer = 0;
    int vectorX;
    int vectorY;

    public Dog(GamePanel gp, int startX, int startY) {
        super(gp);
        this.x = startX;
        this.y = startY;
        this.width = 64; 
        this.height = 64; 
        this.chaseRange = 250;
        vectorX = 0;
        vectorY = 0;
        this.speed = 3;
        setHitbox(40, 32); 
        setSpritesheet("/entity/dog/spritesheet.png", 4, 4);
    }

    // Update method for dog chasing the player
    @Override
    public void update() {
        if (isPlayerInRange()) {
            chasePlayer();
            timer = 81;
            // Increment sprite animation counter
        }
        else {
            wander();
            timer++;
        }
        setFrame((int)Math.floor(spriteCounter),direction);
    }

    private void wander() {
        if (timer > 180) {
            vectorX = r.nextInt(3) - 1; 
            vectorY = r.nextInt(3) - 1; 
            timer = 0;
        }
        if (timer < 80)
        {
            int oldX = x;
            int oldY = y;
            if (vectorY > 0) {y += speed*vectorY; direction = 3;}
            else if (vectorY < 0) {y += speed*vectorY; direction = 2;}
            if (!canMove()) {y = oldY;timer = 100;}
            if (vectorX > 0) {x += speed*vectorX; direction = 1;}
            else if (vectorX < 0) {x += speed*vectorX; direction = 0;}
            if (!canMove()) {x = oldX;timer = 100;}
            spriteCounter += 0.1f;
            if (spriteCounter > 3.9f) spriteCounter = 0.0f;
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
        int playerX = gp.getPlayer().getX();
        int playerY = gp.getPlayer().getY();
        int oldX = x;
        int oldY = y;
        if (playerY > y) {y += speed; direction = 3;}
        else if (playerY < y) {y -= speed; direction = 2;}
        if (!canMove()) y = oldY;
        if (playerX > x) {x += speed; direction = 1;}
        else if (playerX < x) {x -= speed; direction = 0;}
        
        if (!canMove()) x = oldX;
        spriteCounter += 0.1f;
        if (spriteCounter > 3.9f) spriteCounter = 0.0f;


    }

    // Draw method for dog entity
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(new Color(1.0f, 0.5f, 0.0f, 0.2f));
        g2.drawOval(screenX + width / 2 - chaseRange, screenY + height / 2 - chaseRange, chaseRange * 2, chaseRange * 2);
    }
}
