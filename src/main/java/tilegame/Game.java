package tilegame;

import tilegame.display.Display;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private static int BUFFER_COUNT = 3;

    private boolean running = false;
    private Display display;
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;

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
        g.setColor(Color.red);
        g.fillRect(10, 50, 50, 70);

        g.setColor(Color.green);
        g.fillRect(20, 50, 10, 10);

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
