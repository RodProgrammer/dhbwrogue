package dhbw.rouge;

import entity.Entity;
import entity.Player;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

public class ClientConnection implements Runnable {

    private ObjectOutputStream oOut;

    private final Socket socket;
    private final Server server;

    private Player lastPlayerState;

    public ClientConnection(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
    }

    public void sendMessage(String message) {
        if (oOut != null) {
            try {
                oOut.writeObject(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendEntity(Entity entity) {
        if(oOut != null) {
            try {
                oOut.writeObject(entity);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendPlayer(Player player) {
        if (oOut != null) {
            try {
                if (socket.isConnected()) {
                    oOut.writeObject(player);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Client connected.");
            try (ObjectOutputStream oOut = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream())) {

            this.oOut = oOut;
            getConnectionMessages(oIn);
        } catch (IOException e) {
            e.printStackTrace();
            server.removeClient(this);
            System.out.println("Client disconnected.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Couldn't parse entity.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            server.removeClient(this);
            System.out.println("Last Client state: " + lastPlayerState);
        }
    }

    private void getConnectionMessages(ObjectInputStream in) throws ClassNotFoundException, IOException {
        while (true) {
            Object answer;
            try {
                answer = in.readObject();
            } catch (SocketException | EOFException e) {
                break;
            }
            switch (answer) {
                case Player player -> {
                    this.lastPlayerState = (Player) answer;
                    server.sendPlayer(this, player);
                }
                case Entity entity -> {
                    server.sendEntity(this, entity);
                }
                case String s -> server.sendMessage(this, s);
                default -> {}
            }
        }
    }

    public String getUsername() {
        return lastPlayerState.getName();
    }
}
