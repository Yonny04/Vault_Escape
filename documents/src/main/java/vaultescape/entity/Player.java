package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;
import vaultescape.map.KeyDetector;
import vaultescape.map.Wall;

public class Player extends Entity{
    GamePanel gp;
    KeyDetector keyh;
    public boolean alive = true;
    Wall wall;

    public Player(GamePanel gp, KeyDetector keyh){
        this.gp = gp;
        this.keyh = keyh;
        setDefault();
    }

    //Sets default values
    public void setDefault(){
        x = 50;
        y = 50;
        speed = 5;
    }

    // Update method for player entity
    public void update(){
        if(keyh.w == true)  y -= speed; 
        if(keyh.a == true)  x -= speed;
        if(keyh.s == true)  y += speed; 
        if(keyh.d == true)  x += speed; 

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
        g2.setColor(Color.white); //color of the character
        g2.fillRect(x, y, gp.tilesize, gp.tilesize); //making the "character"
    }
}
