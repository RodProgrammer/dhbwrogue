package dhbw.rogue.controller;

import dhbw.rogue.connection.ServerConnection;
import dhbw.rogue.data.Message;
import dhbw.rogue.entity.Entity;
import dhbw.rogue.entity.Player;
import dhbw.rogue.graphics.GameCanvas;
import dhbw.rogue.graphics.Window;
import dhbw.rogue.mapmanager.MapManager;
import dhbw.rogue.spritemanager.ResourceManager;

import javax.swing.*;
import java.net.Socket;

public class Controller {

    private GameCanvas gameCanvas;
    private ServerConnection serverConnection;
    private Window gameWindow;

    public Controller(ResourceManager resourceManager, MapManager mapManager, Socket socket) {
        //the Controller creates the instances, otherwise something will break in the Connection between the Front- and Back-end
        gameWindow = new Window(resourceManager, mapManager);
        this.serverConnection = new ServerConnection(socket, this);
        this.gameCanvas = gameWindow.getGameCanvas();
        gameWindow.setController(this);
    }

    public void sendObject(Object object) {
        if (serverConnection != null) {
            serverConnection.sendObject(object);
        }
    }

    public synchronized void update_entity(Entity entity) {
        gameCanvas.addEntity(entity);
    }

    public synchronized void update_player(Player player) {
        gameCanvas.addPlayer(player);
    }

    public synchronized void addChatMessage(Message message) {
        if (message.getPlayer() != null) {
            gameCanvas.removePlayer(message.getPlayer());
            return;
        }
        gameCanvas.addChatMessage(message);
    }

    public synchronized void addInformationMessage(String information) {
        gameCanvas.addInformationMessage(information);
    }

    public void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(gameWindow, message, title, JOptionPane.WARNING_MESSAGE);
    }

}
