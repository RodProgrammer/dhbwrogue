package dhbw.rogue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends JFrame implements WindowListener {

    private GameCanvas gameCanvas;

    public Window() {
        gameCanvas = new GameCanvas();
        setTitle("Clandestine Dungeons");
        setSize(1280, 720);
        setPreferredSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        addWindowListener(this);

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

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("Closed");
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
