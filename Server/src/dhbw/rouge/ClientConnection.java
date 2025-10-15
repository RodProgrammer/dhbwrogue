package dhbw.rouge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private Server server;

    public ClientConnection(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Client connected.");
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //out.println("Connected to the Server.");
            out.println("Connected to the Server.");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                server.sentMessage(this, message);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            server.removeClient(this);
        }
    }
}
