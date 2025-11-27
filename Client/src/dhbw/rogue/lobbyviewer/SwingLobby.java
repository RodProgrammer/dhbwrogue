package dhbw.rogue.lobbyviewer;

import javax.swing.*;
import java.awt.*;

public class SwingLobby extends JPanel {

    private final String name;
    private int count;
    private final JLabel countLabel;

    public SwingLobby(String name, int count, LobbySelector lobbySelector) {
        setLayout(new GridLayout(1, 3));
        add(new JLabel(name));
        countLabel = new JLabel(String.valueOf(count));
        add(countLabel);
        JButton button = new JButton("Join");
        button.addActionListener(e -> {
            //lobbySelector.setVisible(false);
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
        countLabel.setText(String.valueOf(count));
    }

    public String getName() {
        return name;
    }

}
