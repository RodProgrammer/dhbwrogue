package dhbw.rouge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientConnection implements Runnable {

    private PrintWriter out;
    private final Socket socket;
    private final Server server;

    private String username;

    public ClientConnection(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Client connected.");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            this.out = out;
            getConnectionMessages(in, out);

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

    private void getConnectionMessages(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Connected to the Server.");

        String username = in.readLine();
        this.username = Objects.requireNonNullElse(username, "NoNameClient");

        String message;
        while ((message = in.readLine()) != null) {
            System.out.println("Received: " + message);
            server.sendMessage(this, message);
        }
    }

    public String getUsername() {
        return username;
    }
}
