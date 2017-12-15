package com.noxml.editor.history;

import lombok.Getter;
import lombok.Setter;
import org.dom4j.Element;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Onur Karaduman
 * @since 01.11.17
 */
@Getter
@Setter
public class XmlEditorMemento implements Serializable {
    private final String id;
    private final ChangeType changeType;
    private final Element state;
    private final Date date;

    public XmlEditorMemento(Element state, ChangeType changeType) {
        this.id = UUID.randomUUID().toString();
        this.state = state;
        this.changeType = changeType;
        date = new Date();
    }

    public String getId() {
        return id;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public Element getState() {
        return state;
    }

    public Date getDate() {
        return date;
    }

    protected Object clone() {
        return new XmlEditorMemento((Element) state.clone(), changeType);
    }
}
