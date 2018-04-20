package osdi.configEditor;


import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;


public class DocumentViewer {

	public DocumentViewer() {

	    
	                final JFrame f = new JFrame("Document Viewer");
	                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	                final JFileChooser fileChooser = new JFileChooser();

	                JPanel gui = new JPanel(new BorderLayout());

	                final JEditorPane document = new JEditorPane();
	                gui.add(new JScrollPane(document), BorderLayout.CENTER);

	                JButton open = new JButton("Open");
	                open.addActionListener( new ActionListener() {
	                    public void actionPerformed(ActionEvent ae) {
	                        int result = fileChooser.showOpenDialog(f);
	                        if (result==JFileChooser.APPROVE_OPTION) {
	                            File file = fileChooser.getSelectedFile();
	                            try {
	                                document.setPage(file.toURI().toURL());
	                            } catch(Exception e) {
	                                e.printStackTrace();
	                            }
	                        }
	                    }
	                });
	                gui.add(open, BorderLayout.NORTH);

	                f.setContentPane(gui);
	                f.pack();
	                f.setSize(400,300);
	                f.setLocationByPlatform(true);

	                f.setVisible(true);
	            }
	    }
