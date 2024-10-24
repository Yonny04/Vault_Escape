package vaultescape;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class App extends JFrame{
    public static void main(String[] args) {
        App window = new App();

        window.setSize(500,500);
        window.setTitle("VaultEscape");
        
        
        window.setVisible(true);

        //add swing objects
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        
    }
}