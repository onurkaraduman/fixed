package com.fixed.editor;

import com.fixed.editor.handler.type.XmlEventHandlerAction;
import com.fixed.editor.history.ChangeType;
import com.fixed.editor.history.XmlEditorMementoManager;
import com.fixed.editor.tab.edit.EditHandlerType;
import com.fixed.editor.tab.edit.EditTab;
import com.fixed.editor.tab.edit.pane.EditPane;
import com.fixed.editor.tab.fix.FixTab;
import com.fixed.editor.tab.fix.FixTreeTableView;
import com.fixed.editor.tab.history.HistoryTab;
import com.fixed.editor.tab.log.LogTab;
import com.fixed.editor.tab.common.TextTab;
import com.fixed.editor.tab.common.pane.TextPane;
import com.fixed.editor.tab.xml.XmlTab;
import com.fixed.editor.tab.xml.XmlTreeCell;
import com.fixed.editor.tab.xml.XmlTreeView;
import com.fixed.logging.Logger;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.dom4j.DocumentException;
import quickfix.ConfigError;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FixEditor implements Editor {

	public static Logger LOG = Logger.getLogger(FixEditor.class);

	private XmlTab xmlTab;
	private EditTab editTab;
	private HistoryTab historyTab;
	private TextTab dictionaryTab;
	private LogTab logTab;
	private FixTab fixTab;
	private TextTab fixMessageTab;

	public FixEditor(String dictionaryPath, XmlTreeView treeView, EditPane gridPane, TableView tableView, TextPane dictionaryPane, TableView logTable,
			FixTreeTableView fixTreeView, String fixMessagePath, TextPane fixMessagePane)
			throws DocumentException, IOException {
		XmlEditorMementoManager mementoManager = new XmlEditorMementoManager();
		xmlTab = new XmlTab(dictionaryPath, treeView, mementoManager);
		init(dictionaryPath, gridPane, tableView, mementoManager, dictionaryPane, logTable, fixTreeView, fixMessagePath, fixMessagePane);

	}

	public FixEditor(XmlTreeView treeView, EditPane gridPane, TableView tableView, TextPane dictionaryPane, TableView logTable, FixTreeTableView fixTreeView,
			String fixMessagePath, TextPane fixMessagePane)
			throws DocumentException, IOException {
		XmlEditorMementoManager mementoManager = new XmlEditorMementoManager();
		xmlTab = new XmlTab(treeView, mementoManager);
		init(null, gridPane, tableView, mementoManager, dictionaryPane, logTable, fixTreeView, fixMessagePath, fixMessagePane);
	}

	private void init(String path, EditPane editPane, TableView tableView, XmlEditorMementoManager mementoManager, TextPane dictionaryPane, TableView logTable,
			FixTreeTableView fixTreeView, String fixmessagePath, TextPane fixMessagePane)
			throws IOException {
		dictionaryTab = new TextTab(path, dictionaryPane);
		editTab = new EditTab(editPane);
		historyTab = new HistoryTab(tableView, mementoManager, xmlTab, dictionaryTab);
		logTab = new LogTab(logTable);
		fixTab = new FixTab(fixTreeView);
		fixMessageTab = new TextTab(fixmessagePath, fixMessagePane);
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
				dictionaryTab.loadXml(xmlTab.toXml());
				LOG.info("Saved successfully");
			} catch (IOException e) {
				LOG.error("Error during dictionaryTab loading:", e);
			}
		});
		dictionaryTab.addChangeHandler(event -> {
			try {
				xmlTab.loadXmlText(dictionaryTab.getXml());
				fixTab.loadMessageWithDict(fixMessagePane.getContent(), dictionaryTab.getXml());
			} catch (DocumentException e) {
				LOG.error("Error during dictionaryTab loading:", e);
			} catch (ConfigError configError) {
				configError.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		fixMessagePane.addChangeHandler(event -> {
			try {
				fixTab.loadMessage(fixMessagePane.getContent());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ConfigError configError) {
				configError.printStackTrace();
			}
		});
	}

	@Override
	public void undoDictionary() {
		xmlTab.undo();
		LOG.info("Undo successfully");

	}

	@Override
	public void redoDictionary() {
		xmlTab.redo();
		LOG.info("Redo successfully");

	}

	@Override
	public void saveDictionary() throws IOException {
		String xmlPath = xmlTab.getXmlTreeView().getXmlPath();
		xmlTab.export(xmlPath);
		LOG.info("Saved successfully");
	}

	@Override
	public void saveAsDictionary(String path) throws IOException {
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
	public void openDictionary() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Xml files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(App.stage);
		if (file != null) {
			String xmlPath = file.getAbsolutePath();
			try {
				xmlTab.loadXml(xmlPath);
				dictionaryTab.loadXml(xmlTab.toXml());
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
