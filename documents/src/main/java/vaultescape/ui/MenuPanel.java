package vaultescape.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
    private Image background;
    private Font font;

    public MenuPanel(ActionListener startListener, ActionListener bestScoresLestener, ActionListener exitListener){
        this.setLayout(null);
        this.loadResources();

        JButton buttonStart = new JButton("Start");
        styleButton(buttonStart);
        buttonStart.setBounds(500, 300, 200, 50);  
        buttonStart.addActionListener(startListener); 

        JButton buttonBestScores = new JButton("Best Scores");
        styleButton(buttonBestScores);
        buttonBestScores.setBounds(500, 370, 200, 50);  
        buttonBestScores.addActionListener(bestScoresLestener); 

        JButton buttonExit = new JButton("Exit");
        styleButton(buttonExit);
        buttonExit.setBounds(500, 440, 200, 50); 
        buttonExit.addActionListener(exitListener); 
        
        this.add(buttonStart);
        this.add(buttonBestScores);
        this.add(buttonExit);
    }
    private void loadResources() {
        try {
            InputStream fontStream = getClass().getResourceAsStream("/ui/royal-intonation.ttf");
            InputStream backgroundStream = getClass().getResourceAsStream("/menu/background_city.png");
            background = ImageIO.read(backgroundStream);
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 16);
        } catch (Exception e) {e.printStackTrace();}
    }
    private void styleButton(JButton button){
        button.setFont(font);
        button.setBackground(new Color(82, 45, 61));
        button.setForeground(Color.GRAY);
        button.setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);  
    Graphics2D g2 = (Graphics2D) g;
    g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
}
}
