package game.level;

import game.object.Vector;
import game.panel.GamePanel;
import game.utils.ResourceLoader;

import java.io.BufferedReader;

public class Level {
    public int levelNumber;
    private GamePanel gp;

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
     * Get the time limit
     */
    private void loadLevel() {
        try {
            String path = String.format("/level/%d.level", levelNumber);
            BufferedReader reader = ResourceLoader.loadFile(path);
            VALUABLES_COUNT = Integer.parseInt(reader.readLine());
            TIME_LIMIT = Double.parseDouble(reader.readLine());
            String[] enemiesString = reader.readLine().split(" ");
            GUARDS_COUNT = Integer.parseInt(enemiesString[0]);
            DOGS_COUNT = Integer.parseInt(enemiesString[1]);
            CAMERA_COUNT = Integer.parseInt(enemiesString[2]);
            LASER_COUNT = Integer.parseInt(enemiesString[3]);
            String[] mapString = reader.readLine().split(" ");
            Vector mapSize = new Vector(Integer.parseInt(mapString[0]), Integer.parseInt(mapString[1]));
            gp.getTileManager().loadMap(reader, mapSize.x,mapSize.y);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
