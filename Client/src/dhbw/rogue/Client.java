package dhbw.rogue;

import dhbw.rogue.connection.ServerConnection;
import dhbw.rogue.graphics.Window;
import spritemanager.ResourceManager;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;

    public Client(String ip, int port) {
        ResourceManager resourceManager = new ResourceManager();
        try {
            socket = new Socket(ip, port);
            Window gameWindow = new Window(resourceManager);
            ServerConnection serverConnection = new ServerConnection(socket, gameWindow, resourceManager);
            gameWindow.setServerConnection(serverConnection);
        } catch (IOException ex) {
            System.out.println("[ERROR] Couldn't create Socket");
        }

        File test = new File("resource/entities/chomb/chomb.png");
        System.out.println("[INFO] File: " + test.getAbsolutePath());
        System.out.println("[INFO] Finding it : " + test.exists());

    }

    public static void main(String[] args) {
        new Client("localhost", 4000);
    }

}
