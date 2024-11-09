package vaultescape.entity.reward;

import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

/**
 * Represents a bonus reward item in the game, which has a spawn time attribute to track when it appears.
 * Bonus rewards provide additional points to the player when collected.
 */
public class Diamond extends Reward {
    private Timer spawnDuration = new Timer(10);

    /**
     * Constructs a Diamond with a specified position, point value, and spawn time.
     *
     * @param gp the game panel associated with this bonus reward
     * @param start the starting position
     * @param points the number of points the bonus reward grants upon collection
     */
    public Diamond(GamePanel gp, Vector start) {
        super(gp, start);
        this.points = 100;
        setRewardImage();
    }

    public Timer getTimer() {return spawnDuration;}

    @Override
    public void pickup() {
        gp.getSFX().play("bonus_collect");
        super.pickup();
    }
    
}

