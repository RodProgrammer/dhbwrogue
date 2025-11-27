package dhbw.rogue.lobbyviewer;

import javax.swing.*;
import java.awt.*;

public class SwingLobby extends JPanel {

    private final String name;
    private int count;

    public SwingLobby(String name, int count, LobbySelector lobbySelector) {
        setLayout(new GridLayout(1, 3));
        add(new JLabel(name));
        add(new JLabel(String.valueOf(count)));
        JButton button = new JButton("Join");
        button.addActionListener(e -> {
            lobbySelector.setVisible(false);
        });
        add(button);

        this.name = name;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

}
