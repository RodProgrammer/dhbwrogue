package dhbw.rogue.tiles;

import dhbw.rogue.utility.Settings;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LightTest {

    @Test
    void constructor_initializesWithoutException() {
        assertDoesNotThrow(() -> new Light(100, 100, 50, 120));
    }

    @Test
    void light_image_isCreated() throws Exception {
        Light light = new Light(100, 100, 50, 120);

        Field f = Light.class.getDeclaredField("light");
        f.setAccessible(true);

        BufferedImage img = (BufferedImage) f.get(light);

        assertNotNull(img);
        assertEquals(100, img.getWidth());
        assertEquals(100, img.getHeight());
    }

    @Test
    void drawLight_doesNotThrow() {
        Light light = new Light(50, 50, 30, 100);

        assertDoesNotThrow(light::drawLight);
    }

    @Test
    void render_doesNotThrow() {
        Light light = new Light(50, 50, 30, 100);

        BufferedImage canvas = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = canvas.createGraphics();

        assertDoesNotThrow(() -> light.render(g));

        g.dispose();
    }

    @Test
    void drawLight_createsNonEmptyImage() throws Exception {
        Light light = new Light(0, 0, 40, 200);

        Field f = Light.class.getDeclaredField("light");
        f.setAccessible(true);

        BufferedImage img = (BufferedImage) f.get(light);

        assertNotNull(img);

        boolean hasNonTransparentPixel = false;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (img.getRGB(x, y) != 0) {
                    hasNonTransparentPixel = true;
                    break;
                }
            }
        }

        assertTrue(hasNonTransparentPixel, "Light image should contain drawn pixels");
    }
}