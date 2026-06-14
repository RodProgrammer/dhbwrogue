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
        button.addActionListener(e -> {});
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
        JPanel inputPanel = new JPanel();
        JLabel usernameLabel = new JLabel("Username: ");
        inputPanel.add(usernameLabel);
        JTextField usernameTextField = new JTextField("<Username>", 10);
        inputPanel.add(usernameTextField);
        JLabel ipAddressLabel = new JLabel("IP Address: ");
        inputPanel.add(ipAddressLabel);
        JTextField ipAddressTextField = new JTextField("localhost", 10);
        inputPanel.add(ipAddressTextField);
        JLabel portLabel = new JLabel("Port: ");
        inputPanel.add(portLabel);
        JTextField portTextField = new JTextField("4000", 5);
        inputPanel.add(portTextField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Input Values", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            LobbySelector lobbySelector = new LobbySelector(usernameTextField.getText());
            try {
                new Client(lobbySelector.username, ipAddressTextField.getText(), Integer.parseInt(portTextField.getText()));
            } catch (Exception e) {

            }

        }
    }

}
