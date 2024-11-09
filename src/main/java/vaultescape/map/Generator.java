package vaultescape.map;

import vaultescape.tile.Tile;
import vaultescape.ui.GamePanel;
import vaultescape.utils.Vector;

import java.util.*;

public class Generator<T extends Tile> {
    GamePanel gp;
    TileGenerator tg;
    public List<T> elements = new ArrayList<>();

    public Generator(GamePanel gp) {
        this.gp = gp;
    }

    public void spawn(Class<? extends T> type, int count) {
        try {
            for (int i = 0; i < count; i++) {
                Class[] cArg = new Class[2];
                cArg[0] = GamePanel.class;
                cArg[1] = Vector.class;
                Vector tile = gp.getTileGenerator().nextEmptyTile();
                T element = type.getDeclaredConstructor(cArg).newInstance(gp, tile);
                elements.add(element);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        for (T element : elements) {
            element.update();
        }
    }

    public int getCountByType(Class<? extends T> type) {
        int i = 0;
        for (T element : elements) {
            if (element.getClass() == type) i++;
        }
        return i;
    }
}
