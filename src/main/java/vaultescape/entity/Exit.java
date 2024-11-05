package vaultescape.entity;

import java.awt.Graphics2D;

import vaultescape.map.GamePanel;

/**
 * Represents the exit door that the player will reach upon
 * collecting all the regular rewards.
 */
public class Exit extends Entity {
    private boolean _open = false; 
    /**
     * Constructor for Exit Door, and starting position (top-left)
     * @param gp
     * @param x
     * @param y
     */
    public Exit(GamePanel gp, int x, int y) {
        super(gp);
        this.x = x;
        this.y = y;
        this.width = 128;
        this.height = 128;
        this.spritesheetTileSize = 32;
        setSpritesheet("/entity/exit/spritesheet.png", 2, 1);
        setHitbox(128,100);
        setFrame(0);
    }

    /**
     * Overrides the drawShadow to remove it from the Exit Door.
     * @param g2 the Graphics2D object used for rendering
     */
    @Override
    public void drawShadow(Graphics2D g2) {}

    /**
     * Updates the exit door's sprite state. It will open after all regular
     * rewards are collected.
     */
    @Override
    public void draw(Graphics2D g2) {
        if (gp.getRewardGenerator().getRegularRewardsSize() == 0 && !_open) {
            setFrame(1);
            setHitbox(64, 64);
            gp.getSFX().play(2);
            _open = true;
        }
        super.draw(g2);
    }
}
