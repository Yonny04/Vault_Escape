package vaultescape;

import javax.swing.JFrame;
import vaultescape.map.GamePanel;
import vaultescape.ui.BestScoresPanel;
import vaultescape.ui.MenuPanel;

/**
 * Main application class for VaultEscape, handling the primary game window and
 * navigation between different panels 
 * (menu, game, best scores)
 */
public class App extends JFrame {

    private GamePanel gp; // The main game panel
    private MenuPanel mp; // The main menu panel
    private BestScoresPanel bsp; // The best scores panel

    /**
     * Constructs the main application window, initializing the menu panel and setting
     * up the JFrame properties.
     */
    public App() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 768);
        setResizable(false);
        setTitle("VaultEscape");
        setLocationRelativeTo(null);
        
        mp = new MenuPanel(
            e -> startGame(),  // Start game action listener
            e -> showBestScores(), // Show best scores action listener
            e -> System.exit(0) // Exit action listener
        );
        bsp = new BestScoresPanel(e -> backToMenu());
        setContentPane(mp);
        setVisible(true);
    }

    /**
     * Starts the game by initializing the game panel, setting it as the content pane,
     * and starting the game thread.
     */
    public void startGame() {
        gp = new GamePanel(this);
        setContentPane(gp);
        revalidate();
        repaint();
        gp.requestFocus();
        gp.startGameThread();
    }

    /**
     * Method to notify the App of game completion and update the best scores.
     *
     * @param score the final score to add to BestScoresPanel
     */
    public void updateBestScoreAfterGame(int score) {
        bsp.addNewScore(score);     
    }

    /**
     * Displays the best scores panel.
     */
    private void showBestScores() {
        bsp.updateTable();
        setContentPane(bsp);
        revalidate();
        repaint();
    }

    /**
     * Returns to the main menu by setting the menu panel as the content pane.
     */
    public void backToMenu() {
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
