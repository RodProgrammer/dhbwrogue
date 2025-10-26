package dhbw.rogue;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Chat {

    private final List<String> messageList;
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
        for (String message : messageList) {
            g.drawString(message, 100, y);
            y -= 20;
        }

        if (!characterStack.isEmpty()) {
            g.drawString(createString(), 100, lastPos + 20);
        }
    }

    public void addMessage(String message) {
        messageList.addFirst(message);
        if (messageList.size() > 10) {
            messageList.remove(messageList.getLast());
        }
    }

    public void addLetter(KeyEvent e) {
        char c = e.getKeyChar();
        characterStack.push(c);
    }

    public void sendMessage() {
        gameCanvas.sendMessageToServer("Message: " + createString());
        characterStack.clear();
    }

    private String createString() {
        StringBuilder message = new StringBuilder();
        characterStack.elements().asIterator().forEachRemaining(message::append);
        return message.toString();
    }

}
