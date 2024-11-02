package vaultescape.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import vaultescape.ui.Sprite;
import vaultescape.ui.Sprite2D;

public class TileGenerator {
    private GamePanel gp;
    //public ArrayList<Sprite> tiles = new ArrayList<>();  // store all tiles here
    public ArrayList<Sprite2D> bottomTiles = new ArrayList<>();  // filter bottom tiles
    public ArrayList<Sprite2D> topTiles = new ArrayList<>();  // filter top tiles
    public ArrayList<Sprite2D> walls = new ArrayList<>();  // filter wall collision tiles

    public List<int[]> availableTiles = new ArrayList<>();

    protected BufferedImage spritesheet;

    // Constructor
    public TileGenerator(GamePanel gp) {
        this.gp = gp;
        setTileSpritesheet();
        loadMap();
    }

    public void setTileSpritesheet() {
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream("/map/wall_spritesheet.png"));
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Loads and generates tiles from the given resource stream .txt file.
     */
    public void loadMap() {
        try {
            InputStream stream = getClass().getResourceAsStream("/map/level1.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    
            for (int row = 0; row < gp.numMapCols; row++) {
                String line = reader.readLine();
                String numberStrings[] = line.split(";");
    
                for (int col = 0; col < gp.numMapRows; col++) {
                    int tileNumber = Integer.parseInt(numberStrings[col]);
    
                    if (tileNumber > 0) {
                        createTile(col, row, tileNumber);
                    } else if (tileNumber == 0) {
                        availableTiles.add(new int[]{col * gp.tilesize, row * gp.tilesize});
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage getTileImage(int tileNumber) {
        BufferedImage frame = spritesheet.getSubimage((int)tileNumber%9*16, (int)Math.floor(tileNumber/9.0)*16, 16, 16);
        return frame;
    }
    
    /**
     * Gets a random available tile in the map. Does not remove this tile,
     * so do not use this if you want to spawn entities.
     * @return
     */
    public int[] getRandomAvailableTile() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(0,availableTiles.size());
        return availableTiles.get(randomIndex);
    }
    /**
     * Creates and adds new tile to tiles ArrayList. Also adds
     * the tile to walls ArrayList if it is a wall tile.
     * @param tileX multiply by gp.tilesize to get the global x
     * @param tileY multiply by gp.tilesize to get the global y
     * @param tileNumber 0 is floor, any other number is a wall (for now)
     */
    private void createTile(int tileX, int tileY, int tileNumber) {
        Sprite2D tile = Sprite2D.createSprite2D(gp, tileX*gp.tilesize,tileY*gp.tilesize,gp.tilesize,gp.tilesize);
        tile.setImage(getTileImage(tileNumber));

        // Non-Doors and Upper Walls
        if (!(tileNumber == 24 || tileNumber == 25 || 
            (tileNumber >= 43 && tileNumber <= 46) || tileNumber == 47 || tileNumber == 34 || tileNumber == 35))
        {
            if (tileNumber == 4 || tileNumber == 10 || tileNumber == 11 || 
                    tileNumber == 14 || tileNumber == 15 || (tileNumber >= 28 && tileNumber <= 33) ||
                    tileNumber == 36){
                bottomTiles.add(tile);
                walls.add(tile);
                tile.setHitbox(32, 32);
            }
            else {
                walls.add(tile);
                tile.setHitbox(40, 36);
                if (tileNumber != 48) topTiles.add(tile);
                else bottomTiles.add(tile);
                
            }
            
        }
        else {
            if (tileNumber == 34 || tileNumber == 35) bottomTiles.add(tile);
            else topTiles.add(tile);
        }
    }

    // Draw walls
    public void drawBottom(Graphics2D g2) {
        for (Sprite tile : bottomTiles) {
            tile.draw(g2);  
        }
    }
    public void drawTop(Graphics2D g2) {
        for (Sprite tile : topTiles) {
            tile.draw(g2);  
        }
    }
}
