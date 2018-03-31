package osdi.clientui;

import java.awt.Dimension;

import javax.swing.*;

public class GraphFrame extends JFrame {

	public GraphFrame() {
		super("Graph Frame");
		prepareGraphGUI();
		pack();
		setLocationRelativeTo(null);
	}

	private void prepareGraphGUI() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 800));
		getContentPane().add(panel);

	}
}
