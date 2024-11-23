package game.tile.entity;

import game.object.Vector;
import game.panel.GamePanel;
import game.utils.ResourceLoader;

/**
 * Represents the exit door that the player will reach upon
 * collecting all the regular rewards.
 */
public class Exit extends Entity {
    private boolean isOpen = false; 
    
    /**
     * Constructor for Exit Door, and starting position (top-left)
     * @param gp the game panel
     * @param start the starting position
     */
    public Exit(GamePanel gp, Vector start) {
        super(gp,start);
        this.rect.w *= 2;
        this.rect.h *= 2;
        shadow.hide();
        hitbox.setPosition(16,16);
        hitbox.setSize(rect.getSize().scale(0.8));
        ResourceLoader.loadAnimationPlayer(this, "exit");
        getAnimationPlayer().setFrame(0);
    }
    /**
     * Updates the exit door's sprite state. It will open after all regular
     * rewards are collected.
     */
    @Override
    public void update() {
        if (!gp.getRewardGenerator().hasValuablesLeft() && !isOpen) {
            openExit();
        }
        super.update();
    }
    
    /**
     * Opens the exit door, changing its sprite to the open door.
     * Also plays the exit door opening sound effect.
     */
    public void openExit() {
        isOpen = true;
        getAnimationPlayer().setFrame(1);
        hitbox.setSize(rect.getSize().scale(0.4));
        hitbox.setPosition(32,32);
        gp.getSFX().play("exit_open");   
    }
}
