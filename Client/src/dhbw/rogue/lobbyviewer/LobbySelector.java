package dhbw.rogue.lobbyviewer;


import javax.swing.*;
import java.awt.*;

public class LobbySelector extends JFrame {

    private JPanel lobbylist;

    public LobbySelector() {
        setTitle("Lobbys");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        JButton button = new JButton("Add Lobby");
        add(button);

        lobbylist = new JPanel();

        setVisible(true);
    }

    public void addLobby(String name, int count) {

    }

    public void updateLobby(String name, int count) {

    }

    public void removeLobby(String name) {

    }

    public static void main(String[] args) {
        new LobbySelector();
        //new Client("localhost", 4000);
    }

}
