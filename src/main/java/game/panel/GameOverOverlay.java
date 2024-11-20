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

/**
 * A JPanel that displays the Game Over overlay, showing the final score and options
 * to restart the game, return to the main menu, or exit the application.
 */
public class GameOverOverlay extends JPanel {
    private App app;
    private Container container;
    /**
     * Constructs a GameOverOverlay with specified action listeners for buttons.
     *
     * @param app the main application object
     * @param isWin true if the game was won, false if the game was lost
     * @param levelListener the ActionListener for the restart/next level button
     * @param menuListener the ActionListener for the main menu button
     * @param exitListener the ActionListener for the exit button
     */
    public GameOverOverlay(App app, boolean isWin, ActionListener levelListener, ActionListener menuListener, ActionListener exitListener) {
        this.app = app;
        this.setLayout(null);
        
        String titleText = isWin ? "YOU ESCAPED!" : "ARRESTED!";
        Label titleLabel = new Label(isWin ? ColorPalette.GREEN : ColorPalette.RED, true);
        Label lastScoreLabel = new Label(ColorPalette.PURPLE, true);
        Label levelScoreLabel = new Label(ColorPalette.LIGHT_PURPLE, true);
        Label timeBonusLabel = new Label(ColorPalette.WHITE, true);
        Label currentScoreLabel = new Label(ColorPalette.YELLOW, true);
        container = new Container();
        container.setAlignment(Alignment.CENTER, Alignment.CENTER);
        container.setFont(32);
        container.setSeparation(64);
        container.addPanel(new Rect(1280/2-64*4, 768/2-64*5, 64*8-16, 64*9));
        container.addLabel(titleLabel);
        container.getLabel(0).setFont(48);
        container.addLabel(lastScoreLabel);
        container.addLabel(levelScoreLabel);
        container.addLabel(timeBonusLabel);
        container.addLabel(currentScoreLabel);
        container.getLabel(0).setText(titleText);
        
        String levelButtonText = isWin ? "NEXT LEVEL" : "RESTART";
        if (app.currentLevel < app.MAX_LEVEL) {
            JButton levelButton = new JButton(levelButtonText);
            styleButton(levelButton);
            levelButton.setBounds(1280/2-125, 450, 250, 50);
            levelButton.addActionListener(levelListener);
            this.add(levelButton);
        }

        JButton menuButton = new JButton("BACK TO MENU");
        styleButton(menuButton);
        menuButton.setBounds(1280/2-125, 500, 250, 50);
        menuButton.addActionListener(menuListener);
        this.add(menuButton);

        JButton exitButton = new JButton("EXIT");
        styleButton(exitButton);
        exitButton.setBounds(1280/2-125, 550, 250, 50);
        exitButton.addActionListener(exitListener);
        this.add(exitButton);
    }

    /**
     * Styles a JButton with custom font and colors.
     *
     * @param button the JButton to style
     */
    private void styleButton(JButton button) {
        button.setFont(ResourceLoader.loadFont(32));
        button.setBackground(ColorPalette.GREY);
        button.setForeground(ColorPalette.WHITE);
        button.setFocusPainted(false);
    }

    private int screenScore = 0; 
    /**
     * Paints the background of the overlay with a semi-transparent color,
     * covering the entire area to create a modal effect.
     *
     * @param g the Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        String lastScoreText = String.format("LAST SCORE: %06d", app.currentScore - app.gp.getTimeScore() -app.gp.getLevelScore());
        String levelScoreText = String.format("LEVEL SCORE: +%d", app.gp.getLevelScore());
        String timeBonusText = String.format("TIME BONUS: +%d", app.gp.getTimeScore());
        String currentScoreText = String.format("CURRENT SCORE: %06d", app.currentScore);
        container.getLabel(1).setText(lastScoreText);
        container.getLabel(2).setText(levelScoreText);
        container.getLabel(3).setText(timeBonusText);
        container.getLabel(4).setText(currentScoreText);
        container.draw((Graphics2D)g);
    }
}
