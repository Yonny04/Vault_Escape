package vaultescape.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class InstructionsPanel extends JPanel{
    private Image background;
    private Font font;

    /**
     * Constructs a InstructionsOverlay with instructions how to play the game.
     *
     */
    public InstructionsPanel(ActionListener backListener) {
        this.setLayout(null);
        loadResources();

        JLabel instructionsLabel = new JLabel("Use WASD to move!");
        styleLabel(instructionsLabel);
        instructionsLabel.setBounds(410, 250, 400, 100);
        this.add(instructionsLabel);

        JLabel goalsLabel = new JLabel("Collect 5 rewards and head to exit to win!");
        styleLabel(goalsLabel);
        goalsLabel.setBounds(150, 300, 1000, 100);
        this.add(goalsLabel);

        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.setBounds(500, 500, 200, 50);
        backButton.addActionListener(backListener);
        this.add(backButton);
    }

    /**
     * Loads the custom font for the overlay from resources.
     * This font is used for displaying text in the overlay.
     */
    private void loadResources() {
        try {
            InputStream fontStream = getClass().getResourceAsStream("/ui/royal-intonation.ttf");
            InputStream backgroundStream = getClass().getResourceAsStream("/menu/background_city.png");
            background = ImageIO.read(backgroundStream);
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
