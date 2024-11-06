package vaultescape.entity.enemy;

import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

import java.awt.*;

/**
 * Represents a Guard enemy entity that patrols between two points in either a horizontal or vertical direction.
 * The Guard can reverse direction upon reaching its patrol endpoints or if obstructed.
 */
public class Guard extends Enemy {
    private Vector2 start; // Starting position
    private Vector2 end; // Ending position

    private Vector2 last; // Old Position since last moveTime
    private Timer moveTime = new Timer(2.5);
    
    private boolean goingEnd = true; // Direction of movement: true means moving towards the end position
    private boolean horizontal; // Direction type: true if movement is horizontal, false if vertical

    /**
     * Constructs a Guard entity with a specified patrol range.
     *
     * @param gp the game panel associated with this guard
     * @param start the starting position of this guard's path
     * @param end the end position of this guard's path
     */
    public Guard(GamePanel gp, Vector2 start, Vector2 end) {
        super(gp, start);
        this.speed = 2;
        this.start = start;
        this.end = end;
        this.last = start;
        this.horizontal = (start.x != end.x);
        setSpritesheet("/entity/enemy/guard/spritesheet.png", 4, 4);
    }

    /**
     * Updates the guard's position along its patrol path. If the guard reaches the end of the path,
     * it reverses direction. Additionally, if the guard's speed exceeds the set maximum, it is capped.
     */
    @Override
    public void update() {
        if (speed > 5) speed = 5;
        
        if (moveTime.isTimeUp()) {
            if (last.equals(getPosition())) reverse();
            else last = rect.getPosition();
            moveTime.start();
        }

        if (horizontal) {
            if (goingEnd) move(Direction.RIGHT);
            else move(Direction.LEFT);
            if (rect.x >= end.x || rect.x <= start.x) reverse();
        } else {
            if (goingEnd) move(Direction.DOWN);
            else move(Direction.UP);
            if (rect.y >= end.y || rect.y <= start.y) reverse();
        }
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
        if (_drawCollisions) {
            Vector2 player = gp.getPlayer().getPosition();
            Vector2 playerScreen = gp.getPlayer().getScreenPosition();
            Vector2 startScreen = start.subtract(player).add(playerScreen);
            Vector2 endScreen = end.subtract(player).add(playerScreen);
            if (rect.x + 2*gp.TILE_SIZE.x > player.x - playerScreen.x && 
            rect.x - 2*gp.TILE_SIZE.x < player.x + playerScreen.x && 
            rect.y + 2*gp.TILE_SIZE.y > player.y - playerScreen.y && 
            rect.y - 2*gp.TILE_SIZE.y < player.y + playerScreen.y) {
                g2.setColor(new Color(0.0f, 0.9f, 0.2f, 0.2f));
                g2.fillRect(startScreen.x+32,startScreen.y+32,rect.w/2,rect.h/2);
                g2.setColor(new Color(0.0f, 0.2f, 0.9f, 0.2f));
                g2.fillRect(endScreen.x+32,endScreen.y+32,rect.w/2,rect.h/2);
            }
        }
    }
}
