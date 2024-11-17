package game.map;

import game.object.Rect;
import game.object.Vector;
import game.panel.GamePanel;
import game.tile.Tile;
import game.tile.Tile.Layer;
import game.tile.entity.Exit;
import game.tile.entity.character.Player;
import game.tile.entity.character.enemy.*;
import game.tile.entity.reward.Reward;
import game.utils.ResourceLoader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.*;

/**
 * Responsible for generating and managing the tiles in the game, including floors, walls, and available tiles for spawning.
 * Loads map data from resources and separates tiles based on their types and properties.
 * Also handles drawing the tiles in the correct order to ensure proper layering in the game.
 */
public class TileGenerator {

    GamePanel gp;
    TileGenerator tg;
    Random random;
    public Exit exit; // Exit door

    public BufferedImage floorSheet;
    public BufferedImage wallSheet;

    public List<Vector> emptyTiles = new ArrayList<>();
    public Map<String, Tile> wallTiles = new HashMap<String, Tile>();
    public Set<Tile> floorTiles = new HashSet<>();

    private List<Tile> cameraTiles = new ArrayList<>();
    private List<Tile> laserTiles = new ArrayList<>();

    private final static int[] _bottomWallNums = {
        2, 4, 6, 8, 9, 10, 12, 14, 16, 26, 34, 36, 38, 40, 74, 76, 78, 80};

    private final static int[] _cameraWallNums = {1,3,5,7,13,15,25,51,52};

    private final static int[] _laserWallNums = {2,4,6,8,9,10,14,16,26};

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
            wallSheet = ResourceLoader.loadSpritesheet("wall");
            floorSheet = ResourceLoader.loadSpritesheet("floor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map data from text files and generates floor and wall tiles based on the map layout.
     */
    public void loadMap() {
        try {
            BufferedReader reader = ResourceLoader.loadFile("/map/floor1.map");
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
            reader = ResourceLoader.loadFile("/map/wall1.map");
    
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
        } catch (Exception e) {}
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

    public Vector nextCameraTile() { 
        Random rand = new Random();
        int randIndex = rand.nextInt(0, cameraTiles.size());
        Tile wall = cameraTiles.remove(randIndex);
        return wall.getPosition().add(new Vector(0, 16));
    }

    public Vector nextLaserTile() {
        Random rand = new Random();
        int randIndex = rand.nextInt(0, laserTiles.size());
        Tile wall = laserTiles.remove(randIndex);
        return wall.getPosition();
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
        tile.setLayer(Layer.BOTTOM);
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
        tile.getHitbox().setSize(tile.getSize().scale(0.8));
        tile.getHitbox().setPosition(8,0);
        // Vault Door
        if (tileNumber == 75) {
            if (exit == null){
                exit = new Exit(gp,rect.getPosition());
                wallTiles.put(rect.getUnitString(),exit);
                exit.setLayer(Layer.ORDERED);
            }
            return;
        }
        // Doors and Upper Pillar (no collision)
        if (tileNumber >= 17 && tileNumber <= 25) {
            tile.setCollisionMask(false);
            wallTiles.put(rect.getUnitString(),tile);
            if (tileNumber == 18 || tileNumber == 20) tile.setLayer(Layer.ORDERED);
            else tile.setLayer(Layer.TOP);
            return;
        }

        boolean isBottomTile = false;
        for (int num : _bottomWallNums) if (tileNumber == num) {isBottomTile = true;break;}
        for (int num : _cameraWallNums) if (tileNumber == num) {cameraTiles.add(tile);break;}
        for (int num : _laserWallNums) {if (tileNumber == num) {laserTiles.add(tile);break;}}
        
        if (isBottomTile) {
            if (tileNumber == 26) { // Bottom Pillar
                tile.getHitbox().setRect(new Rect(16,16,24,16));
                tile.setLayer(Layer.ORDERED);
            } else if (tileNumber == 74) {
                tile.getHitbox().setRect(new Rect(0,16,48,8));
            } else tile.setLayer(Layer.BOTTOM);
        } else {
            if (tileNumber != 48) tile.setLayer(Layer.TOP);
            else tile.setLayer(Layer.BOTTOM);
        }
        wallTiles.put(rect.getUnitString(),tile);
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
     * Also draws shadows for enemies, rewards, and the player.
     * @param g2 The Graphics2D object used for drawing.
     */
    private void drawBottom(Graphics2D g2) {
        List<Enemy> enemies = gp.getEnemyGenerator().getEnemies();
        List<Reward> rewards = gp.getRewardGenerator().getRewards();
        Player player = gp.getPlayer();
        for (Tile wall : getScreenTiles()) {
            if (wall.layer == Layer.BOTTOM) {
                wall.draw(g2);
            }
        }
        for (Reward reward : rewards) {
            reward.drawShadow(g2);
        }
        for (Enemy enemy : enemies) {
            if (enemy instanceof Camera) {
                Camera camera = (Camera) enemy;
                camera.getSpotlight().draw(g2);
            }
            enemy.drawShadow(g2);
        }
        player.drawShadow(g2);
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
            if (!player.isAbove(enemy) && enemy.layer == Layer.ORDERED) enemy.draw(g2);
            if (enemy instanceof Laser) ((Laser)enemy).drawLaser(g2);
        }
        for (Reward reward : rewards) {
            if (!player.isAbove(reward)) reward.draw(g2);
        }
        for (Tile wall : getScreenTiles()) {
            if (wall.layer == Layer.ORDERED && !player.isAbove(wall)) wall.draw(g2);
        }
        if (exit != null && player.isAbove(exit)) exit.draw(g2);
        
        player.draw(g2);
        
        for (Tile wall : getScreenTiles()) {
            if (wall.layer == Layer.ORDERED && player.isAbove(wall)) wall.draw(g2);
        }

        for (Enemy enemy : enemies) {
            if (player.isAbove(enemy)) enemy.draw(g2);
        }
        for (Reward reward : rewards) {
            if (player.isAbove(reward)) reward.draw(g2);
        }
        
        if (exit != null && player.isAbove(exit)) exit.draw(g2);
    }

    /**
     * Draws the top layer of wall tiles with Layer.TOP.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    private void drawTop(Graphics2D g2) {
        List<Enemy> enemies = gp.getEnemyGenerator().getEnemies();
        for (Tile wall : wallTiles.values()) {
            if (wall.layer == Layer.TOP) {
                wall.draw(g2);
            }
        }
        for (Enemy enemy : enemies) {
            if (enemy.layer == Layer.TOP) enemy.draw(g2);
        }
    }

    /**
     * Updates the camera entities in the game panel.
     *
     * @return a list of tiles that are currently visible on the screen
     */
    private List<Tile> getScreenTiles() {
        List<Tile> screenTiles = new ArrayList<>();
        for (Tile floor : floorTiles) {
            if (floor.isVisibleOnScreen()) screenTiles.add(floor);
        }
        for (Tile wall : wallTiles.values()) {
            if (wall.isVisibleOnScreen() || wall instanceof Exit) screenTiles.add(wall);
        }
        return screenTiles;
    }

}
