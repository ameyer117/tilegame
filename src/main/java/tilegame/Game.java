package tilegame;

import tilegame.display.Display;

public class Game implements Runnable {

    private Display display;
    private Thread thread;
    private boolean running = false;

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
