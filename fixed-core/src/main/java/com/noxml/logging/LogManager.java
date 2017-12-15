package com.noxml.logging;

/**
 * @author Onur Karaduman
 * @since 05.11.17
 */
public class LogManager {
    private static LogManager logManager;

    public static LogManager getLogManager() {
        if (logManager == null) {
            logManager = new LogManager();
        }
        return logManager;
    }

    public Logger getLogger(String s) {
        return Logger.getLogger(s);
    }
}
