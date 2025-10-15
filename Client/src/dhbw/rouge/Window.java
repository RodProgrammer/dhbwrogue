package dhbw.rouge;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private GameCanvas gameCanvas;

    public Window() {
        gameCanvas = new GameCanvas();
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

}
