package com.fixed.editor;

import java.io.IOException;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
public interface Editor {
    void undoDictionary();

    void redoDictionary();

    void saveDictionary() throws IOException;

    void saveAsDictionary(String path) throws IOException;

    void refresh();

    void close();

    void openDictionary();

    void help();

    void clear();

    void export(String path) throws IOException;
}
