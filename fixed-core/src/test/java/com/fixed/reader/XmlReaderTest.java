package com.fixed.reader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class XmlReaderTest {

    private XmlReader reader;
    private String xmlPath = "src/main/resources/xml/fix44-short.xml";

    @Before
    public void init() throws DocumentException {
        reader = new XmlReader(Paths.get(xmlPath));
    }

    @Test
    public void getDocument() throws Exception {
        Document document = reader.getDocument();
        Element rootElement = document.getRootElement();
        assertTrue(rootElement.getQName().getName().equals("fix"));
    }

    @Test
    public void getPath() throws Exception {
        Path path = reader.getPath();
        assertTrue(path.getFileName().equals(Paths.get(xmlPath).getFileName()));
    }

    @Test
    public void getAbsolutePath() throws Exception {
        String absolutePath = reader.getAbsolutePath();
        assertTrue(absolutePath.contains(xmlPath));
    }

    @Test
    public void loadXmlFile() throws Exception {
        String xmlString = reader.loadXmlFile();
        assertNotNull(xmlString);
        assertTrue(!xmlString.isEmpty());
    }

}