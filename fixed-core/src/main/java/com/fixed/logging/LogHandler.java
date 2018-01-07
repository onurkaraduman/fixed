package com.fixed.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @author Onur Karaduman
 * @since 05.11.17
 */
public class LogHandler extends Handler {

    // TODO - get method name automatically
    @Override
    public void publish(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append(record.getMillis())
                .append(" - ")
                .append(record.getSourceClassName())
                .append("#")
                .append(record.getSourceMethodName())
                .append(" - ")
                .append(record.getMessage());
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
