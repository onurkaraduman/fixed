package com.fixed.editor.tab.edit.pane;

import com.fixed.logging.Logger;
import com.fixed.ui.Component;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import com.fixed.editor.tab.edit.EditHandler;
import com.fixed.editor.tab.edit.EditHandlerType;
import com.fixed.editor.tab.xml.XmlTreeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlPane extends GridPane implements Component {

    public static Logger LOG = Logger.getLogger(XmlTreeView.class);

    private Button btnSave;
    private Button btnCancel;
    private Button btnPlus;
    private Map<EditHandlerType, List<EditHandler>> handlers = new HashMap<>();


    public void addEditHandler(EditHandlerType handlerType, EditHandler editHandler) {
        List<EditHandler> editHandlers = handlers.get(handlerType);
        if (editHandlers == null) {
            editHandlers = new ArrayList<>();
        }
        editHandlers.add(editHandler);
        handlers.put(handlerType, editHandlers);
    }

    @Override
    public void createControls() {
        btnPlus = new Button("Save");
        btnPlus.setOnAction(event -> {
            List<EditHandler> editHandlers = handlers.get(EditHandlerType.SAVE);
            for (EditHandler editHandler : editHandlers) {
                editHandler.handle(event);
            }
        });
        add(btnPlus, 0, 0);

        btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> {
            List<EditHandler> editHandlers = handlers.get(EditHandlerType.CANCEL);
            for (EditHandler editHandler : editHandlers) {
                editHandler.handle(event);
            }
        });
        add(btnCancel, 1, 0);
        Button plusButton = new Button("+");
        plusButton.setOnAction(event -> {
            List<EditHandler> editHandlers = handlers.get(EditHandlerType.PLUS);
            for (EditHandler editHandler : editHandlers) {
                editHandler.handle(event);
            }
        });
        add(plusButton, 2, 0);
    }
}
