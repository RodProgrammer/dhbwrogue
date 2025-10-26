package dhbw.rogue;

import entity.Direction;
import entity.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RogueKeyListener implements KeyListener {

    private final Player player;

    private boolean chatOpened;

    public RogueKeyListener(Player player) {
        this.player = player;
        chatOpened = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!chatOpened) {
            if (KeyEvent.VK_W == e.getKeyCode()) {
                player.addDirection(Direction.UP);
            }

            if (KeyEvent.VK_S == e.getKeyCode()) {
                player.addDirection(Direction.DOWN);
            }

            if (KeyEvent.VK_A == e.getKeyCode()) {
                player.addDirection(Direction.LEFT);
            }

            if (KeyEvent.VK_D == e.getKeyCode()) {
                player.addDirection(Direction.RIGHT);
            }
        }

        if (KeyEvent.VK_ENTER == e.getKeyCode()) {
            chatOpened = !chatOpened;
            player.removeDirection(Direction.UP);
            player.removeDirection(Direction.DOWN);
            player.removeDirection(Direction.LEFT);
            player.removeDirection(Direction.RIGHT);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!chatOpened) {
            if (KeyEvent.VK_W == e.getKeyCode()) {
                player.removeDirection(Direction.UP);
            }

            if (KeyEvent.VK_S == e.getKeyCode()) {
                player.removeDirection(Direction.DOWN);
            }

            if (KeyEvent.VK_A == e.getKeyCode()) {
                player.removeDirection(Direction.LEFT);
            }

            if (KeyEvent.VK_D == e.getKeyCode()) {
                player.removeDirection(Direction.RIGHT);
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
