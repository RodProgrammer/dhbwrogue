package dhbw.rogue.sound;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.*;
import java.io.File;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SoundTest {

    /**
     * Dummy Sound-Datei Pfad (existiert nicht wirklich),
     * Konstruktor sollte Fehler abfangen und nicht crashen.
     */
    @Test
    void constructor_doesNotThrow_onInvalidFile() {
        assertDoesNotThrow(() -> new Sound("invalid/path.wav"));
    }

    @Test
    void constructor_withLoop_doesNotThrow() {
        assertDoesNotThrow(() -> new Sound("invalid/start.wav", "invalid/loop.wav"));
    }

    @Test
    void playMusic_doesNotThrow_whenClipIsNull() {
        Sound sound = new Sound("invalid/path.wav");

        assertDoesNotThrow(sound::playMusic);
    }

    @Test
    void changeVolume_doesNotThrow_whenNoClipLoaded() {
        Sound sound = new Sound("invalid/path.wav");

        assertDoesNotThrow(() -> sound.changeVolume(50));
    }

    @Test
    void changeVolume_acceptsValidRange() throws Exception {
        Sound sound = new Sound("invalid/path.wav");

        sound.changeVolume(0);
        sound.changeVolume(50);
        sound.changeVolume(100);

        Field percentageField = Sound.class.getDeclaredField("currentPercentage");
        percentageField.setAccessible(true);

        int value = (int) percentageField.get(sound);

        assertTrue(value >= 0 && value <= 100);
    }

    @Test
    void privateCreateClip_throwsOrHandlesGracefully() throws Exception {
        Sound sound = new Sound("invalid/path.wav");

        // indirekt über Reflection (keine echte File nötig)
        java.lang.reflect.Method method =
                Sound.class.getDeclaredMethod("createClip", String.class);

        method.setAccessible(true);

        assertThrows(Exception.class, () ->
                method.invoke(sound, "invalid/path.wav")
        );
    }
}