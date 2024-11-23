package game.panel;

import game.App;
import game.audio.*;
import game.level.*;
import game.object.Vector;
import game.tile.entity.character.Player;
import game.tile.entity.character.enemy.*;
import game.tile.entity.reward.Valuable;
import game.ui.Container;
import game.ui.Label;
import game.utils.*;

import javax.swing.JPanel;

import java.awt.*;

/**
 * Represents the main game panel where all game elements are drawn and updated, such as the player, enemies,
 * rewards, and timer. Manages the game loop, input, and screen dimensions.
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen Properties
    public final Vector SCREEN_DIM = new Vector(20,12); // Screen Size (in tiles)
    public final Vector SCREEN_SIZE = SCREEN_DIM.toGlobal(); // Screen Size (in pixels)

    // Frames per second for game loop
    public final int FPS = 60;

    public Level level;
    private int levelScore = 0;

    // Game components
    private final KeyDetector keyh;
    private TileManager tileManager;
    private final Player player;

    // Generators
    private final RewardGenerator rewardGenerator;
    private final EnemyGenerator enemyGenerator;

    // In-Game UI
    public int introFade = 300;
    private Container overlayContainer = new Container();
    private Label levelLabel = new Label(ColorPalette.WHITE,true);
    private Label timerLabel = new Label(ColorPalette.WHITE,true);
    private Label valuablesLabel = new Label(ColorPalette.YELLOW,true);
    private Label scoreLabel = new Label(ColorPalette.LIGHT_PURPLE,true);

    // Timer
    private Timer timer;

    // Thread
    private Thread gameThread;
    private GameOverOverlay gameOverOverlay;

    // App reference and font resource
    public App app;

    /**
     * Constructs the GamePanel, setting up game dimensions, components, resources, and input listeners.
     *
     * @param app the main application instance
     */
    public GamePanel(App app, int levelNumber) {
        this.app = app;
        keyh = new KeyDetector();
        rewardGenerator = new RewardGenerator(this);
        enemyGenerator = new EnemyGenerator(this);
        tileManager = new TileManager(this);
        level = new Level(this, levelNumber);
        player = new Player(this, new Vector(0,0), keyh);
        lastTime = (int)level.TIME_LIMIT;

        setSize(SCREEN_SIZE.x, SCREEN_SIZE.y);
        setBackground(ColorPalette.PURPLE);
        setDoubleBuffered(true);
        addKeyListener(keyh);
        setFocusable(true);
        loadOverlayContainer();
    }

    /**
     * Loads the overlay container with the specified labels and sets their font.
     * This method initializes the overlay container by adding the timer, valuables, and score labels.
     */
    private void loadOverlayContainer() {
        overlayContainer.setFont(32);
        overlayContainer.addLabel(timerLabel);
        overlayContainer.addLabel(valuablesLabel);
        overlayContainer.addLabel(scoreLabel);
    }

    /**
     * Retrieves the tile generator instance.
     *
     * @return the tile generator for managing game map tiles
     */
    public TileManager getTileManager() {return tileManager;}

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
    public Music getMusic() {return app.music;}

    /**
     * Gets the sound effects (SFX) object.
     * @return the SFX object
     */
    public SFX getSFX() {return app.sfx;}

    /**
     * Starts the game by initializing the game panel, setting it as the content pane,
     * @return the game panel
     */
    public boolean isRunning() {return gameThread != null;}

    /**
     * Stops the game upon escaping the vault
     */
    public void completeGame(boolean isWin) {
        gameThread = null;
        getMusic().stop();
        if (isWin) getSFX().play("game_complete");
        else {
            getSFX().play("game_over"); 
            getSFX().play("alarm"); 
            getSFX().loop(2);
        }
        app.addLevelScore();
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
        if (gameOverOverlay != null) app.remove(gameOverOverlay);

        // Create a new GameOverOverlay instance
        gameOverOverlay = new GameOverOverlay(app, isWin,
                e -> {hideGameOverScreen();
                    if (isWin) app.nextLevel();
                    else app.restartGame();},
                e -> {hideGameOverScreen();
                    app.backToMenu();
                    getMusic().play("music");},
                e -> {app.addHighScore();System.exit(0);}
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
        gameOverOverlay.setVisible(false);
        gameOverOverlay.setVisible(true);
    }

    /**
     * Hides the Game Over screen overlay by setting its visibility to false.
     * This effectively removes the overlay from view without removing it from
     * the application frame, allowing it to be shown again later if needed.
     */
    private void hideGameOverScreen() {gameOverOverlay.setVisible(false);}

    /**
     * Starts the game thread, initializing the timer and generating enemies and rewards.
     */
    public void startGameThread() {
        timer = new Timer(level.TIME_LIMIT);
        enemyGenerator.spawn(Guard.class, level.GUARDS_COUNT);
        enemyGenerator.spawn(Dog.class, level.DOGS_COUNT);
        enemyGenerator.spawn(Camera.class, level.CAMERA_COUNT);
        enemyGenerator.spawn(Laser.class, level.LASER_COUNT);

        rewardGenerator.spawn(Valuable.class, level.VALUABLES_COUNT);

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

        while (isRunning()) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {}
            
            if (timer.isTimeUp()) completeGame(false);
        }
    }

    /**
     * Calculates and returns the final score for the player.
     * The final score is computed based on the time left and the player's score.
     * 
     * @return the final score as an integer
     */
    public int getTotalScore() {
        return getTimeScore() + getLevelScore();
    }

    public int getTimeScore() {
        return (int)(getTimer().getSecondsLeft()*30)+100;
    }

    /**
     * Retrieves the current score of the player.
     *
     * @return the player's score
     */
    public int getLevelScore() {return levelScore;}

    /**
     * Adds a specified number of points to the player's score.
     *
     * @param points the number of points to add to the score
     */
    public void addLevelScore(int points) {levelScore += points;}

    /**
     * Retrieves the timer instance.
     *
     * @return the timer managing the game's countdown
     */
    public Timer getTimer() {return timer;}

    /**
     * Retrieves the player instance.
     *
     * @return the player entity
     */
    public Player getPlayer() {return player;}

    /**
     * Updates the state of the player, rewards, and enemies. Checks if the timer has expired.
     */
    private int lastTime;
    public void update() {
        player.update();
        rewardGenerator.update();
        enemyGenerator.update();

        // Gametime Logic (music changes + speed increase + countdown)
        int currentTime = (int)timer.getTimeLeft() / 1000;
        if (lastTime != currentTime) {
            if (lastTime <= level.TIME_LIMIT && lastTime > level.TIME_LIMIT/2 
                    && !getMusic().isPlaying("60")) {
                getMusic().play("60");
            }
            else if (lastTime <= level.TIME_LIMIT/3 && lastTime > level.TIME_LIMIT/4 
                    && !getMusic().isPlaying("30")) {
                getMusic().play("30");
                getSFX().play("time_tick");
                enemyGenerator.addEnemySpeed(1);
            }
            else if (lastTime <= level.TIME_LIMIT/5 && lastTime > 0 
                    && !getMusic().isPlaying("15")) {
                getMusic().play("15");
                getSFX().play("time_tick");
                timerLabel.setColor(ColorPalette.RED);
                enemyGenerator.addEnemySpeed(1);
                player.addSpeed(1);
            }
            if (lastTime <= 10) getSFX().play("time_tick");
            lastTime = currentTime;
        }
        
    }

    private double _fadeLerp = 0.5;
    private int scoreScreen = 0;
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
        tileManager.draw(g2);

        // Draw Time and Score Overlay Container

        if (scoreScreen < levelScore) scoreScreen++;
        String timeString = String.format("Time Left: %ds", timer.getSecondsLeft());
        int valuablesCount = rewardGenerator.generator.getCountByType(Valuable.class);
        String valuablesString = String.format("Valuables Left: %02d",
            valuablesCount);
        if (valuablesCount == 0) valuablesString = "All Valuables Collected! Escape!";
        String scoreText = String.format("Level Score: %06d",scoreScreen);
        overlayContainer.getLabel(0).setText(timeString);
        overlayContainer.getLabel(1).setText(valuablesString);
        overlayContainer.getLabel(2).setText(scoreText);
        overlayContainer.draw(g2);

        if (app.sfx.isPlaying("alarm") || app.sfx.isPlaying("laser") ) {
            g2.setColor(new Color(1.0f, 0.0f, 0.0f, 0.3f));
            g2.fillRect(0,0,SCREEN_SIZE.x,SCREEN_SIZE.y);
        }

        if (introFade > 0) {
            g2.setColor(new Color(89, 81, 120,Math.min(introFade,255)));
            g2.fillRect(0,0,SCREEN_SIZE.x,SCREEN_SIZE.y);
            introFade = (int)Math.round((double)introFade - 8.0 * _fadeLerp);

            levelLabel.setText(String.format("LEVEL %02d",level.levelNumber));
            levelLabel.draw(g2, new Vector(SCREEN_SIZE.x / 2 - 64, SCREEN_SIZE.y / 2));
        }
        g2.dispose();
    }
}
