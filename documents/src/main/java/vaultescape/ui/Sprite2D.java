package vaultescape.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

public class Sprite2D extends Sprite {
    protected GamePanel gp;
    protected int screenX, screenY;
    private boolean _drawCollisions = true;

    public Sprite2D(GamePanel gp) {
        this.gp = gp;
    }
    public int getScreenX(){
        return screenX;
    }
    public int getScreenY(){
        return screenY;
    }
    /**
     * Draw the sprite at the screen location with the given image
     * @param g2
     */
    @Override
    public void draw(Graphics2D g2) {
        int playerX = gp.getPlayer().getX();
        int playerScreenX = gp.getPlayer().getScreenX();
        int playerY = gp.getPlayer().getY();
        int playerScreenY = gp.getPlayer().getScreenY(); 
        screenX = x - playerX + playerScreenX;
        screenY = y - playerY + playerScreenY;
        if (x + gp.tilesize > playerX - playerScreenX && 
            x - gp.tilesize < playerX + playerScreenX && 
            y + gp.tilesize > playerY - playerScreenY && 
            y - gp.tilesize < playerY + playerScreenY) {
            g2.drawImage(image, screenX, screenY, width, height, null);
            if (_drawCollisions) drawHitbox(g2);
        }
    }

    public void drawHitbox(Graphics2D g2) {
        if (_drawCollisions){
            g2.setColor(new Color(0,(float)1.0,(float)1.0,(float)0.5));
            g2.drawRect(screenX + (width - hitboxWidth)/2, screenY + (height - hitboxHeight)/2, hitboxWidth, hitboxHeight);
        }
    }
    static public Sprite2D createSprite2D(GamePanel gp, int x, int y, int width, int height) {
        Sprite2D newSprite = new Sprite2D(gp);
        newSprite.x = x;
        newSprite.y = y;
        newSprite.width = width;
        newSprite.height = height;
        return newSprite;
    }
}
