package game.tile.entity.character.enemy;

import game.object.Vector;
import game.panel.GamePanel;
import game.utils.Timer;

import java.awt.Graphics2D;
import java.util.Random;


/**
 * Represents a Guard enemy entity that patrols between two points in either a horizontal or vertical direction.
 * The Guard can reverse direction upon reaching its patrol endpoints or if obstructed.
 */
public class Guard extends Enemy {
    
    private boolean isHorizontal = new Random().nextBoolean(); // Direction type: true if movement is horizontal, false if vertical
    private boolean goingEnd = true;
    private int timeReduction = 3;
    private Timer wallHitCooldown = new Timer(0.4);

    /**
     * Constructs a Guard entity with a specified patrol range.
     *
     * @param gp the game panel associated with this guard
     * @param start the starting position of this guard's path
     */
    public Guard(GamePanel gp, Vector start) {
        super(gp, start);
        getAnimationPlayer().setSpritesheet("guard", 4, 4);
        setSpeed(2);
    }
    /**
     * Updates the guard's position along its patrol path. If the guard reaches the end of the path,
     * it reverses direction. Additionally, if the guard's speed exceeds the set maximum, it is capped.
     */
    @Override
    public void update() {
        if (!wallHitCooldown.isTimeUp()) {
            getAnimationPlayer().stopAnimation();
            getAnimationPlayer().setFrame(1, direction.ordinal());
            return;
        }
        if (isHorizontal){
            if (goingEnd) move(Direction.RIGHT);
            else move(Direction.LEFT);
        } else {
            if (goingEnd) move(Direction.DOWN);
            else move(Direction.UP);
        }
        
        super.update();
    }

    @Override
    public void move(Direction direction) {
        Vector oldPosition = rect.getPosition();
        moveUnsafe(direction);
        if (!canMove()) {
            turnAround();
            setPosition(oldPosition);
        }
        getAnimationPlayer().playAnimation(direction.name());
    }

    /**
     * Reverses the guard's direction along its patrol path.
     */
    private void turnAround() {
        goingEnd = !goingEnd;
        wallHitCooldown.start();
    }

    /**
     * Draws the guard entity. With debug collisions enabled, the 
     * start and end positions it oscillates between are drawn to aid in debugging AI.
     *
     * @param g2 the Graphics2D object used for rendering the dog and its chase range
     */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (gp.introFade > 0) return;
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

    @Override
    public void setSpeed(int speed) {
        wallHitCooldown.setCountdownTime(1.0/speed);
        super.setSpeed(speed);
    }
}
