package dhbw.rogue;

import entity.Direction;
import entity.Entity;
import entity.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.List;

public class GameCanvas extends Canvas implements Runnable, KeyListener {

    private boolean running;
    private int fps;
    private int tps;
    private final List<String> messages;

    private final Player player;

    private ServerConnection serverConnection;

    private final List<Entity> entities;
    private final List<Player> players;

    public GameCanvas() {
        running = true;

        messages = Collections.synchronizedList(new ArrayList<>());
        players = Collections.synchronizedList(new ArrayList<>());
        entities = Collections.synchronizedList(new ArrayList<>());

        player = new Player(0, 0);

        addKeyListener(this);
    }

    public void startThread() {
        new Thread(this).start();
        deleteMessages();
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
                player.tick();

                synchronized (player) { //ConcurrentModificationException without it :)
                    if (serverConnection != null) {
                        serverConnection.sendObject(player);
                    }
                }
                unprocessed--;
            }

            frames++;
            render();

            if (System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                fps = frames;
                tps = ticks;
                //System.out.println("FPS: " + fps);
                //System.out.println("TPS: " + tps);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }
        Toolkit.getDefaultToolkit().sync();

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED); //For MacOS, since I have stuttering
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.drawString("FPS: " + fps, 20, 20);
        g.drawString("TPS: " + tps, 20, 40);

        int height = 0;

        synchronized (messages) {
            for (String message : messages) {
                g.drawString(message, 10, height + 60);
                height += 15;
            }
        }

        synchronized (entities) {
            for (Entity entity : entities) {
                entity.draw(g);
            }
        }

        synchronized (players) {
            for (Player player : players) {
                player.draw(g);
            }
        }

        player.draw(g);

        g.dispose();
        bs.show();
    }

    public void addPlayer(Player player) {
        synchronized (players) {
            boolean found = false;
            for (Player p : players) {
                if (p.getName().equals(player.getName())) {
                    found = true;
                    p.setX(player.getX());
                    p.setY(player.getY());
                    break;
                }
            }
            if (!found) {
                players.add(player);
            }
        }
    }

    public void addEntity(Entity entity) {
        if(!entities.contains(entity)) {
            entities.add(entity);
        } else {
            entities.remove(entity);
            entities.add(entity);
        }
    }

    public void removePlayer(String playerName) {
        synchronized (players) {
            players.removeIf(p -> p.getName().equals(playerName));
        }
    }

    private void deleteMessages() {
        new Thread(() -> {
            while(running) {
                if(!messages.isEmpty()) {
                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    synchronized (messages) {
                        messages.remove(messages.getFirst());
                    }
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_W == e.getKeyCode()) {
            player.addDirection(Direction.UP);
        }

        if (KeyEvent.VK_S == e.getKeyCode()) {
            player.addDirection(Direction.DOWN);
        }

        if (KeyEvent.VK_A == e.getKeyCode()) {
            player.addDirection(Direction.LEFT);
        }

        if (KeyEvent.VK_D == e.getKeyCode()) {
            player.addDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (KeyEvent.VK_W == e.getKeyCode()) {
            player.removeDirection(Direction.UP);
        }

        if (KeyEvent.VK_S == e.getKeyCode()) {
            player.removeDirection(Direction.DOWN);
        }

        if (KeyEvent.VK_A == e.getKeyCode()) {
            player.removeDirection(Direction.LEFT);
        }

        if (KeyEvent.VK_D == e.getKeyCode()) {
            player.removeDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
