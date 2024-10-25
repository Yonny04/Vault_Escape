package vaultescape;


import java.awt.Dimension;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable{
    // Setting the game panel to appear in the window app
    final int defaultTileSize = 16; // creating
    final int scale = 3; //scaling to 16 by 3 to make a bigger 

    final int tilesize = defaultTileSize * scale;
    final int maxColume = 1980;
    final int maxRow = 1080;

    final int screenWidth = tilesize * maxColume; 
    final int screenHeight = tilesize * maxRow;

    final int fps = 60;

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;

    Thread gameThread; //to run the game "ticks"
    KeyDetector keyh = new KeyDetector();


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);
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
        if(keyh.w == true){
            playerY -= playerSpeed; 
        }
        if(keyh.a == true){
            playerX -= playerSpeed; 
        }
        if(keyh.s == true){
            playerY += playerSpeed; 
        }
        if(keyh.d == true){
            playerX += playerSpeed; 
        }
         
    }

    //repaints the board
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white); //color of the character
        g2.fillRect(playerX, playerY, tilesize, tilesize); //making the "character"
        g2.dispose();   // removes memory


    }


} 
