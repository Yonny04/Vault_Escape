package vaultescape.reward;

import java.awt.Graphics2D;
import java.awt.Rectangle;

abstract class Reward {
    int x, y;
    public abstract void draw(Graphics2D g2);

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public abstract Rectangle getBounds();
}
