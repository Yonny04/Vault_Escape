package vaultescape.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vaultescape.map.GamePanel;
import vaultescape.map.TileGenerator;

public class EnemyGenerator {
    private GamePanel gp;
    private TileGenerator tg;
    private List<Enemy> enemies;
    private Random random;

    public EnemyGenerator(GamePanel gp) {
        this.gp = gp;
        this.tg = gp.getTileGenerator(); 
        this.enemies = new ArrayList<>();
        this.random = new Random();
    }

    public void generateGuards(int count) {
        enemies.clear(); 
        List<int[]> availableTiles = new ArrayList<>(tg.availableTiles);

        for (int i = 0; i < count && !availableTiles.isEmpty(); i++) {
            int index = random.nextInt(availableTiles.size());
            int[] start = availableTiles.remove(index); 
            int range = 200 + random.nextInt(251);
            boolean isHorizontal = random.nextBoolean();
            int x1 = start[0];
            int y1 = start[1];
            int x2 = isHorizontal ? x1 + range : x1;
            int y2 = isHorizontal ? y1 : y1 + range;
            x2 = Math.min(x2, gp.screenWidth - gp.tilesize);
            y2 = Math.min(y2, gp.screenHeight - gp.tilesize);
            Guard guard = new Guard(gp, x1, y1, x2, y2);
            enemies.add(guard);
        }
    }

    public void update(Player player) {
        checkEnemyCollision(player);  
        for (Enemy enemy : enemies) {
            enemy.update();  
        }
    }

    public void checkEnemyCollision(Player player) {
        for (Enemy enemy : enemies) {
            if (enemy instanceof Guard guard) {
                if (guard.isTouching(player) && guard.canCollide()) {
                    gp.getTimer().decreaseTime(5); 
                    guard.recordCollision();  
                }
            }
        }
    }

    public void drawEnemies(Graphics2D g2) {
        for (Enemy enemy : enemies) {
            enemy.draw(g2);
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
