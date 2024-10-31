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
    private long lastSpeedBoostTime = 0;
    private static final long SPEEDUP_COOLDOWN = 5000;

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
                    x2 = Math.min(x2, gp.mapWidth - gp.tilesize);
                    y2 = Math.min(y2, gp.mapHeight - gp.tilesize);
                    enemy = new Guard(gp, start[0], start[1], x2, y2);
                } else if (enemyType == Dog.class) {
                    enemy = new Dog(gp, start[0], start[1]);
                } else if (enemyType == Camera.class) {
                    enemy = new Camera(gp, start[0], start[1], 100);
                } else {
                    continue;
                }
                enemies.add(enemy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void generateAllEnemies(int guardsCount, int dogsCount, int cameraCount) {
        generateEnemies(Guard.class, guardsCount);  
        generateEnemies(Dog.class, dogsCount);   
        generateEnemies(Camera.class, cameraCount);
    }

    public void update(Player player) {
        checkEnemyCollision(player);  
        for (Enemy enemy : enemies) {
            enemy.update();  
        }
    }

    public void increaseEnemySpeed(double multiplier){
        for(Enemy enemy : enemies){
            if(enemy instanceof Guard || enemy instanceof Dog){
                enemy.setSpeed(enemy.getSpeed() * multiplier);
            }
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
                    System.out.println("Dog caught you!");
                }
            }
            if (enemy instanceof Camera camera) {
                if (camera.isPlayerInRange() && camera.canDetect()) {
                    increaseEnemySpeed(1.03); 
                    camera.recordDetection();
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
