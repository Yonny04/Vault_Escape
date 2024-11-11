package game.tile.entity.character.enemy;

import game.object.Vector;
import game.panel.GamePanel;

import java.awt.Graphics2D;
import java.util.Random;


/**
 * Represents a Guard enemy entity that patrols between two points in either a horizontal or vertical direction.
 * The Guard can reverse direction upon reaching its patrol endpoints or if obstructed.
 */
public class Guard extends Enemy {
    
    private boolean isHorizontal; // Direction type: true if movement is horizontal, false if vertical
    private boolean goingEnd = true;
    private int timeReduction = 5;

    /**
     * Constructs a Guard entity with a specified patrol range.
     *
     * @param gp the game panel associated with this guard
     * @param start the starting position of this guard's path
     */
    public Guard(GamePanel gp, Vector start) {
        super(gp, start);
        this.speed = 2;
        setPath();
        getAnimationPlayer().setSpritesheet("/tile/entity/character/enemy/guard/spritesheet.png", 4, 4);
        getAnimationPlayer().setFrame(9);
    }

    public void setPath() {
        Random random = new Random();
        this.isHorizontal = random.nextBoolean();
    }
    /**
     * Updates the guard's position along its patrol path. If the guard reaches the end of the path,
     * it reverses direction. Additionally, if the guard's speed exceeds the set maximum, it is capped.
     */
    @Override
    public void update() {
        if (speed > 7) speed = 7;
        
        Vector old = rect.getPosition();
        if (isHorizontal){
            if (goingEnd) moveUnsafe(Direction.RIGHT);
            else moveUnsafe(Direction.LEFT);
            if (!canMove()) {turnAround(); rect.x = old.x;}
        } else {
            if (goingEnd) moveUnsafe(Direction.DOWN);
            else moveUnsafe(Direction.UP);
            if (!canMove()) {turnAround(); rect.y = old.y;}
        }
        getAnimationPlayer().playAnimation(direction.name());
        super.update();
    }

    /**
     * Reverses the guard's direction along its patrol path.
     */
    private void turnAround() {goingEnd = !goingEnd;}

    /**
     * Draws the guard entity. With debug collisions enabled, the 
     * start and end positions it oscillates between are drawn to aid in debugging AI.
     *
     * @param g2 the Graphics2D object used for rendering the dog and its chase range
     */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if ((double)(attackCooldown.getTimeLeft() / 1000.0) > 0.5 && !canAttack()) {
            attackLabel.setText(String.format("-%ds",timeReduction));
            attackLabel.draw(g2,gp.getPlayer().getScreenPosition());
        }
    }

    @Override
    public void attack() {
        gp.getSFX().play("hit");
        gp.getTimer().decreaseTime(timeReduction);
        super.attack();
    }
}
