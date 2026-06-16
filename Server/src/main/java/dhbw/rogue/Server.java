package dhbw.rogue;

import dhbw.rogue.utility.Logger;

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
            Logger.logError("[ERROR] Server couldn't create ServerSocket");
            Logger.logError(e.getMessage());
            System.exit(-1);
        }
        connections = Collections.synchronizedList(new ArrayList<>());

        lobbyManager = new LobbyManager();

        Logger.logInfo("Server has been started.");
    }


    public void startServer() {
        Lobby lobby = lobbyManager.createLobby("");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    ClientConnection client = new ClientConnection(lobby, socket);
                    lobby.addClient(client);
                    connections.add(client);
                    client.start();
                    lobby.start();
                }).start();

            } catch (IOException e) {
                Logger.logError("Client Connecting error");
                Logger.logError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Server(4000).startServer();
    }
}
