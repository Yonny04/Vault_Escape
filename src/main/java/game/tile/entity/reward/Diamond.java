package game.tile.entity.reward;

import game.object.Vector;
import game.panel.GamePanel;
import game.utils.*;

/**
 * Represents a bonus reward item in the game, which has a spawn time attribute to track when it appears.
 * Bonus rewards provide additional points to the player when collected.
 */
public class Diamond extends Reward {
    private Timer spawnDuration = new Timer(11);

    /**
     * Constructs a Diamond with a specified position, point value, and spawn time.
     *
     * @param gp the game panel associated with this bonus reward
     * @param start the starting position
     */
    public Diamond(GamePanel gp, Vector start) {
        super(gp, start, 200);
        scoreLabel.setColor(ColorPalette.LIGHT_PURPLE);
    }

    int oldTime = 0;
    /**
     * Updates the entity's state, including managing the visibility based on the remaining spawn duration.
     * The visibility toggles as the remaining time decreases, creating a blinking effect.
     */
    @Override
    public void update() {
        int secondsLeft = (int) spawnDuration.getTimeLeft() / 1000;
        int newTime;
        if (secondsLeft <= 1) {
            newTime = (int) spawnDuration.getTimeLeft() / 75;
        } else if (secondsLeft <= 2) {
            newTime = (int) spawnDuration.getTimeLeft() / 125;
        } else {
            newTime = (int) spawnDuration.getTimeLeft() / 250;
        }
        if (oldTime != newTime && secondsLeft <= 4) {
            oldTime = newTime;
            visible = !visible;
        }
        super.update();
    }

    /**
     * Retrieves the spawn duration timer for this entity.
     *
     * @return The spawn duration timer.
     */
    public Timer getTimer() {
        return spawnDuration;
    }

    /**
     * Plays a sound effect for collecting a bonus reward and then 
     * performs the superclass's pickup behavior.
     */
    @Override
    public void pickup() {
        gp.getSFX().play("bonus_collect");
        super.pickup();
    }
}

