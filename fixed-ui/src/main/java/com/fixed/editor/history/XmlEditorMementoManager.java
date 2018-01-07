package com.fixed.editor.history;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Onur Karaduman
 * @since 01.11.17
 */
public class XmlEditorMementoManager {

    private XmlEditorMemento currentState;
    private List<XmlEditorMemento> mementoList;
    private List<MementoStateChangeListener> handlers = new ArrayList<>();


    public XmlEditorMementoManager() {
        mementoList = new ArrayList<>();
    }

    public void save(Element element, ChangeType changeType) {
        XmlEditorMemento previousState = null;
        if (currentState != null) {
            previousState = (XmlEditorMemento) currentState.clone();
        }
        currentState = new XmlEditorMemento((Element) element.clone(), changeType);
        mementoList.add(currentState);
        handle(currentState, previousState);

    }

    public XmlEditorMemento getCurrentState() {
        return currentState;
    }

    public XmlEditorMemento undo() {
        int i = mementoList.indexOf(currentState);
        XmlEditorMemento memento = null;
        if (i > 0) {
            memento = mementoList.get(i - 1);
        } else {
            memento = mementoList.get(0);
        }
        XmlEditorMemento previousState = (XmlEditorMemento) currentState.clone();
        currentState = memento;
        handle(currentState, previousState);
        return memento;
    }

    public XmlEditorMemento redo() {
        XmlEditorMemento memento = null;
        int i = mementoList.indexOf(currentState);
        if (i == mementoList.size() - 1) {
            memento = mementoList.get(i);
        } else {
            memento = mementoList.get(i + 1);
        }
        XmlEditorMemento previousState = (XmlEditorMemento) currentState.clone();
        currentState = memento;
        handle(currentState, previousState);
        return memento;
    }

    public XmlEditorMemento findStateById(String id) {
        return mementoList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    public List<XmlEditorMemento> getMementoList() {
        return mementoList;
    }

    public void addStateChangeHandler(MementoStateChangeListener handler) {
        handlers.add(handler);
    }

    private void handle(XmlEditorMemento currentState, XmlEditorMemento previousState) {
        for (MementoStateChangeListener handler : handlers) {
            MementoChangeEvent event = new MementoChangeEvent(currentState, previousState);
            handler.handle(event);
        }
    }
}
