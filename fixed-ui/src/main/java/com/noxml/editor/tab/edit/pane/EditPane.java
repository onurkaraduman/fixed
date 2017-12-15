package com.noxml.editor.tab.edit.pane;

import com.noxml.logging.Logger;
import com.noxml.ui.Component;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import com.noxml.editor.tab.edit.EditHandler;
import com.noxml.editor.tab.edit.EditHandlerType;
import com.noxml.editor.util.XmlTreeCellHelper;
import com.noxml.editor.tab.xml.XmlTreeCell;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;

import java.util.*;

public class EditPane extends GridPane implements Component {
    public static Logger LOG = Logger.getLogger(EditPane.class);

    private XmlTreeCell treeCell;
    private Map<Control, Control> controls = new LinkedHashMap<>();
    private ControlPane controlPane;
    private Map<EditHandlerType, List<EditHandler>> handlers = new HashMap<>();
    private TablePane tablePane;
    private boolean newCreation = false;


    /**
     * Create new element
     */
    public void createNewElement() {
        newCreation = true;
        clearGridPane();
        createControls();
        createElementRow("");
    }

    @Override
    public void add(Node child, int columnIndex, int rowIndex) {
        super.add(child, columnIndex, rowIndex);
    }

    public void laodCell(XmlTreeCell treeCell) {
        newCreation = false;
        clearGridPane();
        this.treeCell = treeCell;
        createControls();
        controls.putAll(createFieldControls(treeCell));
    }

    @Override
    public void createControls() {
        controlPane = new ControlPane();
        controlPane.addEditHandler(EditHandlerType.CANCEL, event -> {
            cancel();
            List<EditHandler> editHandlers = handlers.get(EditHandlerType.CANCEL);
            if (editHandlers != null) {
                for (EditHandler editHandler : editHandlers) {
                    editHandler.handle(event);
                }
            }
        });
        controlPane.addEditHandler(EditHandlerType.SAVE, event -> {
            save();
            List<EditHandler> editHandlers = handlers.get(EditHandlerType.SAVE);
            if (editHandlers != null) {
                for (EditHandler editHandler : editHandlers) {
                    editHandler.handle(event);
                }
            }

        });
        controlPane.addEditHandler(EditHandlerType.PLUS, event -> {
            addAttribute();
            List<EditHandler> editHandlers = handlers.get(EditHandlerType.PLUS);
            if (editHandlers != null) {
                for (EditHandler editHandler : editHandlers) {
                    editHandler.handle(event);
                }
            }
        });
        controlPane.createControls();
        add(controlPane, 0, 0);
        tablePane = new TablePane(3);
        add(tablePane, 0, 2);
    }

    public void addEventHandler(EditHandlerType handlerType, EditHandler editHandler) {
        List<EditHandler> editHandlers = handlers.get(handlerType);
        if (editHandlers == null) {
            editHandlers = new ArrayList<>();
        }
        editHandlers.add(editHandler);
        handlers.put(handlerType, editHandlers);
    }

    private void createElementRow(String value) {
        TablePane tablePane = new TablePane(2);
        Label lblFieldName = new Label("Field Name:");
        tablePane.addControl(lblFieldName);
        TextField txtField = new TextField(value);
        tablePane.addControl(txtField);
        add(tablePane, 0, 1);
        controls.put(lblFieldName, txtField);
    }

    private void clearGridPane() {
        controls.clear();
        getChildren().clear();
    }

    /**
     * Add textfield to gridpane to add new attribute
     */
    private void addAttribute() {
        TextField attrName = new TextField();
        attrName.setTooltip(new Tooltip("Attribute Name"));
        TextField attrValue = new TextField();
        attrValue.setTooltip(new Tooltip("Attribute Value"));
        tablePane.addControl(attrName);
        tablePane.addControl(attrValue);
        controls.put(attrName, attrValue);
        addRemoveButton();
    }


    private void addRemoveButton() {
        Button remove = new Button("X");
        remove.setOnAction(event -> {
            tablePane.removeRow(remove);
        });
        tablePane.addControl(remove);
    }


    private Map<Control, Control> createFieldControls(XmlTreeCell xmlTreeCell) {
        Map<Control, Control> controlControlMap = new HashMap<>();
        Object item = xmlTreeCell.getItem();
        if (item instanceof Element) {
            Element element = (Element) item;
            List attributes = element.attributes();
            createElementRow(element.getQName().getName());
            for (Object attributeObj : attributes) {
                DefaultAttribute attribute = (DefaultAttribute) attributeObj;
                TextField name = new TextField(attribute.getName());
                tablePane.addControl(name);
                TextField value = new TextField(attribute.getValue());
                tablePane.addControl(value);
                controlControlMap.put(name, value);
                addRemoveButton();
            }
        }
        return controlControlMap;
    }

    private void save() {
        if (controls == null) {
            LOG.error("Controls Map couldn't be null");
            return;
        }
        Element item;
        if (newCreation) {
            item = XmlTreeCellHelper.createElement((Element) treeCell.getItem(), "");
            TreeItem treeItem = XmlTreeCellHelper.createTreeItem(item);
            treeCell.getTreeItem().getChildren().add(treeItem);
        } else {
            item = (Element) treeCell.getItem();
        }
        if (item != null && item instanceof DefaultElement && controls != null) {
            Iterator<Control> iterator = controls.keySet().iterator();
            Control lblElementname = iterator.next();
            Control textElement = controls.get(lblElementname);

            item.setName(((TextField) textElement).getText());

            item.attributes().clear();
            int controlCounter = 0;
            for (Control control : controls.keySet()) {
                if (controlCounter == 0) {
                    controlCounter++;
                    // Continue from second element
                    continue;
                }
                String name = ((TextField) control).getText();
                String value = ((TextField) controls.get(control)).getText();
                Attribute newAttr = new DefaultAttribute(null, name, value, null);
                item.attributes().add(newAttr);
                controlCounter++;
            }
        }
        clearGridPane();
    }

    public void cancel() {
        clearGridPane();
    }
}
