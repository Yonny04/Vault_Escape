package vaultescape.entity;

public abstract class Entity {
    int x, y;
    int speed;
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int value){
        this.x = value;
    }
    public void setY(int value){
        this.y = value;
    }
}
