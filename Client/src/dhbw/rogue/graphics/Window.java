package dhbw.rogue.graphics;

import dhbw.rogue.connection.ServerConnection;
import spritemanager.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final GameCanvas gameCanvas;

    public Window(ResourceManager resourceManager) {
        gameCanvas = new GameCanvas(resourceManager);
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

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public void setServerConnection(ServerConnection serverConnection) {
        gameCanvas.setServerConnection(serverConnection);
    }
}
