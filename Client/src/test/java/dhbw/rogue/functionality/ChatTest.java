package dhbw.rogue.functionality;

import dhbw.rogue.data.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    private Chat chat;

    @BeforeEach
    void setUp() {
        chat = new Chat(null);
    }

    @Test
    void addMessageAddsMessageToList() throws Exception {
        Message message = new Message("Hello", null);

        chat.addMessage(message);

        List<Message> messages = getMessageList();

        assertEquals(1, messages.size());
        assertSame(message, messages.getFirst());
    }

    @Test
    void addMessageKeepsMaximumTenMessages() throws Exception {
        for (int i = 0; i < 11; i++) {
            chat.addMessage(new Message("msg" + i, null));
        }

        List<Message> messages = getMessageList();

        assertEquals(10, messages.size());
    }

    @Test
    void addLetterAddsNormalCharacter() throws Exception {
        KeyEvent event = new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'A'
        );

        chat.addLetter(event);

        Stack<Character> stack = getCharacterStack();

        assertEquals(1, stack.size());
        assertEquals('A', stack.peek());
    }

    @Test
    void addLetterAddsSpecialCharacter() throws Exception {
        KeyEvent event = new KeyEvent(
                new Canvas(),
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                '!'
        );

        chat.addLetter(event);

        assertEquals(1, getCharacterStack().size());
    }

    @Test
    void deleteLetterRemovesLastCharacter() throws Exception {
        getCharacterStack().push('A');
        getCharacterStack().push('B');

        chat.deleteLetter();

        Stack<Character> stack = getCharacterStack();

        assertEquals(1, stack.size());
        assertEquals('A', stack.peek());
    }

    @Test
    void deleteLetterOnEmptyStackDoesNothing() {
        assertDoesNotThrow(() -> chat.deleteLetter());
    }

    @Test
    void clearLettersRemovesAllCharacters() throws Exception {
        Stack<Character> stack = getCharacterStack();

        stack.push('A');
        stack.push('B');
        stack.push('C');

        chat.clearLetters();

        assertTrue(stack.isEmpty());
    }

    @Test
    void sendMessageWithEmptyStackClearsNothingAndDoesNotThrow() {
        assertDoesNotThrow(() -> chat.sendMessage());
    }

    @SuppressWarnings("unchecked")
    private List<Message> getMessageList() throws Exception {
        Field field = Chat.class.getDeclaredField("messageList");
        field.setAccessible(true);
        return (List<Message>) field.get(chat);
    }

    @SuppressWarnings("unchecked")
    private Stack<Character> getCharacterStack() throws Exception {
        Field field = Chat.class.getDeclaredField("characterStack");
        field.setAccessible(true);
        return (Stack<Character>) field.get(chat);
    }
}