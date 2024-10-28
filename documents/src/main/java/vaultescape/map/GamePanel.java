package vaultescape.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import vaultescape.entity.Player;

public class GamePanel extends JPanel implements Runnable {
    final int defaultTileSize = 16; // 16x16 image tile
    final int scale = 4;

    public final int tilesize = defaultTileSize * scale; // 64x64 screen tile
    public final int numCols = 16;
    public final int numRows = 12;

    public final int screenWidth = tilesize * numCols; // 1024px
    public final int screenHeight = tilesize * numRows; // 768px
    final int fps = 60;

    private Thread gameThread;
    private KeyDetector keyh = new KeyDetector();
    private TileGenerator tileGenerator = new TileGenerator(this);
    private Player player = new Player(this, keyh);

    public Player getPlayer(){
        return player;
    }
    // Constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(82,45,61));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);
    }

    public TileGenerator getTileGenerator() {
        return tileGenerator;  // Provide access to tile generator
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update();  // Update player (with collision handling)
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileGenerator.draw(g2);  // Draw tiles
        player.draw(g2);  // Draw player
        g2.dispose();
    }
}
