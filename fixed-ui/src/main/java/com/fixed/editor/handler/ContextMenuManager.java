package com.fixed.editor.handler;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import com.fixed.editor.tab.xml.XmlTreeCell;

public class ContextMenuManager {

    private AddHandler addHandler;
    private RemoveHandler removeHandler;
    private SearchHandler searchHandler;


    public void createContextMenu(ContextMenu contextMenu, XmlTreeCell xmlTreeCell) {
        MenuItem menuItem = new MenuItem("Add Field");
        AddHandler addHandler = new AddHandler(xmlTreeCell);
        menuItem.setOnAction(addHandler);
        setAddHandler(addHandler);
        contextMenu.getItems().add(menuItem);

        MenuItem remove = new MenuItem("Remove");
        RemoveHandler removeHandler = new RemoveHandler(xmlTreeCell);
        remove.setOnAction(removeHandler);
        setRemoveHandler(removeHandler);
        contextMenu.getItems().add(remove);

        MenuItem search = new MenuItem("Search");
        SearchHandler searchHandler = new SearchHandler(xmlTreeCell);
        search.setOnAction(searchHandler);
        setSearchHandler(searchHandler);
        contextMenu.getItems().add(search);

    }

    public void setAddHandler(AddHandler addHandler) {
        this.addHandler = addHandler;
    }

    public void setRemoveHandler(RemoveHandler removeHandler) {
        this.removeHandler = removeHandler;
    }

    public void setSearchHandler(SearchHandler searchHandler) {
        this.searchHandler = searchHandler;
    }

    public AddHandler getAddHandler() {
        return addHandler;
    }

    public RemoveHandler getRemoveHandler() {
        return removeHandler;
    }

    public SearchHandler getSearchHandler() {
        return searchHandler;
    }
}
