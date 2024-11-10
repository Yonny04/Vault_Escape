package vaultescape.ui;

import vaultescape.entity.character.Player;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;

/**
 * A JPanel that displays the Game Over overlay, showing the final score and options
 * to restart the game, return to the main menu, or exit the application.
 */
public class GameOverOverlay extends JPanel {
    private Font font;
    /**
     * Constructs a GameOverOverlay with specified action listeners for buttons.
     *
     * @param player the Player object containing the player's score
     * @param restartListener the ActionListener for the restart button
     * @param menuListener the ActionListener for the main menu button
     * @param exitListener the ActionListener for the exit button
     */
    public GameOverOverlay(Player player, boolean isWin, int timeLeft, int totalScore, ActionListener restartListener, ActionListener menuListener, ActionListener exitListener) {
        this.setLayout(null);
        loadResources();

        String title = isWin ? "Victory!" : "Time is up!";

        JLabel gameOverLabel = new JLabel(title);
        styleLabel(gameOverLabel);
        gameOverLabel.setBounds(450, 50, 300, 100);
        this.add(gameOverLabel);

        JLabel scoreLabel = new JLabel("Your score: " + player.getScore() );
        styleLabel(scoreLabel);
        scoreLabel.setBounds(410, 150, 400, 100);
        this.add(scoreLabel);

        int bonusScore = isWin ? (timeLeft / 50 + 200) : 0;
        JLabel bonusScoreLabel = new JLabel("Time Bonus: " + bonusScore);
        styleLabel(bonusScoreLabel);
        bonusScoreLabel.setBounds(310, 250, 600, 100);
        this.add(bonusScoreLabel);

        JLabel totalScoreLabel = new JLabel("Total score: " + totalScore);
        styleLabel(totalScoreLabel);
        totalScoreLabel.setBounds(410, 350, 400, 100);
        this.add(totalScoreLabel);

        JButton restartButton = new JButton("Restart");
        styleButton(restartButton);
        restartButton.setBounds(500, 500, 200, 50);
        restartButton.addActionListener(restartListener);
        this.add(restartButton);

        JButton menuButton = new JButton("Main Menu");
        styleButton(menuButton);
        menuButton.setBounds(500, 550, 200, 50);
        menuButton.addActionListener(menuListener);
        this.add(menuButton);

        JButton exitButton = new JButton("Exit");
        styleButton(exitButton);
        exitButton.setBounds(500, 600, 200, 50);
        exitButton.addActionListener(exitListener);
        this.add(exitButton);
    }


    /**
     * Loads the custom font for the overlay from resources.
     * This font is used for displaying text in the overlay.
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
     * Styles a JButton with custom font and colors.
     *
     * @param button the JButton to style
     */
    private void styleButton(JButton button) {
        button.setFont(font);
        button.setBackground(new Color(82, 45, 61));
        button.setForeground(Color.GRAY);
        button.setFocusPainted(false);
    }

    /**
     * Styles a JLabel with custom font and colors, centering the text.
     *
     * @param label the JLabel to style
     */
    private void styleLabel(JLabel label) {
        label.setFont(font.deriveFont(Font.BOLD, 48));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER); // Center the label text
    }

    /**
     * Paints the background of the overlay with a semi-transparent color,
     * covering the entire area to create a modal effect.
     *
     * @param g the Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 100)); // Semi-transparent black
        g.fillRect(400, 100, 400, 500); // Cover the entire area
    }
}
