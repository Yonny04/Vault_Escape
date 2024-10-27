package vaultescape.map;

import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileGenerator  {
    GamePanel gp;
    Wall[] wall;

    // Sets the required array size
    public TileGenerator(GamePanel gp){
        this.gp = gp;
        wall = new Wall[10];
        getImage();
    }

    // Getting the image from the resource package "wall"
    public void getImage(){
        try{
            wall[0] = new Wall();
            wall[0].image = ImageIO.read(getClass().getResourceAsStream("/wall/block1.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    // Draws tiles in gamepanel
    public void draw(Graphics2D g2){
        for(int i=0; i <= 200; i= i + gp.tilesize){
            g2.drawImage(wall[0].image, i, 0, gp.tilesize, gp.tilesize,null);
        }
        for(int i=0; i <= 200; i= i+ gp.tilesize){
            g2.drawImage(wall[0].image, 0, i, gp.tilesize, gp.tilesize,null);
        }
    } 
}
