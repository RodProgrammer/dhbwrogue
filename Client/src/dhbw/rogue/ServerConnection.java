package dhbw.rogue;

import data.Message;
import entity.Entity;
import entity.Player;

import javax.swing.*;
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
            e.printStackTrace();
            System.exit(0);
            return;
        }
        createContinuousConnection();
    }

    private void createContinuousConnection() {
        new Thread(() -> {
            Object msg;
            try {
                while ((msg = in.readObject()) != null) {
                    receiveMessage(msg);
                }
            } catch (IOException e) {
                System.out.println("[INFO] Disconnected from server.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayStoreException e) {
                System.err.println("[ERROR] ArrayStoreException while reading object.");
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("[INFO] Disconnected from Server.");
                }
            }
        }).start();
    }

    private void receiveMessage(Object msg) {
        try {
            switch (msg) {
                case String s -> {
                    gameWindow.getGameCanvas().addInformationMessage(s);
                    if (s.split(" ").length == 2 && s.split(" ")[0].equals("Disconnected:")) {
                        gameWindow.getGameCanvas().removePlayer(s.split(" ")[1]);
                    }
                    System.out.println("[INFO] Message received: " + s);
                }
                case Player player -> {
                    //System.out.println(player);
                    gameWindow.getGameCanvas().addPlayer(player);
                }
                case Entity entity -> gameWindow.getGameCanvas().addEntity(entity);
                case Message message -> {
                    gameWindow.getGameCanvas().addChatMessage(message);
                }
                default -> {}
            }
        } catch (ClassCastException e) {
            System.out.println("[ERROR] Can't cast Object" + System.lineSeparator());
            e.printStackTrace();
        }
    }

    public void sendObject(Object o) {
        try {
            synchronized (out) {
                if (out != null && !socket.isClosed()) {
                    out.reset();
                    out.writeObject(o);
                    out.flush();
                } else {
                    System.out.println("[Info] Lost connection.");
                    JOptionPane.showMessageDialog(gameWindow, "Lost connection.", "Server Connection", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
