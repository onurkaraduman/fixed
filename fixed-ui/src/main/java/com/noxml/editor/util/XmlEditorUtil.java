package com.noxml.editor.util;

import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;

import java.util.List;

/**
 * @author Onur Karaduman
 * @since 01.11.17
 */
public class XmlEditorUtil {
    public DefaultElement getDefaultElement(Object object) {
        return null;
    }

    public static String asString(Element element) {
        List attributes = element.attributes();
        StringBuilder sb = new StringBuilder();
        sb.append(element.getName());
        sb.append(" ");
        for (Object attribute : attributes) {
            sb.append(((DefaultAttribute) attribute).getName());
            sb.append("=");
            sb.append(((DefaultAttribute) attribute).getValue());
            sb.append(" ");
        }
        return sb.toString();
    }
}
