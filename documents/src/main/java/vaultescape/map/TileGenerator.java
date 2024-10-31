package vaultescape.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
            spritesheet = ImageIO.read(getClass().getResourceAsStream(String.format("/map/spritesheet.png")));
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Loads and generates tiles from the given resource stream .txt file.
     */
    public void loadMap() {
        try {
            InputStream stream = getClass().getResourceAsStream("/map/testlevel.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    
            for (int row = 0; row < gp.numMapCols; row++) {
                String line = reader.readLine();
                String numberStrings[] = line.split("\t");
    
                for (int col = 0; col < gp.numMapRows; col++) {
                    int tileNumber = Integer.parseInt(numberStrings[col]);
    
                    if (tileNumber > 0) {
                        createTile(col, row, tileNumber);
                    } else {
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
        BufferedImage frame = spritesheet.getSubimage(tileNumber*16, 0, 16, 16);
        return frame;
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
        if ((tileNumber > 0 && tileNumber % 2 == 1) 
                || tileNumber == 12 || tileNumber == 14 || tileNumber == 16) {
            tile.setHitbox(52, 40);
            walls.add(tile);
            topTiles.add(tile);
        }
        else {
            tile.setHitbox(52, 40);
            walls.add(tile);
            bottomTiles.add(tile);
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
