package vaultescape.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import vaultescape.entity.Player;


public class GamePanel extends JPanel implements Runnable{
    // Setting the game panel to appear in the window app
    final int defaultTileSize = 8; // creating
    final int scale = 3; //scaling to 16 by 3 to make a bigger 

    public final int tilesize = defaultTileSize * scale;
    final int maxColume = 63;
    final int maxRow = 34;

    public final int screenWidth = tilesize * maxColume; 
    public final int screenHeight = tilesize * maxRow;

    final int fps = 80;

    Thread gameThread; //to run the game "ticks"
    KeyDetector keyh = new KeyDetector();
    TileGenerator tm = new TileGenerator(this);
    public Player player = new Player(this,keyh);
    // public CollisionCheck cck = new CollisionCheck(this);
    


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);
        this.requestFocusInWindow(); // Request focus for capturing key events
    }

    //begins tic
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    // A clock to the game
    @Override
    public void run() {
        double drawIntv = 1000000000/fps;
        double nextIntv = System.nanoTime() + drawIntv;

        while(gameThread != null){

            update();
            repaint();

            try{
                double remainTime = nextIntv - System.nanoTime();
                remainTime = remainTime/1000000;

                if(remainTime < 0){
                    remainTime = 0;
                }
                Thread.sleep((long) remainTime);
                nextIntv += drawIntv;
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    // Update function for repainting (Drawing)
    public void update(){
        player.update();
    }

    //repaints the board
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tm.draw(g2);
        player.draw(g2);
        // System.out.println("tic");
        g2.dispose();   // removes memory
    }


} 