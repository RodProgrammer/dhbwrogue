package dhbw.rogue.graphics;

import dhbw.rogue.data.Message;
import dhbw.rogue.functionality.Chat;
import dhbw.rogue.functionality.RogueKeyListener;
import dhbw.rogue.connection.ServerConnection;
import dhbw.rogue.effects.Effect;
import dhbw.rogue.entity.Dwarf;
import dhbw.rogue.entity.Entity;
import dhbw.rogue.entity.Player;
import dhbw.rogue.mapmanager.MapManager;
import dhbw.rogue.particle.Particle;
import dhbw.rogue.spritemanager.ResourceManager;
import dhbw.rogue.tiles.Tile;
import dhbw.rogue.utility.Settings;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.List;

/**
 * This class represents the Game Drawing Board aka Canvas. It's where everything being rendered... yea I cant scooby doo this anymore
 */
public class GameCanvas extends Canvas implements Runnable {

    private boolean running;
    private int fps;
    private int tps;
    private final List<String> informationMessages;

    private final Player player;

    private ServerConnection serverConnection;
    private final RogueKeyListener listener;
    private final Chat chat;

    private final List<Entity> entities;
    private final List<Particle> particles;
    private final List<Player> players;

    private final MapRenderer mapRenderer;

    private final ResourceManager resourceManager;
    private final LightRenderer lightRenderer;

    /**
     * This constructor initializes all Characters, Players, Messages, and renderer.
     *
     * @param resourceManager   The Manager which holds all the Sprites
     * @param mapManager        The Manager that holds all the Maps
     */
    public GameCanvas(ResourceManager resourceManager, MapManager mapManager) {
        running = true;

        informationMessages = Collections.synchronizedList(new ArrayList<>());
        players = Collections.synchronizedList(new ArrayList<>());
        entities = Collections.synchronizedList(new ArrayList<>());
        particles = Collections.synchronizedList(new ArrayList<>());

        chat = new Chat(this);

        this.resourceManager = resourceManager;

        player = new Dwarf(0, 0, resourceManager);
        listener = new RogueKeyListener(player, chat, particles);
        addKeyListener(listener);

        mapRenderer = new MapRenderer(resourceManager, mapManager);
        lightRenderer = new LightRenderer(mapRenderer.getMap());

    }

    /**
     * This method starts this object thread.
     */
    public void startThread() {
        new Thread(this).start();
        deleteMessages();
    }

    /**
     * This Method is a Method from Runnable, it starts once a thread is being started.
     * It also has all the tick and render tick logic.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / Settings.TPS;
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (unprocessed >= 1) {
                ticks++;
                player.tick();
                mapRenderer.tick(); // maybe animations for maps?

                synchronized (player) { //ConcurrentModificationException without it :)
                    if (serverConnection != null) {
                        serverConnection.sendObject(player);
                    }
                }

                synchronized (particles) {
                    particles.forEach(Particle::tick);
                    particles.removeIf(Particle::toRemove);
                }

                unprocessed--;
            }

            frames++;
            render();

            if (System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                fps = frames;
                tps = ticks;
                frames = 0;
                ticks = 0;
            }
        }
    }

    /**
     * This method goes through the process of actually rendering the objects on the screen.
     */
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

        int height = 0;

        mapRenderer.render(g, player.getX(), player.getY());

        synchronized (informationMessages) {
            for (String message : informationMessages) {
                g.drawString(message, 20, height + 80);
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
                player.drawPlayer(g, this.player.getX(), this.player.getY());
            }
        }

        player.draw(g);

        synchronized (particles) {
            for(Particle particle : particles) {
                particle.render(g, player.getX(), player.getY());
            }
        }

        lightRenderer.renderLight(g, player.getX(), player.getY());

        for (Effect effect : player.getEffects()) {
            g.drawImage(effect.getEffectIcon(), 48, Settings.SCREEN_HEIGHT-96, null);
        }

        g.setColor(Color.GREEN);
        g.fillRect(20, 48, Settings.SCALED_TILE_SIZE, 16);
        g.setColor(Color.BLUE);
        g.fillRect(20, 64, Settings.SCALED_TILE_SIZE, 16);

        g.setColor(Color.WHITE);
        g.drawString("FPS: " + fps, 20, 20);
        g.drawString("TPS: " + tps, 20, 40);

        chat.renderChat(g);

        g.dispose();
        bs.show();
    }

    /**
     * This method adds a Player or updates a current Player.
     * It also uses the Resource Manager to give the Player their model.
     *
     * @param player    The Player to add or to update
     */
    public synchronized void addPlayer(Player player) {
        if (this.player.getName().equals(player.getName())) {
            this.player.updatePlayer(player);
            return;
        }

        synchronized (players) {
            //it first looks if the player already exists, and updates it
            for (Player p : players) {
                if (p.getName().equals(player.getName())) {
                    p.updatePlayer(player);
                   return;
                }
            }
            player.setResourceManager(resourceManager);
            player.loadImages();
            for(Effect p : player.getEffects()) {
                p.setResourceManager(resourceManager);
                p.loadEffect();
            }
            players.add(player);
        }
    }

    /**
     * This method adds a new Entity
     *
     * @param entity    Entity to add
     */
    public void addEntity(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        } else {
            entities.remove(entity);
            entities.add(entity);
        }
    }

    /**
     * This method removes a Player when its disconnected
     *
     * @param player    Player to remove
     */
    public void removePlayer(Player player) {
        synchronized (players) {
            players.removeIf(p -> p.getName().equals(player.getName()));
        }
    }

    /**
     * This method automatically deletes information Messages after a short time.
     */
    private void deleteMessages() {
        new Thread(() -> {
            while(running) {
                if (!informationMessages.isEmpty()) {
                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    synchronized (informationMessages) {
                        informationMessages.remove(informationMessages.getFirst());
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

    public void addInformationMessage(String message) {
        informationMessages.add(message);
    }

    public void addChatMessage(Message message) {
        chat.addMessage(message);
    }

    public void sendMessageToServer(Message message) {
        serverConnection.sendObject(message);
    }
}
