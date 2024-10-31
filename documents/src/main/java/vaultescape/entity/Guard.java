package vaultescape.entity;

import vaultescape.map.GamePanel;
import vaultescape.ui.Sprite2D;

public class Guard extends Enemy {
    private int x1, y1; 
    private int x2, y2; 
    private boolean goingEnd = true; 
    private boolean horizontal; 

    private long lastCollisionTime = 0;  
    private static final long COOLDOWN = 500;

    public Guard(GamePanel gp, int x1, int y1, int x2, int y2) {
        super(gp);
        setDefault();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x = x1;
        this.y = y1;
        this.horizontal = (x1 != x2);
        setSpritesheet("/entity/guard/spritesheet.png", 4, 4);
        setHitbox(32, 32);
    }

    @Override
    public void setDefault() {
        speed = 2; 
    }

    @Override
    public void update() {
        System.out.println(speed);
        if(speed > 5){
            speed = 5;
        }
        if (horizontal) {
            if (goingEnd) {
                x += speed;
                direction = 1;
            }
            else {
                x -= speed;
                direction = 0;
            }
            if (x >= x2 || x <= x1) reverse();
        } else {
            if (goingEnd) {y += speed; direction = 3;}
            else {y -= speed; direction = 2;}
            if (y >= y2 || y <= y1) reverse();
        }
        if (!canMove(x, y)) {
            reverse(); 
        }

        spriteCounter += 0.1f;
        if (spriteCounter > 3.9f) spriteCounter = 0.0f;
        setFrame((int)Math.floor(spriteCounter),direction);

    }

    private void reverse() {
        goingEnd = !goingEnd;
    }

    private boolean canMove(int x, int y) {
        for (Sprite2D wall : gp.getTileGenerator().walls) {
            if (isTouching(wall)) {
                return false;
            }
        }
        return true;
    }

    public boolean canCollide() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastCollisionTime) >= COOLDOWN;
    }

    public void recordCollision() {
        lastCollisionTime = System.currentTimeMillis();
    }
}
