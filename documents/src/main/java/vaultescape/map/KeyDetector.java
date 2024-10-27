package vaultescape.map;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyDetector implements KeyListener{

    public boolean w,a,s,d;
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    //Key pressed
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W)   w = true;
        if(key == KeyEvent.VK_A)   a = true;
        if(key == KeyEvent.VK_S)   s = true;
        if(key == KeyEvent.VK_D)   d = true;
    }

    //KeyReleased
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W) w = false;
        if(key == KeyEvent.VK_A) a = false;
        if(key == KeyEvent.VK_S) s = false;
        if(key == KeyEvent.VK_D) d = false;
    }
    
}
