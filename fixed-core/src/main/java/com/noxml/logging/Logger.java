package com.noxml.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * TODO - create custom our own logger
 *
 * @author Onur Karaduman
 * @since 05.11.17
 */
public class Logger {

    private java.util.logging.Logger loggerRoot;
    private static List<LogEventHandler> logEventHandlers = new ArrayList<>();

    public static Logger getLogger(Class cl) {
        return getLogger(cl.getName());
    }

    public static Logger getLogger(String s) {
        Logger logger = new Logger();
        logger.loggerRoot = java.util.logging.Logger.getLogger(s);
        logger.addCustomHandler();
        return logger;
    }

    public void info(String message) {
        loggerRoot.info(message);
        handle(LogHandlerType.INFO, message, null);
    }

    public void error(String message, Throwable throwable) {
        loggerRoot.log(Level.SEVERE, message, throwable);
        handle(LogHandlerType.ERROR, message, throwable);
    }

    public void debug(String message) {
        loggerRoot.log(Level.FINE, message);
        handle(LogHandlerType.DEBUG, message, null);
    }

    private void addCustomHandler() {
        loggerRoot.addHandler(new LogHandler());
    }

    public void error(String message) {
        loggerRoot.log(Level.SEVERE, message);
        handle(LogHandlerType.ERROR, message, null);
    }

    public void addPostHandler(LogEventHandler handler) {
        logEventHandlers.add(handler);
    }

    private void handle(LogHandlerType handlerType, String message, Throwable throwable) {
        for (LogEventHandler logEventHandler : logEventHandlers) {
            logEventHandler.handle(new LogEvent(handlerType, message, throwable));
        }
    }
}
