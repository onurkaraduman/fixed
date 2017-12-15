package com.noxml.editor.handler;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
@FunctionalInterface
public interface XmlEventHandler extends EventHandler{

    void handle(Event event);
}
