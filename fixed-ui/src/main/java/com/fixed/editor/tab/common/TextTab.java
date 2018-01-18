package com.fixed.editor.tab.common;


import com.fixed.editor.tab.common.pane.TextPane;
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
            this.textPane.loadContent(IOUtils.toString(new FileInputStream(path)));
        }
    }


    public void loadXml(String xml) {
        textPane.loadContent(xml);
    }

    public void addChangeHandler(EventHandler handler) {
        textPane.addChangeHandler(handler);
    }

    public String getXml() {
        return textPane.getContent();
    }
}
