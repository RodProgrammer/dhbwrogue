package dhbw.rogue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

public class ServerTest {

    @Test
    public void ServerStartTest() {
        Server server = new Server(4001);

        boolean isActiveServer;

        try (Socket socket = new Socket("localhost", 4001)) {
            isActiveServer = true;
        } catch (IOException e) {
            isActiveServer = false;
            System.out.println("Could not connect to the server" + e.getMessage());
        }

        Assertions.assertTrue(isActiveServer);
    }

}