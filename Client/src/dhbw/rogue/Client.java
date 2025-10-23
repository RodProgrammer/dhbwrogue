package dhbw.rogue;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;

    public Client(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            Window gameWindow = new Window();
            ServerConnection serverConnection = new ServerConnection(socket, gameWindow);
            gameWindow.setServerConnection(serverConnection);
        } catch (IOException ex) {
            System.out.println("[ERROR] Couldn't create Socket");
        }

    }

    public static void main(String[] args) {
        new Client("localhost", 4000);
    }

}
