package dhbw.rogue;

import dhbw.rogue.data.Message;
import dhbw.rogue.entity.Entity;
import dhbw.rogue.entity.Player;
import dhbw.rogue.mapmanager.maps.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lobby implements Runnable {

    private final List<ClientConnection> clients;

    private final List<Entity> entities;

    private final String name;

    private Map map; //TODO: add make to test :(

    public Lobby(String name) {
        clients = Collections.synchronizedList(new ArrayList<>());
        entities = Collections.synchronizedList(new ArrayList<>());

        this.name = name;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double ticks = 60D;
        double ns = 1000000000 / ticks;
        double delta = 0;

        int tps = 0;

        while (true) {
            if (clients.isEmpty()) continue;

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                delta--;
                tps++;
            }

            if (tps >= 60) {
                tps = 0;
            }
        }
    }

    public void tick() {
        for (ClientConnection client : clients) {
            Player player = client.getLastPlayerState();
            if (player != null) {
                checkCollision();
            }
        }
    }

    public synchronized void checkCollision() {
        synchronized (clients) {
            for(ClientConnection client : clients) {
                Player player = client.getLastPlayerState();
                //TODO: check Collision here
            }
        }
    }

    public synchronized void addClient(ClientConnection client) {
        clients.add(client);
    }

    public synchronized void sendMessage(ClientConnection clientConnection , Message message) {
        synchronized (clients) {
            for (ClientConnection client : clients) {
                client.sendMessage(new Message(message, clientConnection.getUsername()));
            }
        }
    }

    public synchronized void sendInformation(ClientConnection clientConnection, String information) {
        for (ClientConnection client : clients) {
            if (client != clientConnection) {
                client.sendInformation(information);
            }
        }
    }

    public synchronized void sendEntity(ClientConnection clientConnection, Entity entity) {
        synchronized (clients) {
            for (ClientConnection client : clients) {
                if (client != clientConnection) {
                    client.sendEntity(entity);
                }
            }
        }
    }

    public synchronized void sendPlayer(ClientConnection clientConnection, Player player) {
        synchronized (clients) {
            for (ClientConnection client : clients) {
                if (client != clientConnection) {
                    client.sendPlayer(player);
                }
            }
        }
    }

    public synchronized void updatePlayer(ClientConnection clientConnection, Player player) {
        synchronized (clients) {
            for (ClientConnection client : clients) {
                if (client == clientConnection) {
                    client.sendPlayer(player);
                    break;
                }
            }
        }
    }

    public synchronized void removeClient(ClientConnection clientConnection) {
        clients.remove(clientConnection);
        System.out.println("Client " + clientConnection.getUsername() + " has disconnected from the lobby: " + name + ".");
        for (ClientConnection connection : clients) {
            connection.sendInformation("Disconnected: " + clientConnection.getUsername());
            connection.sendMessage(new Message("Disconnected Player", clientConnection.getLastPlayerState()));
        }
    }

    public void start() {
        new Thread(this).start();
    }
}
