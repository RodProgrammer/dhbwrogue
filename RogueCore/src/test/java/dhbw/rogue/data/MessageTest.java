package dhbw.rogue.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void constructorWithoutPlayerSetsDataCorrectly() {
        Message message = new Message("Test", null);

        assertEquals("Test", message.getData());
        assertNull(message.getPlayer());
    }

    @Test
    void formattedMessageConstructorAddsUsernamePrefix() {
        Message original = new Message("Hello world", null);

        Message formatted = new Message(original, "Alice");

        assertEquals("[Alice]: Hello world", formatted.getData());
    }

    @Test
    void formattedMessageConstructorWorksWithNullUsername() {
        Message original = new Message("Hi", null);

        Message formatted = new Message(original, null);

        assertEquals("[null]: Hi", formatted.getData());
    }
}