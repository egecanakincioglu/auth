package com.egecanakincioglu.utils.system;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String BOT_NAME = "Auth";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void info(String message) {
        log("INFO", message);
    }

    public static void warn(String message) {
        log("WARN", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    public static void command(String message) {
        log("COMMAND", message);
    }

    public static void event(String message) {
        log("EVENT", message);
    }

    public static void database(String message) {
        log("SQL", message);
    }

    private static void log(String level, String message) {
        String time = LocalDateTime.now().format(formatter);
        System.out.println(String.format("[%s] [%s] [%s] %s", time, BOT_NAME, level, message));
    }
}
