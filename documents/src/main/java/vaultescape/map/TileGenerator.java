package vaultescape.map;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileGenerator {
    GamePanel gp;
    Wall[] border;

    public TileGenerator(GamePanel gp){
        this.gp = gp;
        border = new Wall[5];
        getImage();
    }

    public void getImage(){
        try{
            border[0] = new Wall();
            border[0].image = ImageIO.read(getClass().getResourceAsStream("/wall/block1.png"));
            if (border[0].image != null) {
                System.out.println("Image loaded successfully");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(border[0].image, 0, 0, gp.tilesize, gp.tilesize,null);
    } 

}
