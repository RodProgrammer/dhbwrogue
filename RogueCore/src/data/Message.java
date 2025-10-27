package data;

import java.io.Serializable;

public class Message implements Serializable {

    private final String data;

    public Message(String data) {
        this.data = data;
    }

    public Message(Message message, String username) {
        this.data = "[" + username + "]: " + message.getData();
    }

    public String getData() {
        return data;
    }

}
