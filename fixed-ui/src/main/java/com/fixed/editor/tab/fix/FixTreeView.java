package com.fixed.editor.tab.fix;

import com.fixed.editor.handler.XmlEventHandler;
import com.fixed.editor.handler.type.XmlEventHandlerAction;
import com.fixed.logging.Logger;
import com.fixed.reader.XmlReader;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.tree.DefaultText;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
public class FixTreeView extends TreeView {

    public static Logger LOG = Logger.getLogger(FixTreeView.class);

    private Map<XmlEventHandlerAction, List<XmlEventHandler>> actionListMap = new HashMap<>();
    private Document document;
    private TreeItem<Object> selectedItem;
    private String xmlPath;

    private EventHandler clickHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            if (getSelectedItems() != null && getSelectedItems().size() > 0) {
                if (selectedItem != getSelectedItems().get(0)) {
                    selectedItem = getSelectedItems().get(0);
                    List<XmlEventHandler> xmlEventHandlers = actionListMap.get(XmlEventHandlerAction.ITEM_CLICK);
                    for (XmlEventHandler xmlEventHandler : xmlEventHandlers) {
                        xmlEventHandler.handle(event);
                    }
                }
            } else {
                LOG.error("SelectedItems couldn't be null");
            }
        }
    };

    private EventHandler addHandler = event -> {
        if (actionListMap != null) {
            List<XmlEventHandler> xmlEventHandlers = actionListMap.get(XmlEventHandlerAction.ADD_FIELD);
            for (XmlEventHandler xmlEventHandler : xmlEventHandlers) {
                xmlEventHandler.handle(event);
            }
        }
    };

    private EventHandler removeHandler = event -> {
        if (actionListMap != null) {
            List<XmlEventHandler> xmlEventHandlers = actionListMap.get(XmlEventHandlerAction.REMOVE_FIELD);
            for (XmlEventHandler xmlEventHandler : xmlEventHandlers) {
                xmlEventHandler.handle(event);
            }
        }
    };

    private EventHandler searchHandler = event -> {
        if (actionListMap != null) {
            List<XmlEventHandler> xmlEventHandlers = actionListMap.get(XmlEventHandlerAction.SEARCH_FIELD);
            if (xmlEventHandlers != null) {
                for (XmlEventHandler xmlEventHandler : xmlEventHandlers) {
                    xmlEventHandler.handle(event);
                }
            }
        }
    };

    public FixTreeView() {
        init();
    }

    private void init() {
        setEditable(true);
        setCellFactory(param -> {
            FixTreeCell xmlTreeCell = new FixTreeCell();
            return xmlTreeCell;
        });
    }

    public void loadXml(String path) throws DocumentException {
        xmlPath = path;
        XmlReader xmlReader = new XmlReader(Paths.get(path));
        document = xmlReader.getDocument();
        Element rootElement = document.getRootElement();
        recreateTreeItem(rootElement);
    }

    public void loadXmlText(String xmlText) throws DocumentException {
        XmlReader xmlReader = new XmlReader(xmlText);
        document = xmlReader.getDocument();
        Element rootElement = document.getRootElement();
        recreateTreeItem(rootElement);
    }


    public void addEventHandler(XmlEventHandlerAction action, XmlEventHandler listener) {
        List<XmlEventHandler> xmlEventHandlers = actionListMap.get(action);
        if (xmlEventHandlers == null) {
            xmlEventHandlers = new ArrayList<>();
        }
        xmlEventHandlers.add(listener);
        actionListMap.put(action, xmlEventHandlers);
    }

    /**
     * Convert tree view to xml
     *
     * @return
     * @throws IOException
     */
    public String toXml() throws IOException {
        refreshDocument();
        return document.asXML();

    }

    public void refreshDocument() {
        TreeItem<Object> root = getRoot();
        Element value = (Element) root.getValue();
        document.setRootElement(value);
    }

    /**
     * Creating treeItems from scratch with given root element
     *
     * @param element
     */
    public void recreateTreeItem(Element element) {
        setRoot(convertToTreeItem(element));
    }

    public Element getRootElement() {
        return (Element) getRoot().getValue();
    }

    public Document getDocument() {
        refreshDocument();
        return document;
    }

    public List<TreeItem> getSelectedItems() {
        return (List<TreeItem>) getSelectionModel().getSelectedItems().stream().collect(Collectors.toList());
    }


    public String getXmlPath() {
        return xmlPath;
    }

    /**
     * Creating treeItems from xml element with recursion
     *
     * @param element
     * @return
     */
    private TreeItem<Object> convertToTreeItem(Object element) {
        TreeItem<Object> treeItem = new TreeItem<>(element);
        if (element instanceof Element) {
            for (Object o : ((Element) element).content()) {
                if (o instanceof DefaultText) {
                    DefaultText defaultText = (DefaultText) o;
                    String text = defaultText.getText();
                    String newline = System.getProperty("line.separator");
                    if (!text.isEmpty() && !text.contains(newline)) {
                        TreeItem<Object> treeTextItem = new TreeItem<>(o);
                        treeItem.getChildren().add(treeItem);
                    }
                }
                if (o instanceof Element) {
                    treeItem.getChildren().add(convertToTreeItem(o));
                }
            }
        }
        return treeItem;
    }
}
