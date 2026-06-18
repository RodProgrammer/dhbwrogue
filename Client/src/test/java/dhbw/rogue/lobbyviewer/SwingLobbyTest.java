package dhbw.rogue.lobbyviewer;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SwingLobbyTest {

    @Test
    void constructorInitializesValuesCorrectly() {
        SwingLobby lobby = new SwingLobby("TestLobby", 5, null);

        assertEquals("TestLobby", lobby.getName());
        assertEquals(5, lobby.getCount());
    }

    @Test
    void setCountUpdatesValueAndLabel() throws Exception {
        SwingLobby lobby = new SwingLobby("Lobby", 2, null);

        lobby.setCount(7);

        assertEquals(7, lobby.getCount());
        assertEquals("7", getCountLabelText(lobby));
    }

    @Test
    void setCountUpdatesLabelMultipleTimes() throws Exception {
        SwingLobby lobby = new SwingLobby("Lobby", 0, null);

        lobby.setCount(1);
        lobby.setCount(10);

        assertEquals(10, lobby.getCount());
        assertEquals("10", getCountLabelText(lobby));
    }

    @Test
    void countLabelIsInitializedCorrectly() throws Exception {
        SwingLobby lobby = new SwingLobby("Lobby", 99, null);

        assertEquals("99", getCountLabelText(lobby));
    }

    // ----------------------------------------------------
    // Reflection helper
    // ----------------------------------------------------

    private String getCountLabelText(SwingLobby lobby) throws Exception {
        Field field = SwingLobby.class.getDeclaredField("countLabel");
        field.setAccessible(true);

        JLabel label = (JLabel) field.get(lobby);
        return label.getText();
    }
}