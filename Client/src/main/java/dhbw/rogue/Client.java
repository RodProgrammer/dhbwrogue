package dhbw.rogue;

import dhbw.rogue.controller.Controller;
import dhbw.rogue.mapmanager.MapManager;
import dhbw.rogue.spritemanager.ResourceManager;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;

    public Client(String username, String ip, int port) {

        ResourceManager resourceManager = new ResourceManager();
        MapManager mapManager = new MapManager(resourceManager);
        try {
            socket = new Socket(ip, port);
            new Controller(resourceManager, mapManager, socket);
        } catch (IOException ex) {
            System.out.println("[ERROR] Couldn't create Socket");
        }
    }



}
