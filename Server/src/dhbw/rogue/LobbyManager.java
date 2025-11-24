package dhbw.rogue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyManager {

    private final List<Lobby> lobbies;

    public LobbyManager() {
        lobbies = Collections.synchronizedList(new ArrayList<>());
    }

    public void createLobby() {

    }

}
