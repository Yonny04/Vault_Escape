package vaultescape.reward;

import vaultescape.entity.Entity;
import vaultescape.map.GamePanel;

class Reward extends Entity {
    protected int points;
    
    public Reward(GamePanel gp, int x, int y, int points) {
        super(gp);
        this.x = x;
        this.y = y;
        this.points = points;
        this.width = 64;
        this.height = 64;
        setHitbox(48, 48);
        setSpritesheet("/reward/spritesheet.png",6,1);
        setRewardImage();
    }

    /**
     * Sets the reward image based on the number of points the reward is worth.
     * Frames range from 0 to 5.
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
        setFrame(frameNum,0);
    }

    // Getter for points
    public int getPoints() {
        return points;
    }
}
