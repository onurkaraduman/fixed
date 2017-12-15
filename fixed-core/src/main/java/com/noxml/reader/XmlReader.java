package com.noxml.reader;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Onur Karaduman
 * @since 27.10.17
 */
public class XmlReader implements Reader {

    private SAXReader reader;
    private Document document;

    private Path path;

    public XmlReader(Path path) throws DocumentException {
        this.reader = new SAXReader();
        this.path = path;
        this.document = reader.read(getAbsolutePath());
    }


    public XmlReader(String xml) throws DocumentException {
        this.reader = new SAXReader();
        this.document = reader.read(new StringReader(xml));
    }


    public Document getDocument() {
        return document;
    }

    public Path getPath() {
        return path;
    }

    public String getAbsolutePath() {
        return path.toAbsolutePath().toString();
    }

    public String loadXmlFile() throws IOException {
        InputStream inputStream = Files.newInputStream(getPath());
        return IOUtils.toString(inputStream);
    }
}
