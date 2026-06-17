package dhbw.rogue.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = createControllerWithoutConstructor();
    }

    @Test
    void sendObjectDoesNothingWhenServerConnectionIsNull() {
        assertDoesNotThrow(() -> controller.sendObject("test"));
    }

    private Controller createControllerWithoutConstructor() throws Exception {
        var unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);

        sun.misc.Unsafe unsafe =
                (sun.misc.Unsafe) unsafeField.get(null);

        return (Controller) unsafe.allocateInstance(Controller.class);
    }
}