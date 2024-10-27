package vaultescape.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import vaultescape.map.GamePanel;
import vaultescape.map.KeyDetector;
import vaultescape.map.Wall;

public class Player extends Entity{
    GamePanel gp;
    KeyDetector keyh;
    public boolean alive = true;
    Wall wall;

    int direction = 1;
    double spriteCounter;
    BufferedImage[][] spritesheet;

    public Player(GamePanel gp, KeyDetector keyh){
        this.gp = gp;
        this.keyh = keyh;
        setDefault();
        setPlayerSpritesheet();
    }

    //Sets default values
    public void setDefault(){
        x = 50;
        y = 50;
        speed = 5;
    }

    //Sets player spritesheet
    public void setPlayerSpritesheet(){
        try {
            spritesheet = new BufferedImage[4][3];
                for (int i = 0; i < 3; i++) {
                    spritesheet[0][i] = ImageIO.read(getClass().getResourceAsStream(String.format("/entity/player/up%d.png",i+1)));
                    spritesheet[1][i] = ImageIO.read(getClass().getResourceAsStream(String.format("/entity/player/down%d.png",i+1)));
                    spritesheet[2][i] = ImageIO.read(getClass().getResourceAsStream(String.format("/entity/player/left%d.png",i+1)));
                    spritesheet[3][i] = ImageIO.read(getClass().getResourceAsStream(String.format("/entity/player/right%d.png",i+1)));
                }
        } catch (Exception e) {e.printStackTrace();}
    }

    // Update method for player entity
    public void update(){
        // Handle player input for movement
        if (keyh.w || keyh.a || keyh.s || keyh.d) {
            if (keyh.w == true) { 
                y -= speed; 
                direction = 0;
            }
            if (keyh.s == true) { 
                y += speed; 
                direction = 1;
            }
            if (keyh.a == true) { 
                x -= speed;
                direction = 2;
            }
            if (keyh.d == true) { 
                x += speed;
                direction = 3;
            }
            spriteCounter++;
            if (spriteCounter > 29.0) spriteCounter = 0.0;
        } else {
            spriteCounter = 10.0;
        } 

        // Borders for the outer line
        if (x < gp.tilesize) x = gp.tilesize;
        if (x > gp.screenWidth - gp.tilesize) x = gp.screenWidth - gp.tilesize; 
        if (y < gp.tilesize) y = gp.tilesize;
        if (y > gp.screenHeight - gp.tilesize) y = gp.screenHeight - gp.tilesize;

        // wall.collision = false;
        // gp.cck.checkTile(this);
    }

    //draw method for player entity
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int spriteNum = (int)Math.floor(spriteCounter / 10.0);
        image = spritesheet[direction][spriteNum];
        g2.drawImage(image, x, y, gp.tilesize, gp.tilesize, null);
    }
}
