package vaultescape.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import vaultescape.map.GamePanel;
import vaultescape.map.KeyDetector;
import vaultescape.ui.Sprite2D;

public class Player extends Entity {
    KeyDetector keyh;
    public boolean alive = true;
    BufferedImage spritesheet;  

    private int score = 0;

    // Constructor
    public Player(GamePanel gp, KeyDetector keyh) {
        super(gp);
        this.keyh = keyh;
        setDefault();
        setHitbox(48, 32);
        setSpritesheet("/entity/player/spritesheet.png", 4, 4);  
    }
    
    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }

    // Sets default values
    public void setDefault() {
        x = 8 * gp.tilesize;  
        y = 8 * gp.tilesize;
        screenX = gp.numScreenCols*gp.tilesize/2 - (gp.tilesize / 2);
        screenY = gp.numScreenRows*gp.tilesize/2 - (gp.tilesize / 2);
        speed = 5; 
    }

    // Update method for player entity
    public void update() {
        int oldX = x;  
        int oldY = y;  

        // Handle movement input from the key detector
        if (keyh.w || keyh.a || keyh.s || keyh.d) {
            
            if (keyh.a) {
                x -= speed;
                direction = 0;  
            }
            else if (keyh.d) {
                x += speed;
                direction = 1;
            }
                // Check for collisions with walls
            for (Sprite2D wall : gp.getTileGenerator().walls) {
                if (isTouching(wall)) {
                    // go back if collided (?)
                    x = oldX;
                    break;
                }
            }
            if (keyh.w) {
                y -= speed;
                direction = 2; 
            }
            else if (keyh.s) {
                y += speed;
                direction = 3; 
            }
            for (Sprite2D wall : gp.getTileGenerator().walls) {
                if (isTouching(wall)) {
                    // go back if collided (?)
                    y = oldY;
                    break;
                }
            }
            // Increment sprite animation counter
            spriteCounter += 0.1f;
            if (spriteCounter > 3.9f) spriteCounter = 0.0f;
        } else spriteCounter = 1.0f;  // Reset sprite counter when idle
        
        // Set player animation frame from the floored spriteCounter
        setFrame((int)Math.floor(spriteCounter),direction);
    }
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, screenX, screenY, width, height, null);
        super.draw(g2);
    }
} 
