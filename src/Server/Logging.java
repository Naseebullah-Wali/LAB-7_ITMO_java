package Server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.*;

public class Logging {
    private Logger logger;
    public Handler fileHandler;
    Formatter plainText;

    Logging() {
        logger = Logger.getLogger(Logging.class.getName());
        String logFile = "myLogServer.txt";
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(logFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        printWriter.print("");
        printWriter.close();
        try {
            fileHandler = new FileHandler(logFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        plainText = new SimpleFormatter();
        fileHandler.setFormatter(plainText);
        logger.addHandler(fileHandler);
    }

    public void log(Level level, String msg){
        logger.log(level, msg);
    }

    public void info(String msg) {
        log(Level.INFO, msg);
    }
}