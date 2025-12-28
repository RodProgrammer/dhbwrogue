package dhbw.rogue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;

    private final LobbyManager lobbyManager;

    private final List<ClientConnection> connections;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("[ERROR] Server couldn't create ServerSocket");
            System.exit(-1);
        }
        connections = Collections.synchronizedList(new ArrayList<>());

        lobbyManager = new LobbyManager();

        System.out.println("[INFO] Server has been started.");
    }


    public void startServer() {
        Lobby lobby = lobbyManager.createLobby("");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    ClientConnection client = new ClientConnection(lobbyManager, socket);
                    lobby.addClient(client);
                    connections.add(client);
                    client.start();
                    lobby.start();
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
