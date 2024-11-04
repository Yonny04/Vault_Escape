package vaultescape.reward;

import vaultescape.entity.Entity;
import vaultescape.map.GamePanel;

/**
 * Represents a reward item in the game, providing points when collected by the player.
 * The appearance of the reward is determined by its point value, with different frames for different values.
 */
class Reward extends Entity {
    protected int points; // Points granted by the reward when collected

    /**
     * Constructs a Reward entity with specified position, point value, and appearance.
     *
     * @param gp the game panel associated with this reward
     * @param x the x-coordinate of the reward's position
     * @param y the y-coordinate of the reward's position
     * @param points the number of points this reward grants upon collection
     */
    public Reward(GamePanel gp, int x, int y, int points) {
        super(gp);
        this.x = x;
        this.y = y;
        this.points = points;
        this.width = 64;
        this.height = 64;
        setHitbox(48, 48);
        setSpritesheet("/reward/spritesheet.png", 6, 1);
        setRewardImage();
    }

    /**
     * Sets the image of the reward based on its point value. Different frames in the spritesheet
     * represent different point values, with frames ranging from 0 to 5.
     */
    private void setRewardImage() {
        int frameNum = 0;
        switch (points) {
            case 10:
                frameNum = 0;
                break;
            case 20:
                frameNum = 1;
                break;
            case 30:
                frameNum = 2;
                break;
            case 40:
                frameNum = 3;
                break;
            case 50:
                frameNum = 4;
                break;
            case 100:
                frameNum = 5;
                break;
            default:
                break;
        }
        setFrame(frameNum, 0);
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
