package vaultescape;
// import java.awt.*;
// import java.awt.event.*;
import javax.swing.JFrame;

import vaultescape.map.GamePanel;

public class App extends JFrame{
    public static void main(String[] args) {
        App window = new App();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        window.setSize(1024, 768); // 4:3 game resolution
        window.setResizable(false); //user cannot resize
        window.setTitle("VaultEscape"); //title of the window

        GamePanel panel = new GamePanel(); // object for game panel
        window.add(panel); // Adding the board
        
        panel.startGameThread(); //start the tic

        window.setVisible(true); //visibility of window
        window.revalidate(); // Revalidate the window
        window.repaint();    // Repaint the window

        
    }
}