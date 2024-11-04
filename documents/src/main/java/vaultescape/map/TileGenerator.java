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
    public ArrayList<Sprite2D> floorTiles = new ArrayList<>();
    public ArrayList<Sprite2D> bottomTiles = new ArrayList<>();  // filter bottom tiles (walls)
    public ArrayList<Sprite2D> topTiles = new ArrayList<>();  // filter top tiles (walls)
    public ArrayList<Sprite2D> walls = new ArrayList<>();  // filter wall collision tiles
    public List<int[]> availableTiles = new ArrayList<>();

    protected BufferedImage floorSpritesheet;
    protected BufferedImage wallSpritesheet;
    private final static int[] _bottomWallNums = {2,4,6,8,9,10,12,14,16,26,34,36,38,40,74,76,78,80};

    // Constructor
    public TileGenerator(GamePanel gp) {
        this.gp = gp;
        setTileSpritesheet();
        loadMap();
    }

    public void setTileSpritesheet() {
        try {
            wallSpritesheet = ImageIO.read(getClass().getResourceAsStream("/map/wall_spritesheet.png"));
            floorSpritesheet = ImageIO.read(getClass().getResourceAsStream("/map/floor_spritesheet.png"));
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Loads and generates tiles from the given resource stream .txt file.
     */
    public void loadMap() {
        try {
            InputStream stream = getClass().getResourceAsStream("/map/floor1.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    
            for (int row = 0; row < gp.numMapCols; row++) {
                String line = reader.readLine();
                String numberStrings[] = line.split(";");
    
                for (int col = 0; col < gp.numMapRows; col++) {
                    int tileNumber = Integer.parseInt(numberStrings[col]);
    
                    if (tileNumber > 0) {
                        createFloorTile(col, row, tileNumber);
                }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            InputStream stream = getClass().getResourceAsStream("/map/wall1.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    
            for (int row = 0; row < gp.numMapCols; row++) {
                String line = reader.readLine();
                String numberStrings[] = line.split(";");
    
                for (int col = 0; col < gp.numMapRows; col++) {
                    int tileNumber = Integer.parseInt(numberStrings[col]);
    
                    if (tileNumber > 0) {
                        createWallTile(col, row, tileNumber);
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

    private BufferedImage getFloorTileImage(int tileNumber) {
        BufferedImage frame = floorSpritesheet.getSubimage((int)tileNumber%6*16, (int)Math.floor(tileNumber/6.0)*16, 16, 16);
        return frame;
    }

    private BufferedImage getWallTileImage(int tileNumber) {
        BufferedImage frame = wallSpritesheet.getSubimage((int)tileNumber%10*16, (int)Math.floor(tileNumber/10.0)*16, 16, 16);
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

    private void createFloorTile(int tileX, int tileY, int tileNumber) {
        Sprite2D tile = Sprite2D.createSprite2D(gp, tileX*gp.tilesize,tileY*gp.tilesize,gp.tilesize,gp.tilesize);
        tile.setImage(getFloorTileImage(tileNumber));
        floorTiles.add(tile);
    }
    /**
     * Creates and adds new tile to tiles ArrayList. Also adds
     * the tile to walls ArrayList if it is a wall tile.
     * @param tileX multiply by gp.tilesize to get the global x
     * @param tileY multiply by gp.tilesize to get the global y
     * @param tileNumber 0 is floor, any other number is a wall (for now)
     */
    private void createWallTile(int tileX, int tileY, int tileNumber) {
        Sprite2D tile = Sprite2D.createSprite2D(gp, tileX*gp.tilesize,tileY*gp.tilesize,gp.tilesize,gp.tilesize);
        tile.setImage(getWallTileImage(tileNumber));

        // Doors and Upper Pillar (no collision)
        if (tileNumber >= 17 && tileNumber <= 25)
        {
            // Lower Door
            if (tileNumber == 18 || tileNumber == 20) bottomTiles.add(tile);
            else topTiles.add(tile); // Side doors and top part of front-facing door
            return;
        }
        
        boolean isBottomTile = false;
        for (int num : _bottomWallNums) {
            if (tileNumber == num) {
                isBottomTile = true;
                break;
            }
        }
        // Bottom Tile
        if (isBottomTile) {
                bottomTiles.add(tile);
                walls.add(tile);
                tile.setHitbox(32, 36);
                return; }
        // Top Tile
        walls.add(tile);
        tile.setHitbox(40, 36);
        if (tileNumber != 48) topTiles.add(tile);
        else bottomTiles.add(tile);
    }
    // Draw floors
    public void drawFloor(Graphics2D g2) {
        for (Sprite tile : floorTiles) {
            tile.draw(g2);  
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
