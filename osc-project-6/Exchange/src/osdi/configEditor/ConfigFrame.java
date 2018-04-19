package osdi.configEditor;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;



public class ConfigFrame {
	// String _path = new File("").getAbsolutePath();
	private String filePath = "C:\\Users\\Eric\\git\\Loyola-Virtual-Exchange\\osc-project-6\\Exchange\\config";
//	private String _path = new File("").getAbsolutePath();
//	private String filePath = _path + "/Exchange/src/config/";
	public ConfigFrame() {
		JFileChooser mainWindow = new JFileChooser(filePath);
		mainWindow.setDialogTitle("Configuration Files Only");
		mainWindow.setFileFilter(new CFGSearcher());
		
		int check = mainWindow.showOpenDialog(null);
	}
	
}
