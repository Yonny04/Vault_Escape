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

    private void generateEnemies(Class<? extends Enemy> enemyType, int count) {
        List<int[]> availableTiles = new ArrayList<>(tg.availableTiles);

        for (int i = 0; i < count && !availableTiles.isEmpty(); i++) {
            int index = random.nextInt(availableTiles.size());
            int[] start = availableTiles.remove(index);

            try {
                Enemy enemy;
                if (enemyType == Guard.class) {
                    int range = 200 + random.nextInt(251);
                    boolean isHorizontal = random.nextBoolean();
                    int x2 = isHorizontal ? start[0] + range : start[0];
                    int y2 = isHorizontal ? start[1] : start[1] + range;
                    x2 = Math.min(x2, gp.screenWidth - gp.tilesize);
                    y2 = Math.min(y2, gp.screenHeight - gp.tilesize);
                    enemy = new Guard(gp, start[0], start[1], x2, y2);
                } else if (enemyType == Dog.class) {
                    enemy = new Dog(gp, start[0], start[1]);
                } else {
                    continue;
                }
                enemies.add(enemy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void generateAllEnemies(int guardsCount, int dogsCount) {
        generateEnemies(Guard.class, 2);  
        generateEnemies(Dog.class, 1);   
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
            if (enemy instanceof Dog dog) {
                if (dog.isTouching(player)) {
                    System.out.println("Dog caugth you!");
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
