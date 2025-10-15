package dhbw.rouge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public Client(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Window gameWindow = new Window(out);

            new Thread(() -> {
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



            //String userInput;
            //while ((userInput = keyboard.readLine()) != null) {
            //    out.println(userInput);
            //}


        } catch (IOException ex) {
            System.out.println("[ERROR] Couldn't create Socket");
        }
    }

    public static void main(String[] args) {
        new Client("localhost", 4000);
    }

}
