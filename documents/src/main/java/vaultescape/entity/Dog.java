package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

public class Dog extends Enemy{

    private final  int chaseRange;
    int timer = 0;
    int vectorX;
    int vectorY;
    private boolean isFrozen = false;
    private long frozenTime = 0; 


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
        // Check if the dog is frozen
        if (isFrozen) {
            if (System.currentTimeMillis() > frozenTime) {
                isFrozen = false; // Unfreeze
            }
            return; // Skip movement if frozen
        }

        // Check for player in range and chase if not frozen
        if (isPlayerInRange()) {
            chasePlayer();
            timer = 81;
        } else {
            wander();
            timer++;
        }
        setFrame((int)Math.floor(spriteCounter), direction);
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
        } else spriteCounter = 1.0f;
        
    }
    // Check if the player is within chase range
    private boolean isPlayerInRange() {
        int playerX = gp.getPlayer().getX(); // Assuming player has public access to x and y
        int playerY = gp.getPlayer().getY();
        return Math.abs(playerX - x) < chaseRange && Math.abs(playerY - y) <= chaseRange;
    }

    // Freezes the dog
    public void freezeDog(long duration){
        isFrozen = true;
        frozenTime = System.currentTimeMillis() + duration*100;
    }

    // Dog's cooldown for colliding
    public boolean canCollide() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastCollisionTime) >= COOLDOWN;
    }

    // Records the number of collision for the dog
    public void recordCollision() {
        lastCollisionTime = System.currentTimeMillis();
    }

    // Logic for chasing the player
    private void chasePlayer() {
        int playerX = gp.getPlayer().getX();
        int playerY = gp.getPlayer().getY();
        int oldX = x;
        int oldY = y;
    
        // Calculate the difference in x and y positions between the dog and player
        int deltaX = playerX - x;
        int deltaY = playerY - y;
    
        // Move in the x direction
        // Player right
        if (deltaX > 0) { 
            x += speed;
            direction = 1;
        
        // Player left
        } else if (deltaX < 0) {
            x -= speed;
            direction = 0; // Left direction
        }
        // Check if the dog can move in the x direction reset if true
        if (!canMove()) {
            x = oldX;
        }
        
        // Move in the y direction
        // Player down
        if (deltaY > 0) { // Player is below
            y += speed;
            direction = 3;

        // Player up            
        } else if (deltaY < 0) {
            y -= speed;
            direction = 2;
        }

        // Check if the dog can move in the y direction and reset y if collision
        if (!canMove()) {
            y = oldY;
        }
    
        // Increment the sprite animation counter
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
