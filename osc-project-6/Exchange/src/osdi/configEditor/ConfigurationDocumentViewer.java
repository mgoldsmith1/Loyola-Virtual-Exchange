package osdi.configEditor;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationDocumentViewer {
	private Path path;

	public ConfigurationDocumentViewer() {
		URL resourseURL = null;
		
		try {
			resourseURL = ConfigurationDocumentViewer.class.getResource("config");
			File file = null;
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		final JFrame f = new JFrame("Configuration File Editor");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final JFileChooser fileChooser = new JFileChooser();
		
	    // 
	    fileChooser.setCurrentDirectory( new java.io.File(resourseURL.getPath().toString())); //resourseURL.toString() // resourseURL.toURL(); <-- this one requires try catch
		
		JPanel gui = new JPanel(new BorderLayout());

		final JEditorPane document = new JEditorPane();
		gui.add(new JScrollPane(document), BorderLayout.CENTER);
		
	//	URL classpath = ConfigurationDocumentViewer.class.getResource("DocumentViewer.class");
		//File location = new File("locationTarget");
	

		fileChooser.setDialogTitle("Configuration Files");
		CFGSearcher cfgSearch = new CFGSearcher();
	    cfgSearch.accept(new java.io.File(resourseURL.getPath()));
		fileChooser.setFileFilter(cfgSearch);//new CFGSearcher());

		JButton open = new JButton("Open");
		JButton save = new JButton("Save");

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int result = fileChooser.showOpenDialog(f);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						document.setPage(file.toURI().toURL());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int result = fileChooser.showSaveDialog(f);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						document.setPage(file.toURI().toURL());
						BufferedWriter bWriter = null;
						if(result == JFileChooser.APPROVE_OPTION) {
							bWriter = new BufferedWriter(new FileWriter(file));
							document.write(bWriter);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		gui.add(open, BorderLayout.NORTH);
		gui.add(save, BorderLayout.EAST);

		f.setContentPane(gui);
		f.pack();
		f.setSize(600, 500);
		f.setLocationByPlatform(true);
        f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
