package vaultescape.map;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TileGenerator {
    private GamePanel gp;
    public ArrayList<Wall> walls = new ArrayList<>();  // store walls here

    // Constructor
    public TileGenerator(GamePanel gp) {
        this.gp = gp;
        generateWalls();  
    }

    // Generate walls with images and positions
    public void generateWalls() {
        try {
            BufferedImage wallImage = ImageIO.read(getClass().getResourceAsStream("/wall/block1.png"));
            // Generate map borders
            for (int i = 0; i <= gp.screenWidth; i += gp.tilesize) {
                addWall(new Wall(i, 0, gp.tilesize, gp.tilesize), wallImage); 
                addWall(new Wall(i, gp.screenHeight - gp.tilesize, gp.tilesize, gp.tilesize), wallImage); 
            }
            for (int i = 0; i <= gp.screenHeight; i += gp.tilesize) {
                addWall(new Wall(0, i, gp.tilesize, gp.tilesize), wallImage);  
                addWall(new Wall(gp.screenWidth - gp.tilesize, i, gp.tilesize, gp.tilesize), wallImage); 
            }
            // Generate the walls 
            for (int x = gp.tilesize * 5; x < gp.screenWidth - gp.tilesize * 5; x += gp.tilesize * 4) {
                for (int y = gp.tilesize * 5; y < gp.screenHeight - gp.tilesize * 5; y += gp.tilesize * 4) {
                    addWall(new Wall(x, y, gp.tilesize, gp.tilesize), wallImage);  
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // helper for generateWalls()
    private void addWall(Wall wall, BufferedImage image) {
        wall.setImage(image);
        walls.add(wall); 
    }

    // Draw walls
    public void draw(Graphics2D g2) {
        for (Wall wall : walls) {
            wall.draw(g2);  
        }
    }
}
