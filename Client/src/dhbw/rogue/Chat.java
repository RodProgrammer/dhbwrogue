package dhbw.rogue;


import data.Message;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Chat {

    private final List<Message> messageList;
    private final GameCanvas gameCanvas;
    private Stack<Character> characterStack;

    public Chat(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;

        characterStack = new Stack<>();
        messageList = Collections.synchronizedList(new ArrayList<>());
    }

    public void renderChat(Graphics2D g) {

        g.setColor(Color.WHITE);

        int y = (int) ((double) gameCanvas.getHeight()* ((double) 4/6));
        int lastPos = y;

        synchronized (messageList) {
            for (Message message : messageList) {
                g.drawString(message.getData(), 100, y);
                y -= 20;
            }
        }

        if (!characterStack.isEmpty()) {
            g.drawString(createMessage(), 100, lastPos + 20);
        }
    }

    public void addMessage(Message message) {
        messageList.addFirst(message);
        if (messageList.size() > 10) {
            messageList.remove(messageList.getLast());
        }
    }

    public void addLetter(KeyEvent e) {
        char c = e.getKeyChar();
        if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
            characterStack.push(c);
        }
    }

    public void sendMessage() {
        if(!characterStack.isEmpty() && !createMessage().trim().isEmpty()) {
            gameCanvas.sendMessageToServer(new Message(createMessage()));
            characterStack.clear();
        } else {
            characterStack.clear();
        }
    }

    public void deleteLetter() {
        if (!characterStack.isEmpty()) {
            char g = characterStack.pop();
        }
    }

    public void clearLetters() {
        characterStack.clear();
    }

    private String createMessage() {
        return characterStack.stream().map(String::valueOf).collect(Collectors.joining());
    }

}
