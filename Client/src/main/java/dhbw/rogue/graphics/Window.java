package dhbw.rogue.graphics;

import dhbw.rogue.data.Message;
import dhbw.rogue.connection.ServerConnection;
import dhbw.rogue.entity.Entity;
import dhbw.rogue.entity.Player;
import dhbw.rogue.mapmanager.MapManager;
import dhbw.rogue.spritemanager.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final GameCanvas gameCanvas;

    public Window(ResourceManager resourceManager, MapManager mapManager) {
        gameCanvas = new GameCanvas(resourceManager, mapManager);
        setTitle("Clandestine Dungeons");
        setSize(1280, 720);
        setPreferredSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(gameCanvas);
        pack();

        gameCanvas.startThread();

        setVisible(true);
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

    public void setServerConnection(ServerConnection serverConnection) {
        gameCanvas.setServerConnection(serverConnection);
    }
}
