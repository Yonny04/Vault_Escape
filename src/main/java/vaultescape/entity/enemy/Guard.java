package vaultescape.entity.enemy;

import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

import java.awt.*;
import java.util.Random;

/**
 * Represents a Guard enemy entity that patrols between two points in either a horizontal or vertical direction.
 * The Guard can reverse direction upon reaching its patrol endpoints or if obstructed.
 */
public class Guard extends Enemy {
    private Vector start; // Starting position
    private Vector end; // Ending position

    private Vector last; // Old Position since last moveTime
    private Timer moveTime = new Timer(1.0);
    
    private boolean goingEnd = true; // Direction of movement: true means moving towards the end position
    private boolean horizontal; // Direction type: true if movement is horizontal, false if vertical

    /**
     * Constructs a Guard entity with a specified patrol range.
     *
     * @param gp the game panel associated with this guard
     * @param start the starting position of this guard's path
     * @param end the end position of this guard's path
     */
    public Guard(GamePanel gp, Vector start) {
        super(gp, start);
        this.speed = 2;
        setPath();
        setSpritesheet("/entity/enemy/guard/spritesheet.png", 4, 4);
    }

    public void setPath() {
        this.start = getPosition();
        this.last = start;
        Random random = new Random();
        boolean isHorizontal = random.nextBoolean();
        Vector end = new Vector();
        int moveRange = random.nextInt(0,2);
        if (moveRange == 0) moveRange = -1;
        moveRange *= random.nextInt(2,5) * 64;
        end.x = isHorizontal ? start.x + moveRange : start.x;
        end.y = isHorizontal ? start.y : start.y + moveRange;
        end.x = Math.min(end.x, gp.MAP_SIZE.x - Vector.TILE_SIZE.x);
        end.y = Math.min(end.y, gp.MAP_SIZE.y - Vector.TILE_SIZE.y);
        this.end = end;
        this.horizontal = (start.x != end.x);
        goingEnd = true;
    }
    /**
     * Updates the guard's position along its patrol path. If the guard reaches the end of the path,
     * it reverses direction. Additionally, if the guard's speed exceeds the set maximum, it is capped.
     */
    @Override
    public void update() {
        if (speed > 7) speed = 7;
        
        if (moveTime.isTimeUp()) {
            if (last.equals(getPosition())) reverse();
            else last = rect.getPosition();
            moveTime.start();
        }
        Vector delta;
        if (goingEnd) delta = end.subtract(rect);
        else delta = start.subtract(rect);
        
        if (horizontal){
            if (delta.x < 0) move(Direction.RIGHT);
            else move(Direction.RIGHT);
        } else {
            if (delta.y > 0) move(Direction.DOWN);
            else move(Direction.UP);
        }
        if (rect.equals(start) || rect.equals(end)) reverse();
        playAnimation();
    }

    /**
     * Reverses the guard's direction along its patrol path.
     */
    private void reverse() {goingEnd = !goingEnd;}

    /**
     * Draws the guard entity. With debug collisions enabled, the 
     * start and end positions it oscillates between are drawn to aid in debugging AI.
     *
     * @param g2 the Graphics2D object used for rendering the dog and its chase range
     */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (drawCollisions) {
            Vector player = gp.getPlayer().getPosition();
            Vector playerScreen = gp.getPlayer().getScreenPosition();
            Vector startScreen = start.subtract(player).add(playerScreen);
            Vector endScreen = end.subtract(player).add(playerScreen);
            if (rect.x + 2*Vector.TILE_SIZE.x > player.x - playerScreen.x && 
            rect.x - 2*Vector.TILE_SIZE.x < player.x + playerScreen.x && 
            rect.y + 2*Vector.TILE_SIZE.y > player.y - playerScreen.y && 
            rect.y - 2*Vector.TILE_SIZE.y < player.y + playerScreen.y) {
                g2.setColor(new Color(0.0f, 0.9f, 0.2f, 0.2f));
                g2.fillRect(startScreen.x+32,startScreen.y+32,rect.w/2,rect.h/2);
                g2.setColor(new Color(0.0f, 0.2f, 0.9f, 0.2f));
                g2.fillRect(endScreen.x+32,endScreen.y+32,rect.w/2,rect.h/2);
            }
        }
    }

    @Override
    public void attack() {
        gp.getSFX().play("hit");
        gp.getTimer().decreaseTime(5);
        super.attack();
    }
}
