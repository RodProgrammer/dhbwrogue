package dhbw.rogue;

import data.Message;
import entity.Entity;
import mapmanager.maps.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lobby implements Runnable {

    private final List<ClientConnection> clients;

    private final List<Entity> entities ;

    private Map map;

    public Lobby() {
        clients = Collections.synchronizedList(new ArrayList<>());
        entities = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void run() {

    }

    public void tick() {

    }

    public void addClient(ClientConnection client) {
        clients.add(client);
    }

    public synchronized void removeClient(ClientConnection client) {
        clients.remove(client);
    }

    public synchronized void sendMessage(Message message) {
        for (ClientConnection c : clients) {
            c.sendMessage(message);
        }
    }
}
