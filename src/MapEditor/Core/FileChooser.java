package MapEditor.Core;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import MapEditor.Model.ConquestMap;
import MapEditor.Util.MyStringUtil;
import MapEditor.View.LogPanel;

/**
 * 
 * This class is to realize the function of new/save/save as functions for map.
 *
 */
public class FileChooser {
	private ConquestMap map;
	private JFileChooser fc;

	/**
	 * The constructor of the class, to do some initial work.
	 * 
	 * @param operation
	 *            The operation inputed, like "save" or "saveas".
	 * @param map
	 *            The current reading map in memory.
	 */
	public FileChooser(String operation, ConquestMap map) {
		this.map = map;
		fc = new JFileChooser();
		if (operation.equals("save")) {
			mapSave();
			return;
		}
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			switch (operation) {
			case "load":
				mapOpen();
				break;
			case "saveas":
				mapSaveAs();
				break;
			default:
				break;
			}
		} else {
			switch (operation) {
			case "load":
				LogPanel.addLog("Open command cancelled by user.");
				break;
			case "saveas":
				LogPanel.addLog("Save command cancelled by user.");
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Open function.
	 */
	public void mapOpen() {
		File path = fc.getSelectedFile();
		System.out.println(MyStringUtil.getMapPath(path));
		try {
			this.map.load(MyStringUtil.getMapPath(path));
			LogPanel.addLog("Map is successfully loaded!");
		} catch (Exception e) {
			LogPanel.addLog(e.getMessage());
		}

	}

	/**
	 * Save function.
	 */
	public void mapSave() {
		if (this.map.getMapFilePath() != null) {
			try {
				this.map.save();
				LogPanel.addLog("Map is successfully saved!");
			} catch (Exception e) {
				LogPanel.addLog("Save failed!");
			}
		} else {
			LogPanel.addLog("There's no Map loaded!");
		}

	}

	/**
	 * Save as function.
	 */
	public void mapSaveAs() {
		File path = fc.getSelectedFile();
		System.out.println(MyStringUtil.getMapPath(path));
		try {
			this.map.save(MyStringUtil.getMapPath(path));
			LogPanel.addLog("Map is successfully saved!");
		} catch (IOException e) {
			LogPanel.addLog("Save as failed!");
			e.printStackTrace();
		}
	}

}