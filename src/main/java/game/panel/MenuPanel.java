package game.panel;

import game.App;
import game.utils.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * A custom JPanel that displays the main menu of the game, including buttons to start the game,
 * view best scores, and exit. The panel includes a background image, custom font, and button styling.
 */
public class MenuPanel extends JPanel implements Runnable {
    private BufferedImage background; // Background image for the menu
    private Font font; // Custom font for menu buttons and title
    public Thread menuThread;

    /**
     * Constructs the MenuPanel with specified action listeners for each button.
     *
     * @param startListener the ActionListener for the start button
     * @param bestScoresListener the ActionListener for the best scores button
     * @param exitListener the ActionListener for the exit button
     */
    public MenuPanel(App app, ActionListener startListener, ActionListener bestScoresListener, ActionListener instructionListener, ActionListener exitListener) {
        this.setLayout(null);
        background = ResourceLoader.loadSpritesheet("parallax");
        font = ResourceLoader.loadFont(24);
        if (!app.music.isPlaying("music")) app.music.play("music");

        JButton buttonStart = new JButton("START");
        styleButton(buttonStart);
        buttonStart.setBounds(1280/2-100, 300, 200, 50);
        buttonStart.addActionListener(startListener);

        JButton buttonBestScores = new JButton("HIGH SCORES");
        styleButton(buttonBestScores);
        buttonBestScores.setBounds(1280/2-100, 370, 200, 50);
        buttonBestScores.addActionListener(bestScoresListener);

        JButton buttonInstructions = new JButton("TUTORIAL");
        styleButton(buttonInstructions);
        buttonInstructions.setBounds(1280/2-100, 440, 200, 50);
        buttonInstructions.addActionListener(instructionListener);

        JButton buttonExit = new JButton("EXIT");
        styleButton(buttonExit);
        buttonExit.setBounds(1280/2-100, 510, 200, 50);
        buttonExit.addActionListener(exitListener);

        this.add(buttonStart);
        this.add(buttonBestScores);
        this.add(buttonInstructions);
        this.add(buttonExit);

        startMenuThread();
    }

    public void startMenuThread() {
        menuThread = new Thread(this);
        menuThread.start();
    }
    /**
     * Starts the menu thread, which handles the parallax effect of the background.
     */
    @Override
    public void run() {
        while (menuThread != null) {
            parallaxCounter -= 1;
            if (parallaxCounter <= getWidth()*-2) {
                parallaxCounter = 0;
            }
            repaint();
            try {
                Thread.sleep(16); // Approximately 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Applies custom styling to a JButton, including font, background color, and foreground color.
     *
     * @param button the JButton to style
     */
    private void styleButton(JButton button) {
        button.setFont(font);
        button.setBackground(ColorPalette.GREY);
        button.setForeground(ColorPalette.WHITE);
        button.setFocusPainted(false);
    }

    private int parallaxCounter = 0; // X value offset of background
    /**
     * Paints the background and title text for the menu, including a gradient effect.
     *
     * @param g the Graphics object used to paint the component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(background, parallaxCounter, 0, getWidth()*3, getHeight(), this);
        //g2.drawImage(background, parallaxCounter + getWidth()*3, 0, getWidth(), getHeight(), this);
        g2.setFont(font.deriveFont(Font.PLAIN, 64));
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        g2.drawString("Vault Escape", 1280/2-172, 210);
        GradientPaint gradient = new GradientPaint(100, 0, new Color(220, 180, 60), 50, 400, new Color(154, 145, 169));
        g2.setPaint(gradient);
        g2.drawString("Vault Escape", 1280/2-172, 200);
    }
}
