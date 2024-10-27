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
        x = 100; 
        y = 100;
        speed = 5;
    }

    // Update method for player entity
    public void update(){
        if(keyh.w == true)  {y -= speed; System.out.println("W");} 
        if(keyh.a == true)  x -= speed; 
        if(keyh.s == true)  {y += speed; System.out.println("S");}
        if(keyh.d == true)  x += speed; 
        
        if(alive == false){
            //shows menu for when the player dies
            //for later...
            System.out.println("Player Died");

        }
        if (x < 0) x = 0;
        if (x > gp.maxColume - gp.tilesize) x = gp.maxColume - gp.tilesize; // Assuming player width is 32
        if (y < 0) y = 0;
        if (y > gp.maxRow - gp.tilesize) y = gp.maxRow - gp.tilesize; // Assuming player height is 32
    }

    //draw method for player entity
    public void draw(Graphics2D g2){
        g2.setColor(Color.white); //color of the character
        g2.fillRect(x, y, gp.tilesize, gp.tilesize); //making the "character"
    }
}
