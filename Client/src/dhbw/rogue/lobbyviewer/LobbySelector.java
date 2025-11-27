package dhbw.rogue.lobbyviewer;


import dhbw.rogue.Client;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class LobbySelector extends JFrame {

    private final String username;
    private JPanel lobbylist;
    private HashMap<String, SwingLobby> lobbies;

    public LobbySelector(String username) {
        setTitle("Lobbys");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JButton button = new JButton("Add Lobby");
        button.addActionListener(e -> {

        });
        add(button, BorderLayout.SOUTH);

        lobbylist = new JPanel();
        JScrollPane scrollPane = new JScrollPane(lobbylist);
        add(scrollPane);

        lobbies = new HashMap<>();

        this.username = username;

        setVisible(true);
    }

    public void addLobby(String name, int count) {
        SwingLobby temp = new SwingLobby(name, count, this);
        lobbylist.add(temp);
        lobbies.put(name, temp);
    }

    public void updateLobby(String name, int count) {
        lobbies.get(name).setCount(count);
    }

    public void removeLobby(String name) {
        SwingLobby temp = lobbies.get(name);
        lobbylist.remove(temp);
        lobbies.remove(name);
    }

    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog(null, "Please enter your Username", "Username", JOptionPane.INFORMATION_MESSAGE);

        if (input != null && !input.isEmpty()) {
            LobbySelector lobbySelector = new LobbySelector(input);
            new Client(lobbySelector.username, "localhost", 4000);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid or Username already in use", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
