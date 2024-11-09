package vaultescape.ui;

import vaultescape.App;
import vaultescape.audio.*;
import vaultescape.entity.Player;
import vaultescape.entity.reward.Valuable;
import vaultescape.map.*;
import vaultescape.utils.*;

import javax.swing.JPanel;

import java.awt.*;
import java.io.InputStream;

/**
 * Represents the main game panel where all game elements are drawn and updated, such as the player, enemies,
 * rewards, and timer. Manages the game loop, input, and screen dimensions.
 */
public class GamePanel extends JPanel implements Runnable {

    public final Vector SCREEN_DIM = new Vector(20,12); // Screen Size (in tiles)
    public final Vector SCREEN_SIZE = SCREEN_DIM.toGlobal(); // Screen Size (in pixels)

    // Map properties
    public final Vector MAP_TILE = new Vector(40,40); // Map Size (in tiles)
    public final Vector MAP_SIZE = MAP_TILE.toGlobal(); // Map Size (in pixels)

    // Frames per second for game loop
    private final static int FPS = 60;

    // Game Map Data

    // Game components
    private final KeyDetector keyh = new KeyDetector();
    private TileGenerator tileGenerator = new TileGenerator(this);
    private final Vector playerSpawnPos = new Vector(33,3).toGlobal();
    private final Player player = new Player(this, playerSpawnPos,keyh);

    // Thread
    private Thread gameThread;
    private GameOverOverlay gameOverOverlay;

    // Rewards
    private final RewardGenerator rewardGenerator = new RewardGenerator(this, tileGenerator);
    private final int VALUABLES_COUNT = 6;
    private final int DIAMONDS_COUNT = 1;

    // Enemies
    private final EnemyGenerator enemyGenerator = new EnemyGenerator(this);
    private static final int GUARDS_COUNT = 8;
    private static final int DOGS_COUNT = 2;
    private static final int CAMERA_COUNT = 4;

    //Music Components
    private Music music = new Music();
    private SFX sfx = new SFX();

    // Timer
    private Timer timer;
    public static final double LEVEL_TIME = 60.0;
    private int lastTime = 60;

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
        this.setSize(SCREEN_SIZE.x, SCREEN_SIZE.y);
        this.setBackground(new Color(89, 81, 120));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);
        this.loadResources();
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
    public TileGenerator getTileGenerator() {return tileGenerator;}

    /**
     * Retrieves the reward generator instance.
     *
     * @return the reward generator for managing game rewards
     */
    public RewardGenerator getRewardGenerator() {return rewardGenerator;}

    /**
     * Retrieves the enemy generator instance.
     *
     * @return the enemy generator for managing game enemies
     */
    public EnemyGenerator getEnemyGenerator() {return enemyGenerator;}

    /**
     * Gets the backround music (Music) object.
     * @return the Music object
     */
    public Music getMusic() {return music;}

    /**
     * Gets the sound effects (SFX) object.
     * @return the SFX object
     */
    public SFX getSFX() {return sfx;}

    /**
     * Stops the game upon escaping the vault
     */
    public void completeGame(boolean isWin) {
        gameThread = null;
        music.stop();
        if (isWin) {
            sfx.play("game_complete");
            app.updateBestScoreAfterGame(getFinalScore());
        }
        else {sfx.play("game_over"); sfx.play("alarm"); sfx.loop(2);}
        updateGameOverScreen(isWin);
        showGameOverScreen();
    }


    /**
     * Updates the Game Over screen by creating a new instance of the GameOverOverlay with player's end score.
     * This overlay displays the game over information and provides options to restart the game,
     * go back to the main menu, or exit the application.
     *
     * @see GameOverOverlay
     */
    private void updateGameOverScreen(boolean isWin) {
        // If there is an existing overlay, remove it from the App's content pane
        if (gameOverOverlay != null) {
            app.remove(gameOverOverlay);
        }

        // Create a new GameOverOverlay instance
        gameOverOverlay = new GameOverOverlay(
                player,
                isWin,
                (int)timer.getTimeLeft(),
                getFinalScore(),
                e -> {hideGameOverScreen();app.startGame();},
                e -> {hideGameOverScreen();app.backToMenu();},
                e -> System.exit(0)
        );

        gameOverOverlay.setBounds(0, 0, app.getWidth(), app.getHeight());
        gameOverOverlay.setBackground(new Color(0, 0, 0, 0));
        app.add(gameOverOverlay);
        app.setComponentZOrder(gameOverOverlay, 0);
        app.revalidate();
        app.repaint();
    }

    /**
     * Displays the Game Over screen overlay by setting its bounds to match the
     * dimensions of the current panel. It makes the overlay visible, sets its
     * background color to transparent, and adds it to the main application frame.
     * This method also updates the component order to ensure the overlay is rendered on top.
     */
    private void showGameOverScreen() {
        gameOverOverlay.setVisible(true);
    }

    /**
     * Hides the Game Over screen overlay by setting its visibility to false.
     * This effectively removes the overlay from view without removing it from
     * the application frame, allowing it to be shown again later if needed.
     */
    private void hideGameOverScreen() {
        gameOverOverlay.setVisible(false);
    }
    /**
     * Starts the game thread, initializing the timer and generating enemies and rewards.
     */
    public void startGameThread() {
        timer = new Timer(LEVEL_TIME);
        enemyGenerator.spawnAll(GUARDS_COUNT, DOGS_COUNT, CAMERA_COUNT);
        rewardGenerator.spawnAll(VALUABLES_COUNT,DIAMONDS_COUNT);
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Runs the game loop, updating and repainting the game at a fixed frame rate.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            if (timer.isTimeUp()) {
                completeGame(false);
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
     * Calculates and returns the final score for the player.
     * The final score is computed based on the time left and the player's score.
     * 
     * @return the final score as an integer
     */
    public int getFinalScore() {
        if (timer.isTimeUp()) return player.getScore();
        else return (int)getTimer().getTimeLeft() / 50 + 200 + player.getScore();
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

        // Gametime Logic (music changes + speed increase + countdown)
        int currentTime = (int)timer.getTimeLeft() / 1000;
        if (lastTime != currentTime) {
            if (lastTime <= 60 && lastTime > 30 && !music.isPlaying("60")) {
                music.play("60");
            }
            else if (lastTime <= 31 && lastTime > 15 && !music.isPlaying("30")) {
                music.play("30");
                sfx.play("time_tick");
                enemyGenerator.addEnemySpeed(1);
            }
            else if (lastTime <= 16 && lastTime > 0 && !music.isPlaying("15")) {
                music.play("15");
                sfx.play("time_tick");
                enemyGenerator.addEnemySpeed(1);
                player.addSpeed(1);
            }
            if (lastTime <= 10) sfx.play("time_tick");
            lastTime = currentTime;
        }
        
    }

    /**
     * Paints all game elements onto the screen, including the background, 
     * player, enemies, rewards, and UI elements.
     *
     * @param g the Graphics object for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);

        //tileGenerator.drawFloor(g2); // Draw floor tiles
        //player.drawShadow(g2); // Draw player shadow on the floor
        //tileGenerator.drawBottom(g2); // Draw bottom layer tiles
        //rewardGenerator.draw(g2); // Draw rewards
        //enemyGenerator.drawEnemies(g2); // Draw enemies

        tileGenerator.draw(g2);

        //player.draw(g2); // Draw player
        //tileGenerator.drawTop(g2); // Draw top layer tiles

        // Draw timer and score UI
        g2.setFont(font);
        int textY = 620;
        
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        g2.drawString("Time: " + timer.getFormattedTimeLeft(), 80, textY + 6);
        g2.setColor(Color.WHITE);
        g2.drawString("Time: " + timer.getFormattedTimeLeft(), 80, textY);

        String rewardsText = "Valuables Left: " + rewardGenerator.generator.getCountByType(Valuable.class);
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        g2.drawString(rewardsText, 80, textY + 46);
        g2.setColor(new Color(255, 255, 0));
        g2.drawString(rewardsText, 80, textY + 40);

        String scoreText = "Score: " + String.format("%03d", player.getScore());
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        g2.drawString(scoreText, 80, textY + 86);
        g2.setColor(new Color(0, 128, 255));
        g2.drawString(scoreText, 80, textY + 80);

        if (sfx.isPlaying("alarm")) {
            g2.setColor(new Color(1.0f, 0.0f, 0.0f, 0.3f));
            g2.fillRect(0,0,SCREEN_SIZE.x,SCREEN_SIZE.y);
        }

        g2.dispose();
    }
}
