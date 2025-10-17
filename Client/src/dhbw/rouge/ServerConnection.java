package dhbw.rouge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {

    private BufferedReader input;
    private BufferedReader keyboard;
    private PrintWriter out;
    private Socket socket;
    private Window gameWindow;

    public ServerConnection(Socket socket, Window gameWindow) {
        this.socket = socket;
        this.gameWindow = gameWindow;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("[ERROR] Couldn't establish connection.");
            return;
        }
        createContinuousConnection();
    }

    private void createContinuousConnection() {
        new Thread(() -> {
            out.println("RobinTest " + hashCode());
            String msg;
            try {
                while ((msg = input.readLine()) != null) {
                    System.out.println(msg);
                    gameWindow.getGameCanvas().addMessage(msg);
                }
                socket.close();
            } catch (IOException e) {
                System.out.println("[INFO] Disconnected from server.");
            }
        }).start();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void sendObject(Object o) {

    }

}
