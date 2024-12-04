package game.level;

import java.io.BufferedReader;
import java.io.IOException;

import game.object.Vector;
import game.panel.GamePanel;
import game.utils.ResourceLoader;

public class Level{
    public int levelNumber;
    private final GamePanel gp;

    // Time
    public double TIME_LIMIT = 60.0;

    // Valuables
    public int VALUABLES_COUNT;

    // Enemies
    public int GUARDS_COUNT;
    public int DOGS_COUNT;
    public int CAMERA_COUNT;
    public int LASER_COUNT;
    
    /**
     * Constructor for Level
     * @param levelNumber the level number
     */
    public Level(GamePanel gp, int levelNumber) {
        this.gp = gp;
        this.levelNumber = levelNumber;
        loadLevel();
    }

    /**
     * Method to Load the map
     */
    private void loadLevel() {
        String path = String.format("/level/%d.level", levelNumber);
        try (BufferedReader reader = ResourceLoader.loadFile(path)) {
            parseLevelData(reader);
            loadMap(reader);
        } catch (IOException | NumberFormatException e) {
            handleLoadingError(e, path);
        } catch (Exception e) {
            handleUnexpectedError(e, path);
        }
    }

    private void parseLevelData(BufferedReader reader) throws IOException, NumberFormatException {
        // Parsing valuable count
        VALUABLES_COUNT = Integer.parseInt(reader.readLine());

        // Parsing time limit
        TIME_LIMIT = Double.parseDouble(reader.readLine());

        // Parsing enemy counts
        String[] enemiesString = reader.readLine().split(" ");
        GUARDS_COUNT = Integer.parseInt(enemiesString[0]);
        DOGS_COUNT = Integer.parseInt(enemiesString[1]);
        CAMERA_COUNT = Integer.parseInt(enemiesString[2]);
        LASER_COUNT = Integer.parseInt(enemiesString[3]);
    }

    private void loadMap(BufferedReader reader) throws IOException {
        String[] mapString = reader.readLine().split(" ");
        Vector mapSize = new Vector(Integer.parseInt(mapString[0]), Integer.parseInt(mapString[1]));
        gp.getTileManager().loadMap(reader, mapSize.x, mapSize.y);
    }

    private void handleLoadingError(Exception e, String path) {
        System.err.println("Error loading level at " + path);
        e.printStackTrace();
    }

    private void handleUnexpectedError(Exception e, String path) {
        System.err.println("An unexpected error occurred while loading level: " + path);
        e.printStackTrace();
    }
}
