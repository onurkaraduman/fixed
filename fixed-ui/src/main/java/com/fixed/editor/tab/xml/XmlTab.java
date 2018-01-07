package com.fixed.editor.tab.xml;

import com.fixed.editor.history.ChangeType;
import com.fixed.editor.history.XmlEditorMemento;
import com.fixed.editor.history.XmlEditorMementoManager;
import javafx.scene.control.TreeItem;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
public class XmlTab {


    private XmlTreeView xmlTreeView;
    private XmlEditorMementoManager mementoManager;
    private ChangeType changeType = ChangeType.OPEN;

    /**
     * Open xml file with given path
     *
     * @param path
     */
    public XmlTab(String path, XmlTreeView treeView, XmlEditorMementoManager mementoManager) throws DocumentException {
        init();
        this.xmlTreeView = treeView;
        this.mementoManager = mementoManager;
        if (path != null && !path.isEmpty()) {
            loadXml(path);
        }
    }

    public XmlTab(XmlTreeView treeView, XmlEditorMementoManager mementoManager) throws DocumentException {
        this.xmlTreeView = treeView;
        this.mementoManager = mementoManager;
        init();
    }

    public XmlTab() {
        init();
    }

    private void init() {
        if (xmlTreeView == null) {
            xmlTreeView = new XmlTreeView();
        }
    }

    public void add() {

    }

    /**
     * Remove item from tree and remove element from parent node
     */
    public void remove() {
        List<TreeItem> selectedItems = xmlTreeView.getSelectedItems();
        for (TreeItem selectedItem : selectedItems) {
            selectedItem.getParent().getChildren().remove(selectedItem);
            ((Element) selectedItem.getParent().getValue()).remove((Element) selectedItem.getValue());
        }
    }

    public XmlTreeView getXmlTreeView() {
        return xmlTreeView;
    }

    public void undo() {
        xmlTreeView.recreateTreeItem(mementoManager.undo().getState());
    }

    public void redo() {
        xmlTreeView.recreateTreeItem(mementoManager.redo().getState());
    }

    public void saveState() {
        mementoManager.save(xmlTreeView.getRootElement(), changeType);
    }

    public void refresh() {
        xmlTreeView.recreateTreeItem(mementoManager.getCurrentState().getState());
    }


    public void export(String path) throws IOException {
        if (xmlTreeView.getDocument() != null) {
            try (OutputStream outputStream = new FileOutputStream(path)) {
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer;
                writer = new XMLWriter(outputStream, format);
                writer.write(xmlTreeView.getDocument());
            }
        }
    }

    public void loadXml(String path) throws DocumentException {
        xmlTreeView.loadXml(path);
        saveState();
    }

    public void loadXmlText(String xml) throws DocumentException {
        xmlTreeView.loadXmlText(xml);
        saveState();
    }

    public void setStateById(String stateId) {
        XmlEditorMemento state = mementoManager.findStateById(stateId);
        xmlTreeView.recreateTreeItem(state.getState());
    }

    public void setState(XmlEditorMemento memento) {
        xmlTreeView.recreateTreeItem(memento.getState());
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public String toXml() throws IOException {
        return xmlTreeView.toXml();
    }
}
