package game.panel;

import game.App;
import game.audio.*;
import game.map.*;
import game.object.Vector;
import game.tile.entity.character.Player;
import game.tile.entity.reward.Valuable;
import game.ui.Container;
import game.ui.Label;
import game.utils.*;

import javax.swing.JPanel;

import java.awt.*;
import java.io.InputStream;

/**
 * Represents the main game panel where all game elements are drawn and updated, such as the player, enemies,
 * rewards, and timer. Manages the game loop, input, and screen dimensions.
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen Properties
    public final Vector SCREEN_DIM = new Vector(20,12); // Screen Size (in tiles)
    public final Vector SCREEN_SIZE = SCREEN_DIM.toGlobal(); // Screen Size (in pixels)

    // Map properties
    public final Vector MAP_TILE = new Vector(40,40); // Map Size (in tiles)
    public final Vector MAP_SIZE = MAP_TILE.toGlobal(); // Map Size (in pixels)

    // Frames per second for game loop
    public final int FPS = 60;

    // Game components
    private final KeyDetector keyh = new KeyDetector();
    private TileGenerator tileGenerator = new TileGenerator(this);
    private final Vector playerSpawnPos = new Vector(33,3).toGlobal();
    private final Player player = new Player(this, playerSpawnPos,keyh);

    // Rewards
    private final RewardGenerator rewardGenerator = new RewardGenerator(this);
    private final int VALUABLES_COUNT = 6;
    private final int DIAMONDS_COUNT = 1;

    // Enemies
    private final EnemyGenerator enemyGenerator = new EnemyGenerator(this);
    private static final int GUARDS_COUNT = 8;
    private static final int DOGS_COUNT = 2;
    private static final int CAMERA_COUNT = 4;

    // In-Game UI
    public int introFade = 255;
    private Container overlayContainer = new Container(this);
    private Label timerLabel = new Label(ColorPalette.WHITE,true);
    private Label valuablesLabel = new Label(ColorPalette.YELLOW,true);
    private Label scoreLabel = new Label(ColorPalette.LIGHT_PURPLE,true);

    //Music Components
    private Music music = new Music();
    private SFX sfx = new SFX();

    // Timer
    private Timer timer;
    public static final double LEVEL_TIME = 61.0;
    private int lastTime = 61;

    // Thread
    private Thread gameThread;
    private GameOverOverlay gameOverOverlay;

    // App reference and font resource
    public App app;
    public Font font;

    /**
     * Constructs the GamePanel, setting up game dimensions, components, resources, and input listeners.
     *
     * @param app the main application instance
     */
    public GamePanel(App app) {
        this.app = app;
        this.setSize(SCREEN_SIZE.x, SCREEN_SIZE.y);
        this.setBackground(ColorPalette.PURPLE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);
        this.loadResources();
        this.loadOverlayContainer();
    }

    /**
     * Loads the overlay container with the specified labels and sets their font.
     * This method initializes the overlay container by adding the timer, valuables, and score labels.
     */
    private void loadOverlayContainer() {
        overlayContainer.setFont(font);
        overlayContainer.addLabel(timerLabel);
        overlayContainer.addLabel(valuablesLabel);
        overlayContainer.addLabel(scoreLabel);
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
            if (lastTime <= LEVEL_TIME && lastTime > 30 && !music.isPlaying("60")) {
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
                timerLabel.setColor(ColorPalette.RED);
                enemyGenerator.addEnemySpeed(1);
                player.addSpeed(1);
            }
            if (lastTime <= 10) sfx.play("time_tick");
            lastTime = currentTime;
        }
        
    }

    private double _fadeLerp = 0.5;
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

        // Draw Tiles and Entities
        tileGenerator.draw(g2);

        // Draw Time and Score Overlay Container
        String timeString = String.format("Time Left: %ds", timer.getSecondsLeft());
        String valuablesString = String.format("Valuables Left: %d",
            rewardGenerator.generator.getCountByType(Valuable.class));
        String scoreText = String.format("Score: %04d",player.getScore());
        overlayContainer.getLabel(0).setText(timeString);
        overlayContainer.getLabel(1).setText(valuablesString);
        overlayContainer.getLabel(2).setText(scoreText);
        overlayContainer.draw(g2);

        if (sfx.isPlaying("alarm")) {
            g2.setColor(new Color(1.0f, 0.0f, 0.0f, 0.3f));
            g2.fillRect(0,0,SCREEN_SIZE.x,SCREEN_SIZE.y);
        }

        if (introFade > 0) {
            timer.start();
            
            g2.setColor(new Color(89, 81, 120,introFade));
            g2.fillRect(0,0,SCREEN_SIZE.x,SCREEN_SIZE.y);
            introFade = (int)Math.round((double)introFade - 8.0 * _fadeLerp);
        }
        g2.dispose();
    }
}
