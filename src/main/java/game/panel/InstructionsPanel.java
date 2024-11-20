package game.panel;

import game.App;
import game.object.Rect;
import game.ui.Container;
import game.ui.Container.Alignment;
import game.ui.Label;
import game.utils.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class InstructionsPanel extends JPanel{
    private BufferedImage background;
    private Font font;
    private Container container = new Container();
    public App app;

    int instructionCount = 6;
    String[] instructions = {
        "Use WASD to move.",
        "Collect all the valuables, then escape!",
        "GUARDS've had a long shift. Don't disturb their path.",
        "DOGS do bite. Come too close and they'll chase you.",
        "Sightings from CAMERAS increase speed. Avoid the light.",
        "Dont touch LASERS. They hurt... I think.",
    };
    Color[] instructionColors = {
        ColorPalette.WHITE,ColorPalette.YELLOW,ColorPalette.BLUE,
        ColorPalette.RED,ColorPalette.LIGHT_PURPLE,ColorPalette.MAGENTA,};

    /**
     * Constructs a InstructionsOverlay with instructions how to play the game.
     *
     */
    public InstructionsPanel(ActionListener backListener) {
        this.setLayout(null);
        font = ResourceLoader.loadFont(32);

        background = ResourceLoader.loadSpritesheet("parallax").getSubimage(0, 0, 360, 180);
        
        container.setFont(36);
        for (int i = 0; i < instructionCount; i++) {
            Label instructionLabel = new Label(instructionColors[i], true);
            container.addLabel(instructionLabel);
        }
        container.setAlignment(Alignment.CENTER, Alignment.CENTER);
        container.addPanel(new Rect(1280/2-64*8, 0, 64*16-16, 768));
        container.separation = 64;
        JButton backButton = new JButton("BACK TO MENU");
        styleButton(backButton);
        backButton.setBounds(1280/2-125, 768-150, 250, 50);
        backButton.addActionListener(backListener);
        this.add(backButton);
    }

    /**
     * Styles a JButton with custom font and colors.
     *
     * @param button the JButton to style
     */
    private void styleButton(JButton button) {
        button.setFont(font);
        button.setBackground(ColorPalette.GREY);
        button.setForeground(ColorPalette.WHITE);
        button.setFocusPainted(false);
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
        for (int i=0; i<instructionCount; i++) {
            container.getLabel(i).setText(instructions[i]);
        }
        g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        container.draw(g2);
    }
}
