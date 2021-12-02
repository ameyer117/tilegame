package tilegame;

import tilegame.display.Display;
import tilegame.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable {

    private static int BUFFER_COUNT = 3;

    private boolean running = false;
    private Display display;
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;

    private BufferedImage testImage;


    public String title;
    public int width;
    public int height;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    private void init() {
        display = new Display(title, width, height);
        testImage = ImageLoader.loadImage("/textures/smile.png");
    }

    private void tick() {

    }

    private void render() {
        // Memory used to draw on screen (prevents flickering)
        bs = display.getCanvas().getBufferStrategy();

        if(bs == null) {
            display.getCanvas().createBufferStrategy(BUFFER_COUNT);
            return;
        }

        g = bs.getDrawGraphics();

        // Important to clear the screen first
        clearScreen();

        // Start drawing
        g.drawImage(testImage, 20, 20, null);

        // End drawing
        bs.show();
        g.dispose();
    }

    private void clearScreen() {
        g.clearRect(0,0, width, height);
    }

    @Override
    public void run() {
        init();

        while(running) {
            tick();
            render();
        }

        stop();
    }

    public synchronized void start() {
        if(running) return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(thread == null || !running) return;

        running = false;

        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
