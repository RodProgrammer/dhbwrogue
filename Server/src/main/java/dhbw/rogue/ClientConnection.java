package dhbw.rogue;

import dhbw.rogue.data.Message;
import dhbw.rogue.entity.Entity;
import dhbw.rogue.entity.Player;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection implements Runnable {

    private ObjectOutputStream out;

    private final Socket socket;
    private final LobbyManager lobbyManager;

    private Lobby lobby;
    private Player lastPlayerState;

    private volatile boolean isConnected;
    private volatile Status status;

    public ClientConnection(Lobby lobby, Socket socket) {
        this.lobby = lobby; //no Lobby
        this.lobbyManager = null;
        this.socket = socket;
        status = Status.IN_GAME;
        isConnected = true;
    }

    public synchronized void sendInformation(String information) {
        if (out != null) {
            try {
                out.writeObject(information);
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void sendMessage(Message message) {
        if (out != null) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void sendEntity(Entity entity) {
        if (out != null) {
            try {
                out.writeObject(entity);
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void sendPlayer(Player player) {
        if (out != null && isConnected && !socket.isClosed()) {
            try {
                if (socket.isConnected() && isConnected && out != null) {
                    out.writeObject(player);
                    out.flush();
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
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            this.out = out;

            //so we got it so it goes from connection to connection.
            switch(status) {
                case LOBBY -> getLobbyMessages(in);
                case CONNECTED -> getConnectionMessages(in);
                case IN_GAME -> getGameMessages(in);
            }

        } catch (IOException e) {
            e.printStackTrace();
            lobby.removeClient(this);
            System.out.println("Client disconnected.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Couldn't parse entity.");
        } finally {
            try {
                isConnected = false;
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            lobby.removeClient(this);
            System.out.println("Last Client state: " + lastPlayerState);
        }
    }

    private void getConnectionMessages(ObjectInputStream in) throws IOException, ClassNotFoundException {

    }

    private void getLobbyMessages(ObjectInputStream oIn) throws IOException, ClassNotFoundException {
        while(true) {
            Object answer;
            try {
                answer = oIn.readObject();
            } catch (SocketException | EOFException e) {
                break;
            }
            if (answer instanceof String) {
                // TODO: we need to do something here so it goes from CONNECTED to LOBBY, so we end up having it working :)
            }
        }
    }

    private void getGameMessages(ObjectInputStream in) throws ClassNotFoundException, IOException {
        while (true) {
            Object answer;
            try {
                answer = in.readObject();
            } catch (SocketException | EOFException e) {
                System.out.println("[ERROR]: " + e.getMessage());
                break;
            }
            switch (answer) {
                case Player player -> {
                    this.lastPlayerState = (Player) answer;
                    lobby.sendPlayer(this, player);
                }
                case Entity entity -> {
                    lobby.sendEntity(this, entity);
                }
                case Message message -> {
                    lobby.sendMessage(this, message);
                }
                case String s -> lobby.sendInformation(this, s);
                default -> {}
            }
        }
    }

    public synchronized Player getLastPlayerState() {
        return lastPlayerState;
    }

    public synchronized String getUsername() {
        return lastPlayerState.getName();
    }
}
