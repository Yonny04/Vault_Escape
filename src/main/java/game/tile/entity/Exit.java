package game.tile.entity;

import game.object.Vector;
import game.panel.GamePanel;
import game.utils.ResourceLoader;

import java.awt.Graphics2D;

/**
 * Represents the exit door that the player will reach upon
 * collecting all the regular rewards.
 */
public class Exit extends Entity {
    private boolean _open = false; 
    
    /**
     * Constructor for Exit Door, and starting position (top-left)
     * @param gp the game panel
     * @param start the starting position
     */
    public Exit(GamePanel gp, Vector start) {
        super(gp,start);
        this.rect.w *= 2;
        this.rect.h *= 2;
        hitbox.setSize(rect.getSize());
        shadow.hide();
        ResourceLoader.loadAnimationPlayer(this, "exit");
        getAnimationPlayer().setFrame(0);
    }

    /**
     * Updates the exit door's sprite state. It will open after all regular
     * rewards are collected.
     */
    @Override
    public void draw(Graphics2D g2) {
        if (!gp.getRewardGenerator().hasValuablesLeft() && !_open) {
            getAnimationPlayer().setFrame(1);
            hitbox.setSize(rect.scale(0.5));
            gp.getSFX().play("exit_open");
            _open = true;
        }
        super.draw(g2);
    }
}
