package Core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Logger class provides a simple logger utility for recording messages to both
 * a log file and the system console. It ensures thread-safe, synchronized logging
 * and appends new log entries to the end of the log file.
 *
 * Features:
 * - Logs messages with a timestamp.
 * - Writes log entries to a file while also printing them to the console.
 * - Handles file I/O errors gracefully and outputs error messages to the system console.
 * - Employs synchronized methods to prevent concurrent access issues.
 */
public class Logger {

    /**
     * Specifies the name of the log file used by the logger to store log messages.
     * The log file contains all logged messages, appending new entries to preserve
     * existing logs.
     */
    private static final String LOG_FILE = "system_logs.txt";

    /**
     * Logs a given message to a specified log file and outputs it to the system console.
     * This method appends each log entry with a timestamp and ensures thread-safe
     * logging through synchronization. It handles file I/O errors by printing
     * error messages to the system error stream.
     *
     * @param message The message to be logged. This string will be recorded in the log file
     *                and displayed in the system console with a timestamp.
     */
    public static synchronized void log(String message) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            String logMessage = "[" + System.currentTimeMillis() + "] " + message;
            printWriter.println(logMessage);
            System.out.println(logMessage);
        } catch (IOException e) {
            System.err.println("ERROR WRITING TO LOG FILE: " + e.getMessage());
        }
    }
}