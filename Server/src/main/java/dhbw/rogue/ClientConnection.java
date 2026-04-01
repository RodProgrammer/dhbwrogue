package dhbw.rogue;

import dhbw.rogue.data.Message;
import dhbw.rogue.entity.Entity;
import dhbw.rogue.entity.Player;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection implements Runnable {

    private ObjectOutputStream oOut;

    private final Socket socket;
    private final LobbyManager lobbyManager;

    private Lobby lobby;
    private Player lastPlayerState;

    private volatile boolean connected;
    private volatile Status status;

    public ClientConnection(Lobby lobby, Socket socket) {
        this.lobby = lobby; //no Lobby
        this.lobbyManager = null;
        this.socket = socket;
        status = Status.IN_GAME;
        connected = true;
    }

    public synchronized void sendInformation(String information) {
        if (oOut != null) {
            try {
                oOut.writeObject(information);
                oOut.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void sendMessage(Message message) {
        if (oOut != null) {
            try {
                oOut.writeObject(message);
                oOut.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void sendEntity(Entity entity) {
        if (oOut != null) {
            try {
                oOut.writeObject(entity);
                oOut.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void sendPlayer(Player player) {
        if (oOut != null && connected && !socket.isClosed()) {
            try {
                if (socket.isConnected() && connected && oOut != null) {
                    oOut.writeObject(player);
                    oOut.flush();
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

            //so we got it so it goes from connection to connection.
            switch(status) {
                case LOBBY -> getLobbyMessages(oIn);
                case CONNECTED -> getConnectionMessages(oIn);
                case IN_GAME -> getGameMessages(oIn);
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
                connected = false;
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
                // we need to do something here so it goes from CONNECTED to LOBBY, so we end up having it working :)
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
