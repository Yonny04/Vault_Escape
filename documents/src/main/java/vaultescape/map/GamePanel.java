package vaultescape.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.InputStream;

import javax.swing.JPanel;

import vaultescape.App;
import vaultescape.entity.EnemyGenerator;
import vaultescape.entity.Player;
import vaultescape.reward.RewardGenerator;
import vaultescape.ui.Timer;

public class GamePanel extends JPanel implements Runnable {

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

    // Camera Detection 
    private boolean playerDetected = false;

    // FPS
    final int fps = 60;

    //Game basic
    private final KeyDetector keyh = new KeyDetector();
    private TileGenerator tileGenerator = new TileGenerator(this);
    private final Player player = new Player(this, keyh);
    // private BGM bgm = new BGM();
    private Thread gameThread;

    // Timer
    private Timer timer;
    public long levelTime = 60;

    // Rewards
    private final RewardGenerator rewardGenerator;
    private final  int regularRewardCount = 7;

    //Enemies
    private final EnemyGenerator enemyGenerator;

    public Player getPlayer(){
        return player;
    }

    public App app;
    private Font font;
    // Constructor
    public GamePanel(App app) {
        this.app = app;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(89,81,120));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);
        this.loadResources();
        tileGenerator = new TileGenerator(this);
        rewardGenerator = new RewardGenerator(this, tileGenerator);
        enemyGenerator = new EnemyGenerator(this);
    }

    /**
     * Loads the font resource (and any other future
     * global resource for the Game UI)
     */
    private void loadResources() {
        try {
            InputStream fontStream = getClass().getResourceAsStream("/ui/royal-intonation.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 32);
        } catch (Exception e) {e.printStackTrace();}
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
    public boolean isPlayerDetected() {
        return playerDetected;
    }
    public void setPlayerDetected(boolean detected) {
        this.playerDetected = detected;
    }

    public void startGameThread() {
        timer = new Timer(levelTime);
        enemyGenerator.generateAllEnemies(10, 2, 1);
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



    //for music
    // public void playMusic(int i){
    //     bgm.setFile();
    //     bgm.play();
    //     bgm.loop();
    // }

    // public void stopMusic(){
    //     bgm.stop();
    // }



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

        tileGenerator.drawFloor(g2); // Draw floor
        player.drawShadow(g2); // Draw shadow on floor
        tileGenerator.drawBottom(g2);  // Draw bottom tile
        rewardGenerator.drawRewards(g2);
        enemyGenerator.drawEnemies(g2);

        player.draw(g2); 
        tileGenerator.drawTop(g2); // Draw top tiles over player

        g2.setFont(font);
        g2.setColor(new Color(0.0f,0.0f,0.0f,0.5f));
        g2.drawString("Time: " + timer.getFormattedTimeLeft(), 80, 686);
        g2.setColor(new Color(229,255,184));
        g2.drawString("Time: " + timer.getFormattedTimeLeft(), 80, 680);
        
        g2.setColor(new Color(0.0f,0.0f,0.0f,0.5f));
        String scoreText = "Score: " + String.format("%03d", player.getScore());  
        int scoreX = 80 + g2.getFontMetrics().stringWidth("Time: " + timer.getFormattedTimeLeft()) + 20;  
        g2.drawString(scoreText, scoreX, 686);  
        g2.setColor(new Color(255,216,133));
        g2.drawString(scoreText, scoreX, 680);  
        g2.dispose();
    }
}
