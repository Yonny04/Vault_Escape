package vaultescape.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import vaultescape.map.GamePanel;

public class Enemy extends Entity {
    Random r = new Random();

    //private int enemyMove = r.nextInt(4);
    
    public Enemy(GamePanel gp){
        super(gp);
    }

    //Sets default values
    public void setDefault(){
        x = 100; 
        y = 100;
        speed = 5;
    }

    // Update method for enemy entity
    public void update(){
        //while(alive == true){
            //move towards player
        //}
    }

    //draw method for enemy entity
    @Override
    public void draw(Graphics2D g2){
        g2.setColor(Color.red); //color of the character
        g2.fillRect(x, y, gp.tilesize-3, gp.tilesize-3); //making the "character"
    }
}
