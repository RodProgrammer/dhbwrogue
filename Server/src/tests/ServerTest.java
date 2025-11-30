package tests;

import dhbw.rogue.Server;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class ServerTest {

    @Test
    public void ServerStartTest() {
        Server server = new Server(4001);

        boolean isActiveServer;

        try (Socket socket = new Socket("localhost", 4001)) {
            isActiveServer = true;
        } catch (IOException e) {
            isActiveServer = false;
        }

        assertTrue(isActiveServer);
    }

}
