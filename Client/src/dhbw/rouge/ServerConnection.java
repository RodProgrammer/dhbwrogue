package dhbw.rouge;

import entity.Entity;
import entity.Player;

import java.io.*;
import java.net.Socket;

public class ServerConnection {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Socket socket;
    private final Window gameWindow;

    public ServerConnection(Socket socket, Window gameWindow) {
        this.socket = socket;
        this.gameWindow = gameWindow;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println("[ERROR] Couldn't establish connection.");
            return;
        }
        createContinuousConnection();
    }

    private void createContinuousConnection() {
        new Thread(() -> {
            Object msg;
            try {
                while ((msg = in.readObject()) != null) {
                    switch (msg) {
                        case String s -> {
                            gameWindow.getGameCanvas().addMessage(s);
                            if (s.split(" ").length == 2 && s.split(" ")[0].equals("Disconnected:")) {
                                gameWindow.getGameCanvas().removePlayer(s.split(" ")[1]);
                            }
                        }
                        case Player player -> {
                            System.out.println(player);
                            gameWindow.getGameCanvas().addPlayer(player);
                        }
                        case Entity entity -> gameWindow.getGameCanvas().addEntity(entity);
                        default -> {}
                    }
                }
                socket.close();
            } catch (IOException e) {
                System.out.println("[INFO] Disconnected from server.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendObject(Object o) {
        try {
            out.reset();
            out.writeObject(o);
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
