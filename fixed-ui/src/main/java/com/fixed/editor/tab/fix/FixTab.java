package com.fixed.editor.tab.fix;

import com.fixed.fix.parser.FixMessageParser;
import javafx.scene.control.TreeTableView;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
public class FixTab {

    private TreeTableView tableTreeView;
    private FixMessageParser fixMessageParser;



	public FixTab(TreeTableView tableTreeView) {
		this.tableTreeView = tableTreeView;
	}

	private void init(){

    }

	public void update(String messages) {
	}

	public void refresh() {
	}

}
