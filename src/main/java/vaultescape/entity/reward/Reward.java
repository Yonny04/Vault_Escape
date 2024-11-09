package vaultescape.entity.reward;

import vaultescape.entity.Entity;
import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

import java.awt.Graphics2D;
import java.util.Random;
/**
 * Represents a reward item in the game, providing points when collected by the player.
 * The appearance of the reward is determined by its point value, with different frames for different values.
 */
public class Reward extends Entity {
    protected int points = 0; // Points granted by the reward when collected
    private Vector oldRect;

    public Timer animationTimer;

    /**
     * Constructs a Reward entity with specified position, point value, and appearance.
     *
     * @param gp the game panel associated with this reward
     * @param start the starting position
     * @param points the number of points this reward grants upon collection
     */
    public Reward(GamePanel gp, Vector start) {
        super(gp, start);
        setSpritesheet("/entity/reward/spritesheet.png", 8, 1);
        setRewardImage();
        oldRect = rect.add(new Vector());
    }

    /**
     * Sets the image of the reward based on its point value. Different frames in the spritesheet
     * represent different point values, with frames ranging from 0 to 5.
     */
    protected void setRewardImage() {
        Random rand = new Random();
        int frameNum = rand.nextInt(sheetDim.x - 3);
        if (points != 100) setFrame(frameNum, 0);
        else setFrame(sheetDim.x - 3, 0);

    }

    protected void playPickupAnimation() {
        if (animationTimer != null) {
            if (!animationTimer.isTimeUp()) {
                frame += 0.1f;
                setFrame(6 + getNextFrame());
            }
        }
    }

    public void pickup() {
        gp.getPlayer().addScore(points);
        animationTimer = new Timer(0.25);
    }

    double i = 0;
    @Override
    public void update() {
        i += Math.PI / 20.0;
        if (animationTimer == null) rect.y = rect.y + (int)Math.round(Math.sin(i));
        playPickupAnimation();
    }

    /**
     * Draws the shadow below the entity at a calculated offset.
     * This should be called before the main draw call to render beneath the entity.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    @Override
    public void drawShadow(Graphics2D g2) {
        _shadow.setPosition(rect.x + 4, rect.y + 4*12 - (rect.y - oldRect.y)+16);
        _shadow.draw(g2);
    }

    /**
     * Retrieves the point value of the reward.
     *
     * @return the points granted by the reward
     */
    public int getPoints() {
        return points;
    }
}
