package dhbw.rogue.functionality;

import dhbw.rogue.entity.Direction;
import dhbw.rogue.entity.Player;
import dhbw.rogue.graphics.menus.SettingsMenu;
import dhbw.rogue.particle.Particle;
import dhbw.rogue.particle.TextParticle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * This class represents the input of the keyboard to the game.
 */
public class RogueKeyListener implements KeyListener {

    private final Player player;
    private final Chat chat;
    private final SettingsMenu settingsMenu;

    private boolean chatOpened;
    private boolean optionsMenuOpened;
    private List<Particle> particles;

    /**
     * This constructor just takes in all the objects and declares the attributes with it.
     *
     * @param player        The current Player
     * @param chat          The Chat
     * @param settingsMenu   The Options Menu
     * @param particles     The particles - TODO: delete later
     */
    public RogueKeyListener(Player player, Chat chat, SettingsMenu settingsMenu, List<Particle> particles) {
        this.player = player;
        this.chat = chat;
        this.settingsMenu = settingsMenu;

        this.particles = particles;

        chatOpened = false;
        optionsMenuOpened = false;
    }

    /**
     * This method listens to keystrokes and fowards it
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(!chatOpened && !optionsMenuOpened) {
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

            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                //TODO: needs to be deleted in the release Version, it only exists for testing purposes
                particles.add(new TextParticle("Test", player.getX(), player.getY()));
            }

            if (KeyEvent.VK_T == e.getKeyCode()) {
                chatOpened = !chatOpened;
                player.removeDirection(Direction.UP);
                player.removeDirection(Direction.DOWN);
                player.removeDirection(Direction.LEFT);
                player.removeDirection(Direction.RIGHT);
            }

            if(KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                optionsMenuOpened = !optionsMenuOpened;
                player.removeDirection(Direction.UP);
                player.removeDirection(Direction.DOWN);
                player.removeDirection(Direction.LEFT);
                player.removeDirection(Direction.RIGHT);
            }

            return;
        }

        if (optionsMenuOpened) {

            if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                optionsMenuOpened = false;
            }

            settingsMenu.changePointer(e);

            return;
        }

        if (KeyEvent.VK_BACK_SPACE == e.getKeyCode()) {
            chat.deleteLetter();
        }

        if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
            chat.clearLetters();
            chatOpened = !chatOpened;
        }

        if (KeyEvent.VK_ENTER == e.getKeyCode()) {
            chatOpened = !chatOpened;
            chat.sendMessage();
        }

        if (chatOpened) {
            chat.addLetter(e);
        }
    }

    /**
     *
     * @param e the event to be processed
     */
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
    public void keyTyped(KeyEvent e) {}

    public boolean isOptionsMenuOpened() {
        return optionsMenuOpened;
    }
}
