package vaultescape;
// import java.awt.*;
// import java.awt.event.*;
import javax.swing.JFrame;

public class App extends JFrame{
    public static void main(String[] args) {
        App window = new App();
        window.setVisible(true); //visibility of window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(1980, 1080); //setting the height and width
        window.setResizable(false); //user cannot resize
        window.setTitle("VaultEscape"); //title of the window

        GamePanel gp = new GamePanel(); // object for game panel

        window.add(gp); // Adding the board
        gp.startGameThread(); //start the tic

    }
}