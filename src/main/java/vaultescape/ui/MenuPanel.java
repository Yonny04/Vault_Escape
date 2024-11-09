package vaultescape.ui;

import vaultescape.audio.Music;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;

/**
 * A custom JPanel that displays the main menu of the game, including buttons to start the game,
 * view best scores, and exit. The panel includes a background image, custom font, and button styling.
 */
public class MenuPanel extends JPanel {
    private Image background; // Background image for the menu
    private Font font; // Custom font for menu buttons and title
    public Music music = new Music();

    /**
     * Constructs the MenuPanel with specified action listeners for each button.
     *
     * @param startListener the ActionListener for the start button
     * @param bestScoresListener the ActionListener for the best scores button
     * @param exitListener the ActionListener for the exit button
     */
    public MenuPanel(ActionListener startListener, ActionListener bestScoresListener, ActionListener instructionListener, ActionListener exitListener) {
        this.setLayout(null);
        this.loadResources();
        music.play("music");

        JButton buttonStart = new JButton("Start");
        styleButton(buttonStart);
        buttonStart.setBounds(500, 300, 200, 50);
        buttonStart.addActionListener(startListener);

        JButton buttonBestScores = new JButton("Best Scores");
        styleButton(buttonBestScores);
        buttonBestScores.setBounds(500, 370, 200, 50);
        buttonBestScores.addActionListener(bestScoresListener);

        JButton buttonInstructions = new JButton("Instructions");
        styleButton(buttonInstructions);
        buttonInstructions.setBounds(500, 440, 200, 50);
        buttonInstructions.addActionListener(instructionListener);

        JButton buttonExit = new JButton("Exit");
        styleButton(buttonExit);
        buttonExit.setBounds(500, 510, 200, 50);
        buttonExit.addActionListener(exitListener);

        this.add(buttonStart);
        this.add(buttonBestScores);
        this.add(buttonInstructions);
        this.add(buttonExit);
    }

    /**
     * Loads the custom font and background image for the menu panel.
     */
    private void loadResources() {
        try {
            InputStream fontStream = getClass().getResourceAsStream("/ui/royal-intonation.ttf");
            InputStream backgroundStream = getClass().getResourceAsStream("/menu/background.png");
            background = ImageIO.read(backgroundStream);
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies custom styling to a JButton, including font, background color, and foreground color.
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
     * Paints the background and title text for the menu, including a gradient effect.
     *
     * @param g the Graphics object used to paint the component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g2.setFont(font.deriveFont(Font.PLAIN, 64));
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        g2.drawString("Vault Escape", 420, 210);
        GradientPaint gradient = new GradientPaint(100, 0, new Color(220, 180, 60), 50, 400, new Color(154, 145, 169));
        g2.setPaint(gradient);
        g2.drawString("Vault Escape", 420, 200);
    }
}
