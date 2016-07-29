package edu.ucsb.cs56.S12.sbaldwin.pokemon.framework;

import edu.ucsb.cs56.S12.sbaldwin.pokemon.graphics.SpriteBatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * The window frame for the Pokemon map game
 *
 * @author William Bennett
 */
public class MainWindow extends JPanel {
    boolean running = true;
    int width = 640;
    int height = 480;
    static final long maxTime = 16666;
    static final float gameFrameTime = .016666f;

    private JFrame containerWindow;
    volatile private boolean initialized = false;

    protected SpriteBatch spriteBatch;
    Toolkit toolkit;

    /**
     * Constructs the main window
     */
    public MainWindow() {
        containerWindow = new JFrame();
        containerWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        containerWindow.setSize(width, height);

        containerWindow.getContentPane().add(BorderLayout.CENTER, this);

        containerWindow.setVisible(true);

        //setFocusable(true);

        toolkit = Toolkit.getDefaultToolkit();
        width = toolkit.getScreenSize().width;
        height = toolkit.getScreenSize().height;
        spriteBatch = new SpriteBatch(this.createImage(width, height));
    }

    /**
     * Starts running the program
     */
    public void run() {
        long currentTime = System.nanoTime();
        long deltaT = 0;
        load();
        init();
        initialized = true;
        while (running) {
            update(gameFrameTime);
            draw(spriteBatch, gameFrameTime);
            paintScreen(spriteBatch.getBackBuffer());
            deltaT = System.nanoTime() - currentTime;
            try {
                Thread.sleep(Math.max((maxTime - deltaT) / 1000000, 0));
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted");
            }
            currentTime = System.nanoTime();
        }
    }

    /**
     * Initializes game logic code
     */
    protected void init() {
    }

    /**
     * Loads game assets
     */
    protected void load() {
    }

    /**
     * Moves the game forward a delta time and updates it
     *
     * @param gameTime the delta time
     */
    protected void update(float gameTime) {

    }

    /**
     * Moves the rendeering forward a delta time and draws to the screen
     *
     * @param spriteBatch the SpriteBatch that draws to the screen
     * @param gameTime the delta time
     */
    protected void draw(SpriteBatch spriteBatch, float gameTime) {
        if (!initialized)
            return;
    }

    /**
     * Paints the screen with a graphics object from a back buffer
     *
     * @param backBuffer the back buffer
     */
    private void paintScreen(Image backBuffer) {
        Graphics g;
        try {
            g = this.getGraphics();
            g.drawImage(backBuffer, 0, 0, null);
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
            Graphics clearGraphics = backBuffer.getGraphics();
            clearGraphics.setColor(Color.black);
            clearGraphics.drawRect(-1, -1, width, height);
        }
        catch (Exception e) {
            System.err.println("Error getting graphics device");
        }
    }

    public void addKeyListener(KeyListener kl) {
        this.containerWindow.addKeyListener(kl);
    }
}