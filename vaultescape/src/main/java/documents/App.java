package documents;
import javax.swing.JFrame;

public class App extends JFrame{
    public static void main(String[] args) {
        App window = new App();

        window.setSize(1000,1000);
        window.setVisible(true);

        //add swing objects
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}