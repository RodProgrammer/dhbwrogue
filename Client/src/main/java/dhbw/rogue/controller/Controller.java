package dhbw.rogue.controller;

import dhbw.rogue.connection.ServerConnection;
import dhbw.rogue.graphics.GameCanvas;

public class Controller {

    private final GameCanvas gameCanvas;
    private final ServerConnection serverConnection;

    public Controller(GameCanvas gameCanvas, ServerConnection serverConnection) {
        this.gameCanvas = gameCanvas;
        this.serverConnection = serverConnection;
    }

    public void sendObject(Object object) {
        if (serverConnection != null) {
            serverConnection.sendObject(object);
        }
    }

    /**
     * maybe delete them bcs we dont need them
     */
    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }
    public ServerConnection getServerConnection() {
        return serverConnection;
    }

}
