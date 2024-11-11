package game.map;

import game.object.Rect;
import game.object.Vector;
import game.panel.GamePanel;
import game.tile.Tile;
import game.tile.Tile.Layer;
import game.tile.entity.Exit;
import game.tile.entity.character.Player;
import game.tile.entity.character.enemy.Enemy;
import game.tile.entity.reward.Reward;

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

    GamePanel gp;
    TileGenerator tg;
    Random random;
    public Exit exit; // Exit door

    public BufferedImage floorSheet;
    public BufferedImage wallSheet;

    public List<Vector> emptyTiles = new ArrayList<>();
    public List<Tile> wallTiles = new ArrayList<>();
    public List<Tile> floorTiles = new ArrayList<>();

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
            wallSheet = ImageIO.read(getClass().getResourceAsStream("/tile/wall_spritesheet.png"));
            floorSheet = ImageIO.read(getClass().getResourceAsStream("/tile/floor_spritesheet.png"));
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
                        Vector position = new Vector(col, row).toGlobal();
                        Rect rect = new Rect(position);
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
                    Vector pos = new Vector(col,row).toGlobal();
                    if (tileNumber > 0) {
                        Rect rect = new Rect(pos);
                        createWallTile(rect, tileNumber); // Create wall
                    } else if (tileNumber == 0) {
                        emptyTiles.add(pos); // Available Vector2 Position
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a random available tile in the map for entity spawning. 
     * This method does not remove the selected tile from available tiles.
     *
     * @return a Vector representing the x and y coordinates of the available tile
     */
    public Vector getEmptyTile() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(emptyTiles.size());
        return emptyTiles.get(randomIndex);
    }

    /**
     * Uses up a random available tile in the map for entity spawning.
     *
     * @return a Vector representing the x and y coordinates of the available tile
     */
    public Vector nextEmptyTile() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(emptyTiles.size());
        return emptyTiles.remove(randomIndex);
    }

    /**
     * Retrieves a subimage for a floor tile based on the tile number.
     *
     * @param tileNumber the index of the tile in the spritesheet
     * @return the BufferedImage of the specified tile
     */
    private BufferedImage getFloorTileImage(int tileNumber) {
        return floorSheet.getSubimage(tileNumber % 6 * 16, 
            (int)Math.floor(tileNumber / 6.0) * 16, 16, 16);
    }

    /**
     * Retrieves a subimage for a wall tile based on the tile number.
     *
     * @param tileNumber the index of the tile in the spritesheet
     * @return the BufferedImage of the specified wall tile
     */
    private BufferedImage getWallTileImage(int tileNumber) {
        return wallSheet.getSubimage(tileNumber % 9 * 16, 
            (int)Math.floor(tileNumber / 9.0) * 16, 16, 16);
    }
    
    /**
     * Creates a floor tile and adds it to the floor tiles list.
     *
     * @param rect the rectangle representing the tile's position and size
     * @param tileNumber the index of the tile in the spritesheet
     */
    private void createFloorTile(Rect rect, int tileNumber) {
        Tile tile = new Tile(gp);
        tile.setRect(rect);
        tile.setLayer(Tile.Layer.BOTTOM);
        tile.setImage(getFloorTileImage(tileNumber));
        floorTiles.add(tile);
    }

    /**
     * Creates a wall tile and categorizes it into bottom, top, or collision tiles based on tile properties.
     *
     * @param rect the rectangle representing the tile's position and size
     * @param tileNumber the index of the tile in the spritesheet
     */
    private void createWallTile(Rect rect, int tileNumber) {
        Tile tile = new Tile(gp);
        tile.setRect(rect);
        tile.setImage(getWallTileImage(tileNumber));
        tile.setLayer(Layer.BOTTOM);

        tile.hitbox.setSize(tile.getSize().scale(0.8));
        // Vault Door
        if (tileNumber == 75) {
            if (exit == null){
                exit = new Exit(gp,rect.getPosition());
                wallTiles.add(exit);
                exit.setLayer(Layer.ORDERED);
            }
            return;
        }
        // Doors and Upper Pillar (no collision)
        if (tileNumber >= 17 && tileNumber <= 25) {
            tile.setCollisionMask(false);
            wallTiles.add(tile);
            if (tileNumber == 18 || tileNumber == 20) tile.setLayer(Layer.ORDERED);
            else tile.setLayer(Layer.TOP);
            return;
        }

        boolean isBottomTile = false;
        for (int num : _bottomWallNums) {
            if (tileNumber == num) {
                isBottomTile = true;
                break;
            }
        }
        if (isBottomTile) {
            wallTiles.add(tile);
            if (tileNumber != 26) tile.setLayer(Layer.BOTTOM);
            else {
                tile.hitbox.setRect(new Rect(16,16,24,16));
                tile.setLayer(Layer.ORDERED);
            }
        } else {
            wallTiles.add(tile);
            if (tileNumber != 48) tile.setLayer(Layer.TOP);
            else tile.setLayer(Layer.BOTTOM);
        }
    }
    
    /**
     * Draws all tiles and entities in the game panel, managing their draw order.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {
        drawBottom(g2);
        drawOrdered(g2);
        drawTop(g2);
    }

    /**
     * Draws the bottom layer of tiles, including floor tiles and wall tiles with Layer.BOTTOM.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    private void drawBottom(Graphics2D g2) {
        for (Tile floor : floorTiles) {
            floor.draw(g2);
        }
        for (Tile wall : wallTiles) {
            if (wall.layer == Layer.BOTTOM) {
                wall.draw(g2);
            }
        }
    }

    /**
     * Draws tiles and entities in a specific order to ensure correct visual layering.
     * This includes drawing enemies, rewards, ordered wall tiles, the player, and the exit.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void drawOrdered(Graphics2D g2) {
        List<Enemy> enemies = gp.getEnemyGenerator().getEnemies();
        List<Reward> rewards = gp.getRewardGenerator().getRewards();
        Player player = gp.getPlayer();
        
        for (Enemy enemy : enemies) {
            if (player.isAbove(enemy)) enemy.draw(g2);
        }
        for (Reward reward : rewards) {
            if (player.isAbove(reward)) reward.draw(g2);
        }
        for (Tile wall : wallTiles) {
            if (wall.layer == Layer.ORDERED && player.isAbove(wall)) wall.draw(g2);
        }
        if (exit != null && player.isAbove(exit)) exit.draw(g2);
        
        player.draw(g2);
        
        for (Enemy enemy : enemies) {
            if (!player.isAbove(enemy)) enemy.draw(g2);
        }
        for (Reward reward : rewards) {
            if (!player.isAbove(reward)) reward.draw(g2);
        }
        for (Tile wall : wallTiles) {
            if (wall.layer == Layer.ORDERED && !player.isAbove(wall)) wall.draw(g2);
        }
        if (exit != null && !player.isAbove(exit)) exit.draw(g2);
    }

    /**
     * Draws the top layer of wall tiles with Layer.TOP.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    private void drawTop(Graphics2D g2) {
        for (Tile wall : wallTiles) {
            if (wall.layer == Layer.TOP) {
                wall.draw(g2);
            }
        }
    }

}
