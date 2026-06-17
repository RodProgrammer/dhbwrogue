package dhbw.rogue.lobbyviewer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LobbySelectorTest {

    private LobbySelector lobbySelector;
    private JPanel lobbyPanel;
    private HashMap<String, SwingLobby> lobbies;

    @BeforeEach
    void setUp() throws Exception {
        lobbySelector = createWithoutConstructor();

        lobbyPanel = new JPanel();
        lobbies = new HashMap<>();

        setField("lobbylist", lobbyPanel);
        setField("lobbies", lobbies);
    }

    @Test
    void addLobbyAddsLobbyToPanelAndMap() {
        lobbySelector.addLobby("TestLobby", 3);

        assertEquals(1, lobbyPanel.getComponentCount());
        assertTrue(lobbies.containsKey("TestLobby"));
    }

    @Test
    void updateLobbyUpdatesPlayerCount() {
        TestSwingLobby lobby = new TestSwingLobby();

        lobbies.put("TestLobby", lobby);

        lobbySelector.updateLobby("TestLobby", 5);

        assertEquals(5, lobby.lastCount);
    }

    @Test
    void removeLobbyRemovesLobbyFromPanelAndMap() {
        TestSwingLobby lobby = new TestSwingLobby();

        lobbyPanel.add(lobby);
        lobbies.put("TestLobby", lobby);

        lobbySelector.removeLobby("TestLobby");

        assertFalse(lobbies.containsKey("TestLobby"));
        assertEquals(0, lobbyPanel.getComponentCount());
    }

    @Test
    void removeUnknownLobbyDoesNotModifyMap() {
        assertThrows(
                NullPointerException.class,
                () -> lobbySelector.removeLobby("Unknown")
        );
    }

    @Test
    void updateUnknownLobbyThrowsException() {
        assertThrows(
                NullPointerException.class,
                () -> lobbySelector.updateLobby("Unknown", 10)
        );
    }

    // --------------------------------------------------------
    // Helper
    // --------------------------------------------------------

    private LobbySelector createWithoutConstructor() throws Exception {
        Field unsafeField =
                sun.misc.Unsafe.class.getDeclaredField("theUnsafe");

        unsafeField.setAccessible(true);

        sun.misc.Unsafe unsafe =
                (sun.misc.Unsafe) unsafeField.get(null);

        return (LobbySelector)
                unsafe.allocateInstance(LobbySelector.class);
    }

    private void setField(String fieldName, Object value)
            throws Exception {

        Field field =
                LobbySelector.class.getDeclaredField(fieldName);

        field.setAccessible(true);
        field.set(lobbySelector, value);
    }

    // --------------------------------------------------------
    // Test Double
    // --------------------------------------------------------

    static class TestSwingLobby extends SwingLobby {

        int lastCount;

        private TestSwingLobby() {
            super("", 0, null);
        }

        @Override
        public void setCount(int count) {
            lastCount = count;
        }
    }
}