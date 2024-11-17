package game.tile.entity.reward;

import game.object.Vector;
import game.panel.GamePanel;
import game.tile.entity.Entity;
import game.ui.Label;
import game.utils.*;

import java.awt.Graphics2D;
import java.util.Random;

/**
 * Represents a reward item in the game, providing points when collected by the player.
 * The appearance of the reward is determined by its point value, with different frames for different values.
 */
public class Reward extends Entity {
    protected int points = 0; // Points granted by the reward when collected
    protected Label scoreLabel = new Label(ColorPalette.YELLOW,true);
    private Vector oldRect;

    /**
     * Constructs a Reward entity with specified position, point value, and appearance.
     *
     * @param gp the game panel associated with this reward
     * @param start the starting position
     * @param points the number of points this reward grants upon collection
     */
    public Reward(GamePanel gp, Vector start, int points) {
        super(gp, start);
        ResourceLoader.loadAnimationPlayer(this, "reward");
        this.points = points;
        setRewardImage();
        oldRect = rect.add(new Vector());
        scoreLabel.setFont(gp.font);
    }

    /**
     * Sets the image of the reward based on its point value. Different frames in the spritesheet
     * represent different point values, with frames ranging from 0 to 5.
     */
    protected void setRewardImage() {
        Random rand = new Random();
        int valuable = rand.nextInt(5);
        if (this instanceof Diamond) getAnimationPlayer().setFrame(5); // Diamond
        else getAnimationPlayer().setFrame(valuable);

    }

    public void pickup() {
        gp.getPlayer().addScore(points);
        getAnimationPlayer().playAnimation("pickup");
    }

    double i = 0;
    @Override
    public void update() {
        i += Math.PI / 20.0;
        if (!getAnimationPlayer().isPlaying()) rect.y = rect.y + (int)Math.round(Math.sin(i));
        super.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getAnimationPlayer().isPlaying()) { //Pickup animation
            scoreLabel.setText(String.format("+%3d",points));
            scoreLabel.draw(g2,screen);
        }
        super.draw(g2);
        
    }
    /**
     * Draws the shadow below the entity at a calculated offset.
     * Keeps the shadow fixed as the reward moves up and down.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    @Override
    public void drawShadow(Graphics2D g2) {
        shadow.setPosition(rect.x + 4, rect.y + 4*12 - (rect.y - oldRect.y)+16);
        shadow.draw(g2);
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
