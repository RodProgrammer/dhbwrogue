package dhbw.rogue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyManager {

    private final List<Lobby> lobbies;

    public LobbyManager() {
        lobbies = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized Lobby createLobby(String lobbyName) {
        Lobby lobby = new Lobby(lobbyName);
        lobbies.add(lobby);
        return lobby;
    }

    public synchronized void removeLobby(Lobby lobby) {
        synchronized (lobbies) {
           lobbies.removeIf(s -> s.equals(lobby));
        }
    }

}
