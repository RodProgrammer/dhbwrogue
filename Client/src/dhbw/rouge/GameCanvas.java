package dhbw.rouge;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class GameCanvas extends Canvas implements Runnable {

    private boolean running;
    private int fps;
    private int tps;
    private List<String> messages;

    public GameCanvas() {
        running = true;

        messages = Collections.synchronizedList(new ArrayList<>());
    }

    public void startThread() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / 60.0;
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();

        while(running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (unprocessed >= 1) {
                ticks++;
                //animation update here
                unprocessed--;
            }

            {
                frames++;
                //maybe instead of repaint() use the other method of rendering with buffer
                render();
            }

            if (System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                fps = frames;
                tps = ticks;
                System.out.println("FPS: " + fps);
                System.out.println("TPS: " + tps);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.drawString("FPS: " + fps, 20, 20);
        g.drawString("TPS: " + tps, 20, 40);
        //System.out.println("Messages Size: " + messages.size());

        int height = 0;
        for(String message : messages) {
            g.drawString(message, 10, height + 60);
            height += 10;
        }

        g.dispose();
        bs.show();
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
