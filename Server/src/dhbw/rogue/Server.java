package dhbw.rogue;

import data.Message;
import entity.Entity;
import entity.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;

    private final List<ClientConnection> connections;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("[ERROR] Server couldn't create ServerSocket");
        }
        connections = Collections.synchronizedList(new ArrayList<>());

        //pingClients();
    }

    public void removeClient(ClientConnection clientConnection) {
        connections.remove(clientConnection);
        System.out.println("Client " + clientConnection.getUsername() + " has disconnected.");
        for (ClientConnection connection : connections) {
            connection.sendInformation("Disconnected: " + clientConnection.getUsername());
        }
    }

    public void sendMessage(ClientConnection clientConnection , Message message) {
        synchronized (connections) {
            for (ClientConnection client : connections) {
                client.sendMessage(new Message(message, clientConnection.getUsername()));
            }
        }
    }

    public void sendInformation(ClientConnection clientConnection, String information) {
        for (ClientConnection client : connections) {
            if (client != clientConnection) {
                client.sendInformation(information);
            }
        }
    }

    public void sendEntity(ClientConnection clientConnection, Entity entity) {
        synchronized (connections) {
            for (ClientConnection client : connections) {
                if (client != clientConnection) {
                    client.sendEntity(entity);
                }
            }
        }
    }

    public void sendPlayer(ClientConnection clientConnection, Player player) {
        synchronized (connections) {
            for (ClientConnection client : connections) {
                if (client != clientConnection) {
                    client.sendPlayer(player);
                }
            }
        }
    }

    public void startServer() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientConnection client = new ClientConnection(socket, this);
                connections.add(client);
                client.start();
            } catch (IOException e) {
                System.out.println("[ERROR] Client Connecting error");
            }
        }
    }

    private void pingClients() {
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {}

                for (ClientConnection clientConnection : connections) {
                    clientConnection.sendInformation("Ping from Server!");
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Server(4000).startServer();
    }
}
