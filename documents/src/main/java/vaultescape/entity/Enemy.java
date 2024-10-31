package vaultescape.entity;

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
