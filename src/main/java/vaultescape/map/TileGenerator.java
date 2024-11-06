package vaultescape.map;

import vaultescape.entity.Exit;
import vaultescape.ui.GamePanel;
import vaultescape.utils.*;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Responsible for generating and managing the tiles in the game, including floors, walls, and available tiles for spawning.
 * Loads map data from resources and separates tiles based on their types and properties.
 */
public class TileGenerator {
    private GamePanel gp;
    public ArrayList<Sprite2D> floorTiles = new ArrayList<>();
    public ArrayList<Sprite2D> bottomTiles = new ArrayList<>(); // Bottom wall tiles
    public ArrayList<Sprite2D> topTiles = new ArrayList<>(); // Top wall tiles
    public ArrayList<Sprite2D> walls = new ArrayList<>(); // Collision wall tiles
    public List<Vector2> availableTiles = new ArrayList<>(); // Available tiles for entity spawning
    public Exit exit; // Exit door

    protected BufferedImage floorSpritesheet;
    protected BufferedImage wallSpritesheet;
    private final static int[] _bottomWallNums = {
        2, 4, 6, 8, 9, 10, 12, 14, 16, 26, 34, 36, 38, 40, 74, 76, 78, 80};

    /**
     * Constructs the TileGenerator, loading the spritesheets and map data from resources.
     *
     * @param gp the game panel associated with this tile generator
     */
    public TileGenerator(GamePanel gp) {
        this.gp = gp;
        setTileSpritesheet();
        loadMap();
    }

    /**
     * Loads the spritesheets for floor and wall tiles from resources.
     */
    public void setTileSpritesheet() {
        try {
            wallSpritesheet = ImageIO.read(getClass().getResourceAsStream("/map/wall_spritesheet.png"));
            floorSpritesheet = ImageIO.read(getClass().getResourceAsStream("/map/floor_spritesheet.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map data from text files and generates floor and wall tiles based on the map layout.
     */
    public void loadMap() {
        try {
            InputStream stream = getClass().getResourceAsStream("/map/floor1.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            for (int row = 0; row < gp.MAP_TILE.y; row++) {
                String line = reader.readLine();
                String[] numberStrings = line.split(";");
    
                for (int col = 0; col < gp.MAP_TILE.x; col++) {
                    int tileNumber = Integer.parseInt(numberStrings[col]);
                    if (tileNumber > 0) {
                        Vector2 position = new Vector2(col, row).toGlobal();
                        Rect2 rect = new Rect2(position);
                        createFloorTile(rect, tileNumber);
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
    
            for (int row = 0; row < gp.MAP_TILE.y; row++) {
                String line = reader.readLine();
                String[] numberStrings = line.split(";");
    
                for (int col = 0; col < gp.MAP_TILE.x; col++) {
                    int tileNumber = Integer.parseInt(numberStrings[col]);
                    Vector2 pos = new Vector2(col,row).toGlobal();
                    if (tileNumber > 0) {
                        Rect2 rect = new Rect2(pos);
                        createWallTile(rect, tileNumber); // Create wall
                    } else if (tileNumber == 0) {
                        availableTiles.add(pos); // Available Vector2 Position
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a subimage for a floor tile based on the tile number.
     *
     * @param tileNumber the index of the tile in the spritesheet
     * @return the BufferedImage of the specified tile
     */
    private BufferedImage getFloorTileImage(int tileNumber) {
        return floorSpritesheet.getSubimage(tileNumber % 6 * 16, 
            (int)Math.floor(tileNumber / 6.0) * 16, 16, 16);
    }

    /**
     * Retrieves a subimage for a wall tile based on the tile number.
     *
     * @param tileNumber the index of the tile in the spritesheet
     * @return the BufferedImage of the specified wall tile
     */
    private BufferedImage getWallTileImage(int tileNumber) {
        return wallSpritesheet.getSubimage(tileNumber % 9 * 16, 
            (int)Math.floor(tileNumber / 9.0) * 16, 16, 16);
    }

    /**
     * Gets a random available tile in the map for entity spawning. 
     * This method does not remove the selected tile from available tiles.
     *
     * @return an Vector2 array representing the x and y coordinates of the available tile
     */
    public Vector2 getRandomAvailableTile() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(availableTiles.size());
        return availableTiles.get(randomIndex);
    }

    /**
     * Creates a floor tile and adds it to the floor tiles list.
     *
     * @param tileX the x-coordinate of the tile in tile units
     * @param tileY the y-coordinate of the tile in tile units
     * @param tileNumber the index of the tile in the spritesheet
     */
    private void createFloorTile(Rect2 rect, int tileNumber) {
        Sprite2D tile = new Sprite2D(gp);
        tile.setRect(rect);
        tile.setImage(getFloorTileImage(tileNumber));
        floorTiles.add(tile);
    }

    /**
     * Creates a wall tile and categorizes it into bottom, top, or collision tiles based on tile properties.
     *
     * @param tileNumber the index of the tile in the spritesheet
     */
    private void createWallTile(Rect2 rect, int tileNumber) {
        Sprite2D tile = new Sprite2D(gp);
        tile.setRect(rect);
        tile.setImage(getWallTileImage(tileNumber));

        // Vault Door
        if (tileNumber == 75) {
            if (exit == null){
                exit = new Exit(gp,rect.getPosition());
                bottomTiles.add(exit);
                walls.add(exit);
            }
            return;
        }
        // Doors and Upper Pillar (no collision)
        if (tileNumber >= 17 && tileNumber <= 25) {
            if (tileNumber == 18 || tileNumber == 20) bottomTiles.add(tile);
            else topTiles.add(tile);
            return;
        }

        boolean isBottomTile = false;
        for (int num : _bottomWallNums) {
            if (tileNumber == num) {
                isBottomTile = true;
                break;
            }
        }
        tile.setHitbox(tile.getDimension().scale(0.8));
        if (isBottomTile) {
            bottomTiles.add(tile);
            walls.add(tile);
        } else {
            walls.add(tile);
            if (tileNumber != 48) topTiles.add(tile);
            else bottomTiles.add(tile);
        }
    }

    /**
     * Draws all floor tiles on the screen.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    public void drawFloor(Graphics2D g2) {
        for (Sprite tile : floorTiles) {
            tile.draw(g2);
        }
    }

    /**
     * Draws all bottom wall tiles on the screen.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    public void drawBottom(Graphics2D g2) {
        for (Sprite tile : bottomTiles) {
            tile.draw(g2);
        }
    }

    /**
     * Draws all top wall tiles on the screen.
     *
     * @param g2 the Graphics2D object used for rendering
     */
    public void drawTop(Graphics2D g2) {
        for (Sprite tile : topTiles) {
            tile.draw(g2);
        }
    }
}
