package vaultescape.reward;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import vaultescape.ui.Sprite;

class Reward extends Sprite {
    protected int points;
    private BufferedImage spritesheet;
    
    public Reward(int x, int y, int points) {
        this.x = x;
        this.y = y;
        this.points = points;
        this.width = 64;
        this.height = 64;
        setRewardSpritesheet();
        setRewardImage();
    }
    private void setRewardSpritesheet(){
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream(String.format("/reward/spritesheet.png")));
        } catch (Exception e) {e.printStackTrace();}
    }
    // Getter for points
    public int getPoints() {
        return points;
    }

    private void setRewardImage() {
        setImage(spritesheet.getSubimage(((points/4))*16, 0, 16, 16));
    }
}
