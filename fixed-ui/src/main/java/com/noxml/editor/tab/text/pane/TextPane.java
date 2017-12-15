package com.noxml.editor.tab.text.pane;

import com.noxml.ui.Component;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class TextPane extends GridPane implements Component {

    private Button save;
    private TextArea textArea;
    private String xml;
    private List<EventHandler> saveHandlers = new ArrayList<>();


    public TextPane() {
        createControls();
    }

    @Override
    public void createControls() {
        save = new Button("Save");
        save.setDisable(true);
        save.setOnAction(event -> {
            for (EventHandler saveHandler : saveHandlers) {
                saveHandler.handle(event);
            }
        });
        textArea = new TextArea("No xml");

        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (isChanged()) {
                    save.setDisable(false);
                } else {
                    save.setDisable(true);
                }
            }
        });

        textArea.prefWidthProperty().bind(widthProperty());
        textArea.prefHeightProperty().bind(heightProperty());

        add(save, 0, 0);
        add(textArea, 0, 1);

    }

    public void loadXml(String xml) {
        this.xml = xml;
        textArea.setText(xml);
    }


    public void addChangeHandler(EventHandler handler) {
        saveHandlers.add(handler);
    }

    public String getXml() {
        return textArea.getText();
    }

    private boolean isChanged() {
        if (xml != null) {
            if (xml.equals(textArea.getText())) {
                return false;
            } else {
                return true;
            }
        } else if (!textArea.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
