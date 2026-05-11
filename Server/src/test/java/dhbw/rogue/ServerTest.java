package dhbw.rogue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerTest {

    @Test
    public void ServerStartTest() {
        Server server = new Server(4001);

        boolean isActiveServer;

        try (ServerSocket socket = new ServerSocket(4001)) {
            isActiveServer = false;
        } catch (IOException e) {
            isActiveServer = true;
            System.out.println("Could not connect to the server" + e.getMessage());
        }

        Assertions.assertTrue(isActiveServer);
    }

}