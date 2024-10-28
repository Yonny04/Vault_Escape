package vaultescape.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import vaultescape.ui.Sprite;

public class TileGenerator {
    private GamePanel gp;
    public ArrayList<Sprite> tiles = new ArrayList<>();  // store all tiles here
    public ArrayList<Wall> walls = new ArrayList<>();  // store only walls here
    protected BufferedImage spritesheet;

    // Constructor
    public TileGenerator(GamePanel gp) {
        this.gp = gp;
        setTileSpritesheet();
        loadMap();
        //generateTiles(); 
    }

    public void setTileSpritesheet() {
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream(String.format("/map/spritesheet.png")));
        } catch (Exception e) {e.printStackTrace();}
    }
    // Generate tiles with images and positions
    //public void generateTiles() {
            // //Generate map borders
            //for (int i = 0; i <= gp.screenWidth; i += gp.tilesize) {
                //addWall(new Wall(i, 0, gp.tilesize, gp.tilesize), spritesheet); 
                //addWall(new Wall(i, gp.screenHeight - gp.tilesize, gp.tilesize, gp.tilesize), spritesheet); 
            //}
            //for (int i = 0; i <= gp.screenHeight; i += gp.tilesize) {
                //addWall(new Wall(0, i, gp.tilesize, gp.tilesize), spritesheet);  
                //addWall(new Wall(gp.screenWidth - gp.tilesize, i, gp.tilesize, gp.tilesize), spritesheet); 
            //}
            // //Generate the walls 
            //for (int x = gp.tilesize * 5; x < gp.screenWidth - gp.tilesize * 5; x += gp.tilesize * 4) {
                //for (int y = gp.tilesize * 5; y < gp.screenHeight - gp.tilesize * 5; y += gp.tilesize * 4) {
                    //addWall(new Wall(x, y, gp.tilesize, gp.tilesize), spritesheet);  
                //}
            //}
    //}

    /**
     * Loads and generates tiles from the given resource stream .txt file.
     */
    public void loadMap() {
        try {
            InputStream stream = getClass().getResourceAsStream("/map/testlevel.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            for (int row = 0; row < gp.numRows; row++) {
                String line = reader.readLine();
                String numberStrings[] = line.split(" ");
                for (int col = 0; col < gp.numCols; col++) {
                    int tileNumber = Integer.parseInt(numberStrings[col]);
                    if (tileNumber > 0) createTile(col,row,tileNumber);
                }
            }
            reader.close();
        } catch(Exception e) {e.printStackTrace();}
    }

    /**
     * Creates and adds new tile to tiles ArrayList. Also adds
     * the tile to walls ArrayList if it is a wall tile.
     * @param tileX multiply by gp.tilesize to get the global x
     * @param tileY multiply by gp.tilesize to get the global y
     * @param tileNumber 0 is floor, any other number is a wall (for now)
     */
    private void createTile(int tileX, int tileY, int tileNumber) {
        if ((tileNumber > 0 && tileNumber % 2 == 1) || tileNumber == 14 || tileNumber == 16) {
            createWall(tileX, tileY, tileNumber);
        }
        Sprite tile = Sprite.createSprite(tileX*gp.tilesize,tileY*gp.tilesize,gp.tilesize,gp.tilesize);
        tile.setImage(getTileImage(tileNumber));
        tiles.add(tile);
    }
    
    /**
     * Creates a wall to the walls ArrayList for collision detections
     * @param tileX
     * @param tileY
     * @param tileNumber
     */
    private void createWall(int tileX, int tileY, int tileNumber) {
        Wall wall = new Wall(tileX*gp.tilesize,tileY*gp.tilesize,gp.tilesize,gp.tilesize);
        wall.setImage(getTileImage(tileNumber));
        walls.add(wall);
    }

    private BufferedImage getTileImage(int tileNumber) {
        BufferedImage frame = spritesheet.getSubimage(tileNumber*16, 0, 16, 16);
        return frame;
    }
    // Draw walls
    public void draw(Graphics2D g2) {
        for (Sprite tile : tiles) {
            tile.draw(g2);  
        }
    }
}
