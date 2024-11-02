package vaultescape.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import vaultescape.App;
import vaultescape.entity.EnemyGenerator;
import vaultescape.entity.Player;
import vaultescape.reward.RewardGenerator;
import vaultescape.ui.Timer;

public class GamePanel extends JPanel implements Runnable {
    public App app;

    //Tilesize
    final int defaultTileSize = 16; // 16x16 image tile
    final int scale = 4;

    // Screen
    public final int tilesize = defaultTileSize * scale; // 64x64 screen tile
    public final int numScreenCols = 20;
    public final int numScreenRows = 12;

    public final int screenWidth = tilesize * numScreenCols; // 1280px
    public final int screenHeight = tilesize * numScreenRows; // 768px

    // Map
    public final int numMapCols = 40;
    public final int numMapRows = 40;
    public final int mapWidth = tilesize * numMapCols;
    public final int mapHeight = tilesize * numMapRows;

    // FPS
    final int fps = 60;

    //Game basic
    private Thread gameThread;
    private KeyDetector keyh = new KeyDetector();
    private TileGenerator tileGenerator = new TileGenerator(this);
    private Player player = new Player(this, keyh);

    // Timer
    private Timer timer;
    public long levelTime = 60;

    // Rewards
    private RewardGenerator rewardGenerator;
    private int regularRewardCount = 7;

    //Enemies
    private EnemyGenerator enemyGenerator;

    public Player getPlayer(){
        return player;
    }
    // Constructor
    public GamePanel(App app) {
        this.app = app;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(82,45,61));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);

        tileGenerator = new TileGenerator(this);
        rewardGenerator = new RewardGenerator(this, tileGenerator);
        enemyGenerator = new EnemyGenerator(this);
    }

    public TileGenerator getTileGenerator() {
        return tileGenerator;  // Provide access to tile generator
    }
    public RewardGenerator getRewardGenerator(){
        return rewardGenerator;
    }
    public EnemyGenerator getEnemyGenerator(){
        return enemyGenerator;
    }

    public void startGameThread() {
        timer = new Timer(levelTime);
        enemyGenerator.generateAllEnemies(2, 1, 1);
        rewardGenerator.generateRegularRewards(regularRewardCount);
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            if(timer.isTimeUp()){
                System.out.println("Time is up! Exit is closed!");
                gameThread = null;
                app.backToMenu();
                return;
            }
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetGame() {
        timer = new Timer(levelTime);  
        player.setX(8 * tilesize);
        player.setY(8 * tilesize);
        rewardGenerator.generateRegularRewards(regularRewardCount);
    }

    public Timer getTimer(){
        return timer;
    }

    public void update() {
        player.update();
        rewardGenerator.update(player);
        enemyGenerator.update(player);
        if(timer.isTimeUp()){
            // gameThread = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(java.awt.Color.WHITE);

        player.drawShadow(g2); // Draw shadow first
        tileGenerator.drawBottom(g2);  // Draw bottom tile
        rewardGenerator.drawRewards(g2);
        enemyGenerator.drawEnemies(g2);

        player.draw(g2); 
        tileGenerator.drawTop(g2); // Draw top tiles over player

        g2.setFont(g2.getFont().deriveFont(20f)); 
        g2.setColor(java.awt.Color.WHITE);
        g2.drawString("Time: " + timer.getFormattedTimeLeft(), 80, 680);

        String scoreText = "Score: " + String.format("%03d", player.getScore());  
        int scoreX = 80 + g2.getFontMetrics().stringWidth("Time: " + timer.getFormattedTimeLeft()) + 20;  
        g2.drawString(scoreText, scoreX, 680);  

        g2.dispose();
    }
}
