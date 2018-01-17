package com.fixed.editor.tab.dictionary;


import com.fixed.editor.tab.dictionary.pane.TextPane;
import javafx.event.EventHandler;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class TextTab {

    private TextPane textPane;


    public TextTab(TextPane textPane) {
        this.textPane = textPane;
    }

    public TextTab(String path, TextPane textPane) throws IOException {
        this.textPane = textPane;
        if (path != null && !path.isEmpty()) {
            this.textPane.loadXml(IOUtils.toString(new FileInputStream(path)));
        }
    }


    public void loadXml(String xml) {
        textPane.loadXml(xml);
    }

    public void addChangeHandler(EventHandler handler) {
        textPane.addChangeHandler(handler);
    }

    public String getXml() {
        return textPane.getXml();
    }
}
