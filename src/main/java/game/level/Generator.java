package game.level;

import game.object.Vector;
import game.panel.GamePanel;
import game.tile.Tile;
import game.tile.entity.character.enemy.*;

import java.util.*;

/**
 * The Generator class is responsible for managing and spawning tiles in a game panel.
 *
 * @param <T> The type of tile this generator manages, which must extend the Tile class.
 */
public class Generator<T extends Tile> {
    GamePanel gp;
    public List<T> elements = new ArrayList<>();

    /**
     * Constructs a Generator object with the specified game panel.
     *
     * @param gp The game panel associated with this generator.
     */
    public Generator(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Spawns a specified number of tiles of the given type and adds them to the elements list.
     * Each tile is instantiated using reflection.
     *
     * @param type  The class type of the tiles to spawn.
     * @param count The number of tiles to spawn.
     */
    public void spawn(Class<? extends T> type, int count) {
        try {
            for (int i = 0; i < count; i++) {
                Class<?>[] args = new Class[]{GamePanel.class, Vector.class};
                Vector tile = gp.getTileManager().nextEmptyTile();
                if (type == Camera.class) {
                    tile = gp.getTileManager().nextCameraTile();
                }
                else if (type == Laser.class) {
                    tile = gp.getTileManager().nextLaserTile();
                }
                T element = type.getDeclaredConstructor(args).newInstance(gp, tile);
                elements.add(element);
            }
        } catch (Exception e) {}
    }

    /**
     * Updates all tiles managed by this generator by calling their update method.
     */
    public void update() {
        for (T element : elements) {
            element.update();
        }
    }

    /**
     * Returns the count of tiles of the specified type.
     *
     * @param type The class type of the tiles to count.
     * @return The number of tiles of the specified type.
     */
    public int getCountByType(Class<? extends T> type) {
        int i = 0;
        for (T element : elements) {
            if (element.getClass() == type) i++;
        }
        return i;
    }
}

