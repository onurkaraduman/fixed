package com.noxml.editor;

import java.io.IOException;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
public interface Editor {
    void undo();

    void redo();

    void save() throws IOException;

    void saveAs(String path) throws IOException;

    void refresh();

    void close();

    void open();

    void help();

    void clear();

    void export(String path) throws IOException;
}
