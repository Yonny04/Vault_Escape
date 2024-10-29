package vaultescape;
// import java.awt.*;
// import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import vaultescape.map.GamePanel;
import vaultescape.ui.MenuPanel;

public class App extends JFrame{

    private GamePanel gp;
    private MenuPanel mp;
    public App() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 768); 
        setResizable(false);
        setTitle("VaultEscape");
        setLocationRelativeTo(null);  
        gp = new GamePanel();
        mp = new MenuPanel(
            e -> startGame(),  
            e -> System.exit(0)
        );
        setContentPane(mp);
        setVisible(true);
    }
    private void startGame(){
        setContentPane(gp);
        revalidate();
        repaint();
        gp.requestFocus();
        gp.startGameThread();
    }
    public static void main(String[] args) {
        new App();
    }



   /*  public static void main(String[] args) {
        App window = new App();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        window.setSize(1280, 768); // 4:3 game resolution
        window.setResizable(false); //user cannot resize
        window.setTitle("VaultEscape"); //title of the window

        GamePanel panel = new GamePanel(); // object for game panel
        window.add(panel); // Adding the board
        
        panel.startGameThread(); //start the tic

        window.setVisible(true); //visibility of window
        window.revalidate(); // Revalidate the window
        window.repaint();    // Repaint the window

        
    }*/
}