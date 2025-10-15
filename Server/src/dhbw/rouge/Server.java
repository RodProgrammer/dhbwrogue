package dhbw.rouge;

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
    }

    public void removeClient(ClientConnection clientConnection) {
        connections.remove(clientConnection);
    }

    public void sentMessage(ClientConnection clientConnection, String message) {
        synchronized (connections) {
            for(ClientConnection client : connections) {
                if(client != clientConnection) {
                    client.sendMessage(message);
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

    public static void main(String[] args) {
        new Server(4000).startServer();
    }
}
