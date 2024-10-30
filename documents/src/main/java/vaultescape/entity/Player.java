package vaultescape.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import vaultescape.map.GamePanel;
import vaultescape.map.KeyDetector;
import vaultescape.map.Wall;

public class Player extends Entity {
    KeyDetector keyh;
    public boolean alive = true;
    Wall wall;  

    int direction = 1;  
    double spriteCounter;  
    BufferedImage spritesheet;  

    private int score = 0;

    // Constructor
    public Player(GamePanel gp, KeyDetector keyh) {
        super(gp);
        this.keyh = keyh;
        this.width -= 3;
        this.height -= 3;
        setDefault();  
        setPlayerSpritesheet();  
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);  
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
        speed = 5; 
    }

     //Sets player spritesheet
    public void setPlayerSpritesheet() {
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream(String.format("/entity/player/spritesheet.png")));
        } catch (Exception e) {e.printStackTrace();}
    }

    // Update method for player entity
    public void update() {
        int oldX = x;  
        int oldY = y;  

        // Handle movement input from the key detector
        if (keyh.w || keyh.a || keyh.s || keyh.d) {
            if (keyh.w) {
                y -= speed;
                direction = 2; 
            }
            else if (keyh.s) {
                y += speed;
                direction = 3; 
            }
            if (keyh.a) {
                x -= speed;
                direction = 0;  
            }
            else if (keyh.d) {
                x += speed;
                direction = 1;
            }

            // Increment sprite animation counter
            spriteCounter += 0.1;
            if (spriteCounter > 3.9) spriteCounter = 0.0;
        } else {
            spriteCounter = 1.0;  // Reset sprite counter when idle
        }

        // Check for collisions with walls
        for (Wall wall : gp.getTileGenerator().walls) {
            if (isTouching(wall)) {
                // go back if collided (?)
                x = oldX;
                y = oldY;
                spriteCounter = 1.0;  // Reset sprite counter when idle
                break;
            }
        }
        // Set player animation frame from the floored spriteCounter
        int spriteNum = (int) Math.floor(spriteCounter);
        BufferedImage currentFrame = spritesheet.getSubimage(spriteNum*16,direction*16,16,16);
        this.setImage(currentFrame);
    }
} 
