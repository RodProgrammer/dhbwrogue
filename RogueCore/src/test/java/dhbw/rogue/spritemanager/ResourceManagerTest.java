package dhbw.rogue.spritemanager;

import dhbw.rogue.sound.Sound;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ResourceManagerTest {

    @Test
    void constructor_initializesWithoutException() {
        assertDoesNotThrow(ResourceManager::new);
    }

    @Test
    void getSound_returnsTitleMusic() {
        ResourceManager rm = new ResourceManager();

        Sound sound = rm.getSound("title_music");

        assertNotNull(sound);
    }

    @Test
    void getSound_returnsNullForUnknownKey() {
        ResourceManager rm = new ResourceManager();

        assertNull(rm.getSound("unknown_sound"));
    }

    @Test
    void multipleCalls_returnSameInstances() {
        ResourceManager rm = new ResourceManager();

        var s1 = rm.getSpritesheet("dwarf");
        var s2 = rm.getSpritesheet("dwarf");

        assertSame(s1, s2, "Spritesheets should be cached and identical");
    }

    @Test
    void constructor_doesNotCrash_evenIfResourcesMissing() {
        assertDoesNotThrow(ResourceManager::new);
    }
}