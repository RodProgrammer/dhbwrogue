package dhbw.rogue;

import data.Message;
import entity.Entity;
import entity.Player;
import utility.Settings;

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

        System.out.println("[INFO] Server has been started.");
    }


    public void startServer() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    //getClient Lobby connection, after that it should go to the lobby or not
                    Lobby lobby = new Lobby("");
                    ClientConnection client = new ClientConnection(lobby, socket);
                    connections.add(client);
                    client.start();
                }).start();
            } catch (IOException e) {
                System.out.println("[ERROR] Client Connecting error");
            }
        }
    }

    public static void main(String[] args) {
        new Server(4000).startServer();
    }
}
