package com.fixed.editor.handler;

import com.fixed.editor.tab.xml.XmlTreeCell;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Onur Karaduman
 * @since 03.11.17
 */
public class AddHandler implements EventHandler {
    private XmlTreeCell treeCell;
    private List<EventHandler> listeners = new ArrayList<>();

    public AddHandler(final XmlTreeCell treeCell) {
        this.treeCell = treeCell;
    }


    @Override
    public void handle(Event event) {
        for (EventHandler listener : listeners) {
            listener.handle(event);
        }
    }

    public void addEventHandler(EventHandler handler) {
        listeners.add(handler);
    }
}
