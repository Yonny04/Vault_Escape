package game.tile.entity.character.enemy;

import game.object.Vector;
import game.panel.GamePanel;
import game.tile.Tile;
import game.tile.entity.Entity;
import game.utils.ColorPalette;
import game.utils.Timer;

import java.awt.Graphics2D;
import java.util.*;

public class Laser extends Enemy {
    private boolean isHorizontal = new Random().nextBoolean();
    public List<Entity> laserTiles = new ArrayList<>();
    private boolean laserOn = false;

    private Timer laserTimer = new Timer(2);

    /**
     * Constructs a Camera object with specified game panel, position, and detection range.
     *
     * @param gp the game panel associated with this entity
     * @param start the camera's starting position
     */
    public Laser(GamePanel gp, Vector start) {
        super(gp, start);
        shadow.hide();
        this.timeReduction = 5;
        animationPlayer.setSpritesheet("laser",3,4);
        animationPlayer.setFrame(0,3);
        attackLabel.setColor(ColorPalette.MAGENTA);
        setSpeed(3);
        setLayer(Layer.TOP);
        createLaser();
    }

    private void createLaser() {
        Vector offset = new Vector(0, 64);
        Vector position = getPosition().add(offset);
        while (!gp.getTileGenerator().wallTiles.containsKey(position.getUnitString())) {
                Entity laserTile = new Entity(gp, position);
                position = position.add(offset);
                laserTile.getAnimationPlayer().setSpritesheet("laser", 3, 4);
                laserTile.getAnimationPlayer().setFrame(2, 3);
                laserTiles.add(laserTile);
        }
        Entity laserTile = new Entity(gp, position);
        position = position.add(offset);
        laserTile.getAnimationPlayer().setSpritesheet("laser", 3, 4);
        laserTile.getAnimationPlayer().setFrame(2, 3);
        laserTiles.add(laserTile);
    }

    @Override
    public void update() {
        if (laserTimer.isTimeUp()) {
            laserTimer.start();
            laserOn = !laserOn;
            animationPlayer.setFrame(laserOn ? 1:0,3);
        }
        super.update();
    }

    @Override
    public boolean canAttack() {
        if (!laserOn) return false;
        if (!attackCooldown.isTimeUp()) return false;
        for (Entity tile : laserTiles) {
            if (tile.isTouchingPlayer()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void attack() {
        gp.getSFX().play("laser");
        gp.getSFX().loop(1);
        laserTimer.start();
        gp.getTimer().decreaseTime(timeReduction);
        super.attack();
        
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (gp.introFade >0) return;
        if ((double)(attackCooldown.getTimeLeft() / 1000.0) > 0.5 && !canAttack()) {
            attackLabel.setText(String.format("-%ds",timeReduction));
            attackLabel.draw(g2,gp.getPlayer().getScreenPosition());
        }
        
    }

    public void drawLaser(Graphics2D g2) {
        if (!laserOn) return;
        for (Tile tile : laserTiles) {
            tile.draw(g2);
        }
    }
}
