package models;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ActivityLogger {

    private static final String FILE_NAME = "activity_log.txt";

    public static void log(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {

            writer.println(LocalDateTime.now() + " - " + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
