package dhbw.rogue.particle;

import dhbw.rogue.utility.Settings;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TextParticleTest {

    @Test
    void constructor_initializesFields() throws Exception {
        TextParticle p = new TextParticle("Hello", 10, 20);

        Field msgField = TextParticle.class.getDeclaredField("msg");
        msgField.setAccessible(true);

        assertEquals("Hello", msgField.get(p));
    }

    @Test
    void tick_increasesTime_untilRemoval() {
        TextParticle p = new TextParticle("Test", 0, 0);

        // 60 ticks -> noch nicht entfernt
        for (int i = 0; i < 60; i++) {
            p.tick();
        }

        assertFalse(isRemoved(p));

        // 1 weiterer Tick -> remove sollte gesetzt sein
        p.tick();

        assertTrue(isRemoved(p));
    }

    @Test
    void render_doesNotCrash() {
        TextParticle p = new TextParticle("RenderTest", 50, 50);

        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        assertDoesNotThrow(() ->
                p.render(g, 0, 0)
        );

        g.dispose();
    }

    @Test
    void tick_setsRemoveAfterTime() throws Exception {
        TextParticle p = new TextParticle("Expire", 0, 0);

        for (int i = 0; i < 61; i++) {
            p.tick();
        }

        Field removeField = Particle.class.getDeclaredField("remove");
        removeField.setAccessible(true);

        assertTrue(removeField.getBoolean(p));
    }

    // ---------------- Helper ----------------

    private boolean isRemoved(TextParticle p) {
        try {
            Field f = Particle.class.getDeclaredField("remove");
            f.setAccessible(true);
            return f.getBoolean(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getIntField(Object obj, String name) {
        try {
            Field f = obj.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.getInt(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
