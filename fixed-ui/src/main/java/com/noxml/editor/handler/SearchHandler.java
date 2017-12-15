package com.noxml.editor.handler;

import com.noxml.editor.tab.xml.XmlTreeCell;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Onur Karaduman
 * @since 03.11.17
 */
public class SearchHandler implements EventHandler {

    private XmlTreeCell treeCell;
    private List<EventHandler> listeners = new ArrayList<>();

    public SearchHandler(XmlTreeCell treeCell) {
        this.treeCell = treeCell;
    }

    @Override
    public void handle(Event event) {

    }

    public void addEventHandler(EventHandler handler) {
        listeners.add(handler);
    }
}
