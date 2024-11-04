package vaultescape.map;

import java.awt.*;
import java.io.InputStream;

import javax.swing.JPanel;

import vaultescape.App;
import vaultescape.entity.EnemyGenerator;
import vaultescape.entity.Player;
import vaultescape.reward.RewardGenerator;
import vaultescape.ui.Timer;

/**
 * Represents the main game panel where all game elements are drawn and updated, such as the player, enemies,
 * rewards, and timer. Manages the game loop, input, and screen dimensions.
 */
public class GamePanel extends JPanel implements Runnable {

    // Tile and screen properties
    final int defaultTileSize = 16; // Base size of a tile
    final int scale = 4; // Scale factor for tile size
    public final int tilesize = defaultTileSize * scale; // 64x64 scaled tile size
    public final int numScreenCols = 20;
    public final int numScreenRows = 12;
    public final int screenWidth = tilesize * numScreenCols; // Screen width in pixels
    public final int screenHeight = tilesize * numScreenRows; // Screen height in pixels

    // Map properties
    public final int numMapCols = 40;
    public final int numMapRows = 40;
    public final int mapWidth = tilesize * numMapCols;
    public final int mapHeight = tilesize * numMapRows;

    // Camera detection flag
    private boolean playerDetected = false;

    // Frames per second for game loop
    final int fps = 60;

    // Game components
    private final KeyDetector keyh = new KeyDetector();
    private TileGenerator tileGenerator = new TileGenerator(this);
    private final Player player = new Player(this, keyh);
    private Thread gameThread;

    // Timer
    private Timer timer;
    public long levelTime = 60;

    // Reward and Enemy generators
    private final RewardGenerator rewardGenerator;
    private final int regularRewardCount = 5;
    private final EnemyGenerator enemyGenerator;
    private final int guardsCount = 8;
    private final int dogsCount = 2;
    private final int cameraCount = 1;

    // App reference and font resource
    public App app;
    private Font font;

    /**
     * Constructs the GamePanel, setting up game dimensions, components, resources, and input listeners.
     *
     * @param app the main application instance
     */
    public GamePanel(App app) {
        this.app = app;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(89, 81, 120));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);
        this.loadResources();
        tileGenerator = new TileGenerator(this);
        rewardGenerator = new RewardGenerator(this, tileGenerator);
        enemyGenerator = new EnemyGenerator(this);
    }

    /**
     * Loads the font resource and any other future global resources needed for the game UI.
     */
    private void loadResources() {
        try {
            InputStream fontStream = getClass().getResourceAsStream("/ui/royal-intonation.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 32);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the tile generator instance.
     *
     * @return the tile generator for managing game map tiles
     */
    public TileGenerator getTileGenerator() {
        return tileGenerator;
    }

    /**
     * Retrieves the reward generator instance.
     *
     * @return the reward generator for managing game rewards
     */
    public RewardGenerator getRewardGenerator() {
        return rewardGenerator;
    }

    /**
     * Retrieves the enemy generator instance.
     *
     * @return the enemy generator for managing game enemies
     */
    public EnemyGenerator getEnemyGenerator() {
        return enemyGenerator;
    }

    /**
     * Checks if the player has been detected by any camera.
     *
     * @return true if the player is detected, false otherwise
     */
    public boolean isPlayerDetected() {
        return playerDetected;
    }

    /**
     * Sets the player detection status.
     *
     * @param detected true to indicate the player is detected, false otherwise
     */
    public void setPlayerDetected(boolean detected) {
        this.playerDetected = detected;
    }

    /**
     * Stops the game upon escaping the vault
     */
    public void completeGame() {
        gameThread = null;
        System.out.println("VICTORY: You escaped!");
        app.backToMenu(); // Return back to menu after game ends
    }

    /**
     * Stops the game upon running out of time
     */
    public void gameOver() {
        gameThread = null;
        System.out.println("GAME OVER: Time is up! Exit is closed!");
        app.backToMenu(); // Return back to menu after game ends
    }
    /**
     * Starts the game thread, initializing the timer and generating enemies and rewards.
     */
    public void startGameThread() {
        timer = new Timer(levelTime);
        enemyGenerator.generateAllEnemies(guardsCount, dogsCount, cameraCount);
        rewardGenerator.generateRegularRewards(regularRewardCount);
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Runs the game loop, updating and repainting the game at a fixed frame rate.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            if (timer.isTimeUp()) {
                gameOver();
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

    /**
     * Retrieves the timer instance.
     *
     * @return the timer managing the game's countdown
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Retrieves the player instance.
     *
     * @return the player entity
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Updates the state of the player, rewards, and enemies. Checks if the timer has expired.
     */
    public void update() {
        player.update();
        rewardGenerator.update(player);
        enemyGenerator.update(player);
    }

    /**
     * Paints all game elements onto the screen, including the background, player, enemies, rewards, and UI elements.
     *
     * @param g the Graphics object for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);

        tileGenerator.drawFloor(g2); // Draw floor tiles
        player.drawShadow(g2); // Draw player shadow on the floor
        tileGenerator.drawBottom(g2); // Draw bottom layer tiles
        rewardGenerator.drawRewards(g2); // Draw rewards
        enemyGenerator.drawEnemies(g2); // Draw enemies

        player.draw(g2); // Draw player
        tileGenerator.drawTop(g2); // Draw top layer tiles

        // Draw timer and score UI
        g2.setFont(font);
        int textY = 620;
        
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        g2.drawString("Time: " + timer.getFormattedTimeLeft(), 80, textY + 6);
        g2.setColor(Color.WHITE);
        g2.drawString("Time: " + timer.getFormattedTimeLeft(), 80, textY);

        String rewardsText = "Basic Rewards Left: " + rewardGenerator.getRegularRewardsSize();
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        g2.drawString(rewardsText, 80, textY + 46);
        g2.setColor(new Color(255, 255, 0));
        g2.drawString(rewardsText, 80, textY + 40);

        String scoreText = "Score: " + String.format("%03d", player.getScore());
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        g2.drawString(scoreText, 80, textY + 86);
        g2.setColor(new Color(0, 128, 255));
        g2.drawString(scoreText, 80, textY + 80);

        g2.dispose();
    }
}
