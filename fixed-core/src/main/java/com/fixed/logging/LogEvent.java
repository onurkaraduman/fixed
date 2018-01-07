package com.fixed.logging;

import java.util.Date;

public class LogEvent {
    private LogHandlerType handlerType;
    private Date date;
    private String message;
    private Throwable throwable;

    public LogEvent(LogHandlerType handlerType, String message, Throwable throwable) {
        date = new Date();
        this.handlerType = handlerType;
        this.message = message;
        this.throwable = throwable;
    }

    public LogHandlerType getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(LogHandlerType handlerType) {
        this.handlerType = handlerType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
