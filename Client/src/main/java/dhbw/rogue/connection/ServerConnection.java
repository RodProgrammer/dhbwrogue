package dhbw.rogue.connection;

import dhbw.rogue.data.Message;
import dhbw.rogue.graphics.Window;
import dhbw.rogue.entity.Entity;
import dhbw.rogue.entity.Player;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * This class is representing the connection to the Server.
 * It sends and receives Objects to and from the Server.
 */
public class ServerConnection {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Socket socket;
    private final Window gameWindow;

    /**
     * The Constructor creates the ObjectInput- and ObjectOutputStream out of the Socket.
     *
     * @param socket        The socket to the Server
     * @param gameWindow    The Game
     */
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

    /**
     * It's a connection to the server in a never-ending loop.
     */
    private void createContinuousConnection() {
        new Thread(() -> {
            Object msg;
            try {
                while ((msg = in.readObject()) != null) {
                    receiveMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
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
                    System.out.println("[ERROR] " + e.getMessage());
                    System.out.println("[INFO] Disconnected from Server.");
                }
            }
        }).start();
    }

    /**
     * This method is taking in an Object from the Server and evaluates it.
     *
     * @param objectInput The input Object
     */
    private synchronized void receiveMessage(Object objectInput) {
        try {
            switch (objectInput) {
                case String informationMessage -> gameWindow.addInformationMessage(informationMessage);
                case Player player -> gameWindow.update_player(player);
                case Entity entity -> gameWindow.update_entity(entity);
                case Message message -> gameWindow.addChatMessage(message);
                default -> {}
            }
        } catch (ClassCastException e) {
            System.out.println("[ERROR] Can't cast Object" + System.lineSeparator());
            e.printStackTrace();
        }
    }

    /**
     * This method sends an Object to the Server.
     *
     * @param o The Object
     */
    public synchronized void sendObject(Object o) {
        try {
            synchronized (out) {
                if (out != null && !socket.isClosed()) {
                    out.reset();
                    out.writeObject(o);
                    out.flush();
                } else {
                    System.out.println("[INFO] Lost connection.");
                    JOptionPane.showMessageDialog(gameWindow, "Lost connection.", "Server Connection", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
