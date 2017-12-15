package com.noxml.editor;

import com.noxml.editor.handler.type.XmlEventHandlerAction;
import com.noxml.editor.history.ChangeType;
import com.noxml.editor.history.XmlEditorMementoManager;
import com.noxml.editor.tab.edit.EditHandlerType;
import com.noxml.editor.tab.edit.EditTab;
import com.noxml.editor.tab.edit.pane.EditPane;
import com.noxml.editor.tab.history.HistoryTab;
import com.noxml.editor.tab.log.LogTab;
import com.noxml.editor.tab.text.TextTab;
import com.noxml.editor.tab.text.pane.TextPane;
import com.noxml.editor.tab.xml.XmlTab;
import com.noxml.editor.tab.xml.XmlTreeCell;
import com.noxml.editor.tab.xml.XmlTreeView;
import com.noxml.logging.Logger;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class XmlEditor implements Editor {

    public static Logger LOG = Logger.getLogger(XmlEditor.class);

    private XmlTab xmlTab;
    private EditTab editTab;
    private HistoryTab historyTab;
    private TextTab textTab;
    private LogTab logTab;


    public XmlEditor(String path, XmlTreeView treeView, EditPane gridPane, TableView tableView, TextPane textPane, TableView logTable) throws DocumentException, IOException {
        XmlEditorMementoManager mementoManager = new XmlEditorMementoManager();
        xmlTab = new XmlTab(path, treeView, mementoManager);
        init(path, gridPane, tableView, mementoManager, textPane, logTable);

    }

    public XmlEditor(XmlTreeView treeView, EditPane gridPane, TableView tableView, TextPane textPane, TableView logTable) throws DocumentException, IOException {
        XmlEditorMementoManager mementoManager = new XmlEditorMementoManager();
        xmlTab = new XmlTab(treeView, mementoManager);
        init(null, gridPane, tableView, mementoManager, textPane, logTable);
    }


    private void init(String path, EditPane editPane, TableView tableView, XmlEditorMementoManager mementoManager, TextPane textPane, TableView logTable) throws IOException {
        textTab = new TextTab(path, textPane);
        editTab = new EditTab(editPane);
        historyTab = new HistoryTab(tableView, mementoManager, xmlTab, textTab);
        logTab = new LogTab(logTable);
        xmlTab.getXmlTreeView().addEventHandler(XmlEventHandlerAction.ITEM_CLICK, event -> {
            editTab.laodCell((XmlTreeCell) event.getSource());
            xmlTab.setChangeType(ChangeType.REVISION);
        });
        xmlTab.getXmlTreeView().addEventHandler(XmlEventHandlerAction.ADD_FIELD, event -> {
            editTab.createNewElement();
            xmlTab.setChangeType(ChangeType.NEW_CREATION);
        });
        editTab.addEventHandler(EditHandlerType.SAVE, event -> {
            try {
                xmlTab.saveState();
                textTab.loadXml(xmlTab.toXml());
                LOG.info("Saved successfully");
            } catch (IOException e) {
                LOG.error("Error during text loading:", e);
            }
        });
        textTab.addChangeHandler(event -> {
            try {
                xmlTab.loadXmlText(textTab.getXml());
            } catch (DocumentException e) {
                LOG.error("Error during text loading:", e);
            }
        });
    }

    @Override
    public void undo() {
        xmlTab.undo();
        LOG.info("Undo successfully");

    }

    @Override
    public void redo() {
        xmlTab.redo();
        LOG.info("Redo successfully");

    }

    @Override
    public void save() throws IOException {
        String xmlPath = xmlTab.getXmlTreeView().getXmlPath();
        xmlTab.export(xmlPath);
        LOG.info("Saved successfully");
    }

    @Override
    public void saveAs(String path) throws IOException {
        xmlTab.export(path);
        LOG.info("Saved successfully");
    }

    @Override
    public void refresh() {

    }

    @Override
    public void close() {
        // any component can be used to reach the window
        Stage stage = (Stage) xmlTab.getXmlTreeView().getScene().getWindow();
        stage.close();
    }

    @Override
    public void open() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Xml files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(App.stage);
        if (file != null) {
            String xmlPath = file.getAbsolutePath();
            try {
                xmlTab.loadXml(xmlPath);
                textTab.loadXml(xmlTab.toXml());
                LOG.info("Loaded successfully");
            } catch (DocumentException e) {
                LOG.error("Error during loading xml. Path:" + xmlPath, e);
            } catch (IOException e) {
                LOG.error("Error during loading xml. Path:" + xmlPath, e);
            }
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void export(String path) throws IOException {

    }

    public String getVersion() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        return model.getProperties().getProperty("project.version");
    }
}
