package dhbw.rogue.graphics;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LightRendererTest {

    @Test
    void constructorWithNullTileMapCreatesNoLightMap() throws Exception {
        LightRenderer renderer = new LightRenderer(null);

        assertNull(getLightMap(renderer));
    }

    private BufferedImage getLightMap(LightRenderer renderer)
            throws Exception {

        Field field =
                LightRenderer.class.getDeclaredField("lightMap");

        field.setAccessible(true);

        return (BufferedImage) field.get(renderer);
    }
}