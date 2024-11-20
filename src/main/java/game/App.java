package game;

import game.audio.*;
import game.panel.*;

import javax.swing.*;

import java.awt.event.*;

/**
 * Main application class for VaultEscape, handling the primary game window and
 * navigation between different panels 
 * (menu, game, best scores)
 */
public class App extends JFrame {

    public GamePanel gp; // The main game panel
    public MenuPanel mp; // The main menu panel
    private InstructionsPanel ip; // The instructions panel
    private BestScoresPanel bsp; // The best scores panel

    public int currentLevel = 1;
    public final int MAX_LEVEL = 4;
    public int currentScore = 0;

    public Music music = new Music();
    public SFX sfx = new SFX();

    /**
     * Constructs the main application window, initializing the menu panel and setting
     * up the JFrame properties.
     */
    public App() {
        App frame = this;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1280, 768);
        setTitle("Vault Escape");
        setResizable(false);
        setLocationRelativeTo(null);
        
        mp = new MenuPanel(this,
            e -> {startGame();},  // Start game action listener
            e -> {showBestScores();}, // Show best scores action listener
            e -> {showInstructions();}, // Show instructions action listener
            e -> {System.exit(0);} // Exit action listener
        );
        bsp = new BestScoresPanel(e -> backToMenu());
        ip = new InstructionsPanel(e -> backToMenu());

        setContentPane(mp);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(JOptionPane.showConfirmDialog(frame, "Are you sure?") 
                        == JOptionPane.OK_OPTION){
                    frame.setVisible(false);
                    frame.dispose();
                    if (gp != null && !gp.isRunning()) addHighScore();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Starts the game by initializing the game panel, setting it as the content pane,
     * and starting the game thread.
     */
    public void startGame() {
        if (mp.menuThread != null) mp.menuThread = null;
        sfx.play("confirm");
        music.stop();
        gp = new GamePanel(this,currentLevel);
        setContentPane(gp);
        revalidate();
        repaint();
        gp.requestFocus();
        gp.startGameThread();
    }

    public void restartGame() {
        addHighScore();
        currentLevel = 1;
        currentScore = 0;
        startGame();
    }
    /**
     * Method to notify the App of game completion and update the best scores.
     */
    public void nextLevel() {
        currentLevel++;
        startGame();
    }

    /**
     * Method to notify the App of game completion and update the best scores.
     *
     * @param score the final score to add to BestScoresPanel
     */
    public void addHighScore() {
        bsp.addNewScore(currentScore);     
    }

    public void addLevelScore() {
        currentScore += gp.getLevelScore() + gp.getTimeScore();
    }

    /**
     * Displays the best scores panel.
     */
    private void showBestScores() {
        sfx.play("select");
        bsp.updateTable();
        setContentPane(bsp);
        revalidate();
        repaint();
    }

    private void showInstructions() {
        sfx.play("select");
        setContentPane(ip);
        revalidate();
        repaint();
    }

    /**
     * Returns to the main menu by setting the menu panel as the content pane.
     */
    public void backToMenu() {
        if (mp.menuThread == null) mp.startMenuThread();
        sfx.play("back");
        addHighScore();
        currentLevel = 1;
        currentScore = 0;
        setContentPane(mp);
        revalidate();
        repaint();
    }

    /**
     * Main method to launch the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new App();
    }
}
