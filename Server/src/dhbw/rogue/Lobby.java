package dhbw.rogue;

import data.Message;
import entity.Entity;
import entity.Player;
import mapmanager.maps.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lobby implements Runnable {

    private final transient List<ClientConnection> clients;

    private final transient List<Entity> entities;

    private final String name;

    private transient Map map;

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

                if (player.getX() < 0 && player.getY() < 0) {
                    player.setX(0);
                    player.setY(0);
                    updatePlayer(client, player);
                }

                if (player.getX() < 0) {
                    player.setX(0);
                    updatePlayer(client, player);
                } else if (player.getY() < 0) {
                    player.setY(0);
                    updatePlayer(client, player);
                }
            }
        }
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

    public void addClient(ClientConnection client) {
        clients.add(client);
    }

    public synchronized void removeClient(ClientConnection clientConnection) {
        clients.remove(clientConnection);
        System.out.println("Client " + clientConnection.getUsername() + " has disconnected from the lobby: " + name + ".");
        for (ClientConnection connection : clients) {
            connection.sendInformation("Disconnected: " + clientConnection.getUsername());
            connection.sendMessage(new Message("Disconnected Player", clientConnection.getLastPlayerState()));
        }
    }

    public synchronized void sendMessage(Message message) {
        for (ClientConnection c : clients) {
            c.sendMessage(message);
        }
    }
}
