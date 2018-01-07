package com.fixed.editor.util;

import javafx.scene.control.TreeItem;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

import java.util.List;

public class XmlTreeCellHelper {

    public static Element createElement(String elementName, List<Attribute> attributes) {
        Element element = DocumentHelper.createElement(elementName);
        element.setAttributes(attributes);
        return element;
    }

    public static Element createElement(Element parent, String name) {
        Element element = DocumentHelper.createElement(name);
        element.setParent(parent);
        parent.content().add(element);
        return element;
    }

    public static TreeItem createTreeItem(Element element) {
        return new TreeItem(element);
    }

    public static Attribute createAttribute(String name, String value) {
        return new DefaultAttribute(null, name, value, null);
    }
}
