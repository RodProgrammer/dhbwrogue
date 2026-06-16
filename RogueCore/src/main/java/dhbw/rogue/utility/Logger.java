package dhbw.rogue.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Logger {

    public final static Logger logger = new Logger();
    
    private boolean worked;

    private String filename;
    
    private Logger() {
        try {
            this.filename = "log" + System.currentTimeMillis() + ".txt";
            File logFile = new File(filename);
            this.worked = logFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String message) {
        String temp = "[INFO]: " + message + System.lineSeparator();
        System.out.println(temp);
        logger.saveLog(temp);
    }

    public static void logWarning(String message) {
        String temp = "[WARNING]: " + message + System.lineSeparator();
        System.out.println(temp);
        logger.saveLog(temp);
    }

    public static void logError(String message) {
        String temp = "[ERROR]: " + message + System.lineSeparator();
        System.err.println(temp);
        logger.saveLog(temp);
    }

    private void saveLog(String message) {
        if (!worked) return;

        try {
            Files.writeString(Path.of(this.filename), message, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("[ERROR]: " + e.getMessage());
        }
    }

}
