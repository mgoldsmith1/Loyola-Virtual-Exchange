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

public class DocumentViewer {
	private Path path;

	public DocumentViewer() {
		URL resourseURL = null;
		
		try {
		    resourseURL = getClass().getResource("client.cfg");
			File file = null;
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		final JFrame f = new JFrame("Document Viewer");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JFileChooser fileChooser = new JFileChooser();
		
		try {
			fileChooser.setCurrentDirectory(new java.io.File( resourseURL.toURI()));
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		JPanel gui = new JPanel(new BorderLayout());

		final JEditorPane document = new JEditorPane();
		gui.add(new JScrollPane(document), BorderLayout.CENTER);
		
		URL classpath = DocumentViewer.class.getResource("DocumentViewer.class");
		File location = new File("locationTarget");
	

		fileChooser.setDialogTitle("Configuration Files Only");
		fileChooser.setFileFilter(new CFGSearcher());

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
		gui.add(save, BorderLayout.SOUTH);

		f.setContentPane(gui);
		f.pack();
		f.setSize(400, 300);
		f.setLocationByPlatform(true);

		f.setVisible(true);
	}
}
