package com.fixed.editor.history;

public class MementoChangeEvent {
    private XmlEditorMemento currentState;
    private XmlEditorMemento previousState;


    public MementoChangeEvent(XmlEditorMemento currentState, XmlEditorMemento previousState) {
        this.currentState = currentState;
        this.previousState = previousState;
    }

    public XmlEditorMemento getCurrentState() {
        return currentState;
    }

    public void setCurrentState(XmlEditorMemento currentState) {
        this.currentState = currentState;
    }

    public XmlEditorMemento getPreviousState() {
        return previousState;
    }

    public void setPreviousState(XmlEditorMemento previousState) {
        this.previousState = previousState;
    }
}
