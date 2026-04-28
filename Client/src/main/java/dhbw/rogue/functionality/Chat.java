package dhbw.rogue.functionality;


import dhbw.rogue.data.Message;
import dhbw.rogue.graphics.GameCanvas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;


/**
 * This class represents the Chat inside the Game
 */
public class Chat {

    private final List<Message> messageList;
    private final GameCanvas gameCanvas;
    private final Stack<Character> characterStack;

    /**
     * This constructor creates the necessary objects.
     *
     * @param gameCanvas The drawing Window from the Game
     */
    public Chat(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;

        characterStack = new Stack<>();
        messageList = Collections.synchronizedList(new ArrayList<>());

        messageDeleter();
    }

    /**
     * This method draws the Chat onto the screen with the given graphics object.
     *
     * @param graphics The Graphics2D object from the Game
     */
    public void renderChat(Graphics2D graphics) {

        graphics.setColor(Color.WHITE);

        int y = (int) ((double) gameCanvas.getHeight() * ((double) 4/6));
        int lastPos = y;

        synchronized (messageList) {
            for (Message message : messageList) {
                graphics.drawString(message.getData(), 100, y);
                y -= 20;
            }
        }

        if (!characterStack.isEmpty()) {
            graphics.drawString(">: "  + createMessage(), 100, lastPos + 20);
        }
    }

    /**
     * This method adds a Message to the Chat
     *
     * @param message   Message to add
     */
    public void addMessage(Message message) {
        messageList.addFirst(message);
        if (messageList.size() > 10) {
            messageList.remove(messageList.getLast());
        }
    }

    /**
     * This Method adds a Letter to the Chatbox
     *
     * @param e Letter to add
     */
    public void addLetter(KeyEvent e) {
        char c = e.getKeyChar();
        if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE && Character.isLetterOrDigit(c) || isValidSpecialCharacter(c)) {
            characterStack.push(c);
        }
    }

    /**
     * This method sends the method to the Server while also clearing the chatbox
     */
    public void sendMessage() {
        if (!characterStack.isEmpty() && !createMessage().trim().isEmpty()) {
            gameCanvas.sendMessageToServer(new Message(createMessage(), null));
            characterStack.clear();
        } else {
            characterStack.clear();
        }
    }

    /**
     * This method deletes the last letter added.
     */
    public void deleteLetter() {
        if (!characterStack.isEmpty()) {
            characterStack.pop();
        }
    }

    /**
     * This method clears all letter from the chatbox.
     */
    public void clearLetters() {
        characterStack.clear();
    }

    /**
     * This method converts the characters from the chatbox to a String.
     *
     * @return The message from the chatbox as a String.
     */
    private String createMessage() {
        String result;
        synchronized (characterStack) {
            result = characterStack.stream().map(String::valueOf).collect(Collectors.joining());
        }
        return result;
    }

    /**
     * This method is a timer that deletes the latest message after 10 seconds.
     */
    private void messageDeleter() {
        new Thread(() -> {
            while(true) {
                int oldValue = messageList.size();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignored) {}

                if (!messageList.isEmpty() && messageList.size() >= oldValue) {
                    messageList.removeLast();
                }
            }
        }).start();
    }

    private boolean isValidSpecialCharacter(char c) {
        return " §#_-;.:*'?!/,<>^°=)(|{}&%$@€+ßöäüÖÄÜ".indexOf(c) >= 0;
    }
}
