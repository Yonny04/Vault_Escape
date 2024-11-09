package vaultescape.entity.reward;

import vaultescape.ui.GamePanel;
import vaultescape.utils.Vector;

/**
 * Represents a basic reward item in the game, providing a random point value from a set of possible values.
 * basic rewards are simpler than bonus rewards, as they do not track spawn time.
 */
public class Valuable extends Reward {
    /**
     * Constructs a BasicReward with a specified position and a randomly chosen point value.
     *
     * @param gp the game panel associated with this basic reward
     * @param start the starting position
     */
    public Valuable(GamePanel gp, Vector start) {
        super(gp, start);
        this.points = 25;
        setRewardImage();
    }

    @Override
    public void pickup() {
        gp.getSFX().play("basic_collect");
        super.pickup();
    }
}
