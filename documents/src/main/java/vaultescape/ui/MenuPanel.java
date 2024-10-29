package vaultescape.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel{
    private Image background;

    public MenuPanel(ActionListener startListener, ActionListener bestScoresLestener, ActionListener exitListener){
        this.setLayout(null);
        background = new ImageIcon(getClass().getClassLoader().getResource("menu/background.png")).getImage();
        
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
    private void styleButton(JButton button){
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
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
