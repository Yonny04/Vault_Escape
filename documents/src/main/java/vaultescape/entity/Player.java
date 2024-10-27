package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;
import vaultescape.map.KeyDetector;

public class Player extends Entity{
    GamePanel gp;
    KeyDetector keyh;
    public boolean alive = true;

    public Player(GamePanel gp, KeyDetector keyh){
        this.gp = gp;
        this.keyh = keyh;
        setDefault();
    }

    //Sets default values
    public void setDefault(){
        x = 10;
        y = 10;
        speed = 5;
    }

    // Update method for player entity
    public void update(){
        if(keyh.w == true)  y -= speed; 
        if(keyh.a == true)  x -= speed;
        if(keyh.s == true)  y += speed; 
        if(keyh.d == true)  x += speed; 
        
        if(alive == false){
            //shows menu for when the player dies
            //for later...
            System.out.println("Player Died");

        }
        if (x < 0) x = 0;
        if (x > gp.screenWidth - gp.tilesize) {x = gp.screenWidth - gp.tilesize;} 
        if (y < 0) y = 0;
        if (y > gp.screenHeight - gp.tilesize) y = gp.screenHeight - gp.tilesize;
    }

    //draw method for player entity
    public void draw(Graphics2D g2){
        g2.setColor(Color.white); //color of the character
        g2.fillRect(x, y, gp.tilesize, gp.tilesize); //making the "character"
    }
}
