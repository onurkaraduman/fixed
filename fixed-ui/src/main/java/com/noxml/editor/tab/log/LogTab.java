package com.noxml.editor.tab.log;

import com.noxml.logging.Logger;
import javafx.scene.control.TableView;

public class LogTab {

    private TableView tblLog;

    public LogTab(TableView tblLog) {
        this.tblLog = tblLog;
        init();
    }

    private void init() {
        Logger.getLogger(LogTab.class).addPostHandler(event -> {
            tblLog.getItems().add(event);
        });
    }
}
