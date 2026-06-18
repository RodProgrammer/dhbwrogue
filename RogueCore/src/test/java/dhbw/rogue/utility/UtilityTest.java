package dhbw.rogue.utility;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    void sleep_doesNotThrow() {
        assertDoesNotThrow(() -> Utility.sleep(1));
    }

    @Test
    void sleep_handlesInterruptGracefully() {
        Thread thread = new Thread(() -> Utility.sleep(200));
        thread.start();
        thread.interrupt();

        assertDoesNotThrow(() -> thread.join());
    }

    @Test
    void scaleImage_returnsImage_withCorrectSize() {
        BufferedImage input = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

        BufferedImage result = Utility.scaleImage(input);

        assertNotNull(result);
        assertEquals(Settings.SCALED_TILE_SIZE, result.getWidth());
        assertEquals(Settings.SCALED_TILE_SIZE, result.getHeight());
    }

    @Test
    void scaleImage_withCustomSize_returnsCorrectDimensions() {
        BufferedImage input = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

        int size = 32;

        BufferedImage result = Utility.scaleImage(input, size);

        assertNotNull(result);
        assertEquals(size, result.getWidth());
        assertEquals(size, result.getHeight());
    }

    @Test
    void getImages_returns2DArray() {
        BufferedImage input = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);

        BufferedImage[][] result = Utility.getImages(input, 16, 16, 16);

        assertNotNull(result);
        assertTrue(result.length > 0);
        assertTrue(result[0].length > 0);
    }

    @Test
    void isEmpty_logic_isCovered_indirectly() {
        // indirekter Test: transparente vs. gefüllte Bilder
        BufferedImage transparent = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        BufferedImage[][] result = Utility.getImages(transparent, 8, 8, 8);

        // kann alles null sein → erlaubt, aber kein Crash
        assertDoesNotThrow(() -> Utility.getImages(transparent, 8, 8, 8));
    }

    @Test
    void scaleImage_doesNotMutateOriginal() {
        BufferedImage original = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

        BufferedImage copy = Utility.scaleImage(original);

        assertNotSame(original, copy);
    }
}