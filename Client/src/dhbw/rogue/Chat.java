package dhbw.rogue;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chat {

    private final List<String> messageList;
    private final GameCanvas gameCanvas;

    public Chat(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        messageList = Collections.synchronizedList(new ArrayList<>());
    }

    public void renderChat(Graphics2D g) {
        int y = (int) ((double) gameCanvas.getHeight()* ((double) 4/6));
        for (String message : messageList) {
            g.drawString(message, 100, y);
            y -= 20;
        }
    }

    public void addMessage(String message) {
        messageList.addFirst(message);
        if (messageList.size() > 10) {
            messageList.remove(messageList.getLast());
        }
    }

}
