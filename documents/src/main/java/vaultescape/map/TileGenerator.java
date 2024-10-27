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
        //Right and Left borders
        for(int i=0; i <= gp.screenWidth; i= i + gp.tilesize){
            g2.drawImage(wall[0].image, i, 0, gp.tilesize, gp.tilesize,null);
            g2.drawImage(wall[0].image, i, gp.screenHeight, gp.tilesize, gp.tilesize,null);
        }
        
        //Top and bottom borders
        for(int i=0; i <= gp.screenHeight; i= i+ gp.tilesize){
            g2.drawImage(wall[0].image, 0, i, gp.tilesize, gp.tilesize,null);
            g2.drawImage(wall[0].image, gp.screenWidth, i, gp.tilesize, gp.tilesize,null);   
        }

        // "Walls"
        for(int i=198; i <= gp.screenWidth; i= i + gp.tilesize * 5){
            g2.drawImage(wall[0].image, i, 144, gp.tilesize, gp.tilesize,null);
            g2.drawImage(wall[0].image, i-100, 250, gp.tilesize, gp.tilesize,null);
            g2.drawImage(wall[0].image, i-232, 400, gp.tilesize, gp.tilesize,null);
            g2.drawImage(wall[0].image, i-433, 550, gp.tilesize, gp.tilesize,null);
        }

    } 
}
