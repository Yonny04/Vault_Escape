package vaultescape;
import javax.swing.JFrame;

import vaultescape.map.GamePanel;
import vaultescape.ui.BestScoresPanel;
import vaultescape.ui.MenuPanel;

public class App extends JFrame{

    private GamePanel gp;
    private MenuPanel mp;
    private BestScoresPanel bsp;

    public App() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 768); 
        setResizable(false);
        setTitle("VaultEscape");
        setLocationRelativeTo(null);
        mp = new MenuPanel(
            e -> startGame(),  
            e -> showBestScores(),
            e -> System.exit(0)
        );
        bsp = new BestScoresPanel(e -> backToMenu());
        setContentPane(mp);
        setVisible(true);
    }

    private void startGame(){

        gp = new GamePanel(this);
        setContentPane(gp);
        revalidate();
        repaint();
        gp.requestFocus();
        gp.startGameThread();
        
    }

    private void showBestScores(){
        setContentPane(bsp);
        revalidate();
        repaint();
    }

    public void backToMenu() {
        setContentPane(mp);
        revalidate();
        repaint();
    }
    public static void main(String[] args) {
        new App();
    }
}