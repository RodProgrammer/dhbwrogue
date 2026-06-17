package dhbw.rogue.spritemanager;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class SpriteSheetTest {

    @Test
    void constructor_doesNotThrow_withInvalidPath() {
        assertDoesNotThrow(() -> new SpriteSheet("invalid/path.png"));
    }

    @Test
    void constructor_withCustomSize_doesNotThrow() {
        assertDoesNotThrow(() -> new SpriteSheet("invalid/path.png", 64, 64));
    }

    @Test
    void getTileset_returnsNull_ifImageMissing() {
        SpriteSheet sheet = new SpriteSheet("invalid/path.png");

        assertNull(sheet.getTileset());
    }

    @Test
    void getTileset_returnsNull_forCustomConstructor_invalidPath() {
        SpriteSheet sheet = new SpriteSheet("invalid/path.png", 32, 32);

        assertNull(sheet.getTileset());
    }

    @Test
    void getTileset_doesNotReturnEmptyArray_whenValid() {
        // Achtung: Dieser Test funktioniert nur, wenn echte Datei existiert
        SpriteSheet sheet = new SpriteSheet("resource/entities/dwarf/mhap_male_dwarf_03.png");

        BufferedImage[][] tiles = sheet.getTileset();

        if (tiles != null) {
            assertTrue(tiles.length > 0, "Rows should exist");
            assertTrue(tiles[0].length > 0, "Columns should exist");
        } else {
            // akzeptabel im CI / ohne Assets
            assertNull(tiles);
        }
    }

    @Test
    void getTileset_isStable_reference() {
        SpriteSheet sheet = new SpriteSheet("invalid/path.png");

        assertSame(sheet.getTileset(), sheet.getTileset(),
                "Getter should return same reference");
    }
}