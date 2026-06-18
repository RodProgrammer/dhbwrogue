package dhbw.rogue.tiles;

import dhbw.rogue.utility.Settings;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TestLightTileTest {

    @Test
    void constructor_initializesWithoutException() {
        assertDoesNotThrow(() -> new TestLightTile(10, 20));
    }

    @Test
    void constructor_setsCollision() throws Exception {
        TestLightTile tile = new TestLightTile(0, 0);

        Field collisionField = Tile.class.getDeclaredField("hasCollision");
        collisionField.setAccessible(true);

        assertTrue(collisionField.getBoolean(tile));
    }

    @Test
    void constructor_createsLight() throws Exception {
        TestLightTile tile = new TestLightTile(0, 0);

        Field lightField = Tile.class.getDeclaredField("light");
        lightField.setAccessible(true);

        Light light = (Light) lightField.get(tile);

        assertNotNull(light);
    }

    @Test
    void draw_doesNotThrow() {
        TestLightTile tile = new TestLightTile(0, 0);

        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        assertDoesNotThrow(() -> tile.draw(g, 50, 50));

        g.dispose();
    }

    @Test
    void reloadLight_replacesLightInstance() throws Exception {
        TestLightTile tile = new TestLightTile(0, 0);

        Field lightField = Tile.class.getDeclaredField("light");
        lightField.setAccessible(true);

        Light before = (Light) lightField.get(tile);

        tile.reloadLight();

        Light after = (Light) lightField.get(tile);

        assertNotSame(before, after, "reloadLight should create new Light instance");
    }

    @Test
    void reloadLight_doesNotCrash() {
        TestLightTile tile = new TestLightTile(0, 0);

        assertDoesNotThrow(tile::reloadLight);
    }

    @Test
    void tick_doesNothing_butDoesNotCrash() {
        TestLightTile tile = new TestLightTile(0, 0);

        assertDoesNotThrow(tile::tick);
    }

    @Test
    void draw_rendersWithoutException() {
        TestLightTile tile = new TestLightTile(100, 100);

        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        assertDoesNotThrow(() -> tile.draw(g, 10, 10));

        g.dispose();
    }
}