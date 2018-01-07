package com.fixed.editor.handler;

import com.fixed.editor.tab.xml.XmlTreeCell;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Onur Karaduman
 * @since 03.11.17
 */
public class RemoveHandler implements EventHandler {

    private XmlTreeCell treeCell;
    private List<EventHandler> listeners = new ArrayList<>();

    public RemoveHandler(final XmlTreeCell treeCell) {
        this.treeCell = treeCell;
    }

    @Override
    public void handle(Event event) {
        TreeItem treeItem = treeCell.getTreeItem();
        treeItem.getParent().getChildren().remove(treeItem);
        Element item = (Element) treeCell.getItem();
        item.getParent().content().remove(item);
    }

    public void addEventHandler(EventHandler handler) {
        listeners.add(handler);
    }
}
