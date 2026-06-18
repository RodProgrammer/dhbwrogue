package dhbw.rogue.graphics;

import dhbw.rogue.data.Message;
import dhbw.rogue.functionality.Chat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameCanvasTest {

    private GameCanvas canvas;

    @BeforeEach
    void setUp() throws Exception {
        canvas = createWithoutConstructor();

        setField(canvas, "players",
                Collections.synchronizedList(new ArrayList<>()));

        setField(canvas, "entities",
                Collections.synchronizedList(new ArrayList<>()));

        setField(canvas, "informationMessages",
                Collections.synchronizedList(new ArrayList<>()));

        setField(canvas, "chat", new TestChat());
    }

    @Test
    void addInformationMessageAddsMessage() throws Exception {
        canvas.addInformationMessage("Hello");

        List<String> messages =
                getField("informationMessages");

        assertEquals(1, messages.size());
        assertEquals("Hello", messages.getFirst());
    }

    @Test
    void addChatMessageDelegatesToChat() throws Exception {
        TestChat chat = getField("chat");

        Message message = new Message("test", null);

        canvas.addChatMessage(message);

        assertSame(message, chat.lastMessage);
    }

    // --------------------------------------------------------
    // Reflection Helper
    // --------------------------------------------------------

    @SuppressWarnings("unchecked")
    private <T> T getField(String name) throws Exception {
        Field field = GameCanvas.class.getDeclaredField(name);
        field.setAccessible(true);
        return (T) field.get(canvas);
    }

    private void setField(Object target,
                          String name,
                          Object value) throws Exception {

        Field field = GameCanvas.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    private GameCanvas createWithoutConstructor() throws Exception {
        Field f = sun.misc.Unsafe.class
                .getDeclaredField("theUnsafe");

        f.setAccessible(true);

        sun.misc.Unsafe unsafe =
                (sun.misc.Unsafe) f.get(null);

        return (GameCanvas)
                unsafe.allocateInstance(GameCanvas.class);
    }

    // --------------------------------------------------------
    // Test Doubles
    // --------------------------------------------------------

    static class TestChat extends Chat {

        Message lastMessage;

        TestChat() {
            super(null);
        }

        @Override
        public void addMessage(Message message) {
            lastMessage = message;
        }
    }
}