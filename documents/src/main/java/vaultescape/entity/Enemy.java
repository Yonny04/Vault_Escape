package vaultescape.entity;

import java.util.Random;

import vaultescape.map.GamePanel;

public class Enemy extends Entity {

    public long lastCollisionTime = 0;  
    public static final long COOLDOWN = 500;

    Random r = new Random();

    //private int enemyMove = r.nextInt(4);
    
    public Enemy(GamePanel gp){
        super(gp);
    }

    public double getSpeed(){
        return speed;
    }
    public void setSpeed(double value){
        this.speed = value;
    }
    // Update method for enemy entity
    public void update(){
        //while(alive == true){
            //move towards player
        //}
    }

}
