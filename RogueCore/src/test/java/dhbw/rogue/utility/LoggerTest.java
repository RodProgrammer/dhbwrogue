package dhbw.rogue.utility;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    @Test
    void singleton_instance_exists() {
        assertNotNull(Logger.logger);
    }

    @Test
    void constructor_createsFilename() throws Exception {
        Field f = Logger.class.getDeclaredField("filename");
        f.setAccessible(true);

        String filename = (String) f.get(Logger.logger);

        assertNotNull(filename);
        assertTrue(filename.startsWith("log"));
        assertTrue(filename.endsWith(".txt"));
    }

    @Test
    void logInfo_doesNotThrow() {
        assertDoesNotThrow(() ->
                Logger.logInfo("Test info")
        );
    }

    @Test
    void logWarning_doesNotThrow() {
        assertDoesNotThrow(() ->
                Logger.logWarning("Test warning")
        );
    }

    @Test
    void logError_doesNotThrow() {
        assertDoesNotThrow(() ->
                Logger.logError("Test error")
        );
    }

    @Test
    void saveLog_createsFile_contentIsWritten() throws Exception {
        Logger.logInfo("JUnit test message");

        // filename aus Singleton holen
        Field f = Logger.class.getDeclaredField("filename");
        f.setAccessible(true);

        String filename = (String) f.get(Logger.logger);

        Path path = Path.of(filename);

        assertTrue(Files.exists(path), "Log file should exist");

        String content = Files.readString(path);

        assertTrue(content.contains("JUnit test message"));
    }

    @Test
    void multiple_logs_append_to_file() throws Exception {
        Logger.logInfo("First message");
        Logger.logInfo("Second message");

        Field f = Logger.class.getDeclaredField("filename");
        f.setAccessible(true);

        String filename = (String) f.get(Logger.logger);

        String content = Files.readString(Path.of(filename));

        assertTrue(content.contains("First message"));
        assertTrue(content.contains("Second message"));
    }

    @Test
    void logger_is_singleton() {
        Logger a = Logger.logger;
        Logger b = Logger.logger;

        assertSame(a, b);
    }
}