package menuExample;



//package com.tutorialspoint.gui;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class menuExample {
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel; 

	public menuExample(){
		prepareGUI();
	}
	public static void main(String[] args){
		menuExample  swingMenuDemo = new menuExample();     
		swingMenuDemo.showMenuDemo();
	}
	private void prepareGUI(){
		mainFrame = new JFrame("Java SWING Examples");
		mainFrame.setSize(400,400);
		mainFrame.setLayout(new GridLayout(3, 1));

		headerLabel = new JLabel("",JLabel.CENTER );
		statusLabel = new JLabel("",JLabel.CENTER);        
		statusLabel.setSize(350,100);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});    
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);  
	}
	private void showMenuDemo(){
		//create a menu bar
		final JMenuBar menuBar = new JMenuBar();

		//create menus
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit"); 
		final JMenu aboutMenu = new JMenu("About");
		final JMenu linkMenu = new JMenu("Links");

		//create menu items
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.setMnemonic(KeyEvent.VK_N);
		newMenuItem.setActionCommand("New");

		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.setActionCommand("Open");

		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setActionCommand("Save");

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setActionCommand("Exit");

		JMenuItem cutMenuItem = new JMenuItem("Cut");
		cutMenuItem.setActionCommand("Cut");

		JMenuItem copyMenuItem = new JMenuItem("Copy");
		copyMenuItem.setActionCommand("Copy");

		JMenuItem pasteMenuItem = new JMenuItem("Paste");
		pasteMenuItem.setActionCommand("Paste");

		JMenuItem repository = new JMenuItem("repository");
		repository.setActionCommand("repository");

		MenuItemListener menuItemListener = new MenuItemListener();

		newMenuItem.addActionListener(menuItemListener);
		openMenuItem.addActionListener(menuItemListener);
		saveMenuItem.addActionListener(menuItemListener);
		exitMenuItem.addActionListener(menuItemListener);
		cutMenuItem.addActionListener(menuItemListener);
		copyMenuItem.addActionListener(menuItemListener);
		pasteMenuItem.addActionListener(menuItemListener);
		repository.addActionListener(menuItemListener);

		final JCheckBoxMenuItem showWindowMenu = new JCheckBoxMenuItem("Show About", true);
		showWindowMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if(showWindowMenu.getState()){
					menuBar.add(aboutMenu);
				} else {
					menuBar.remove(aboutMenu);
				}
			}
		});
		final JRadioButtonMenuItem showLinksMenu = new JRadioButtonMenuItem(
				"Show Links", true);
		showLinksMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if(menuBar.getMenu(3)!= null){
					menuBar.remove(linkMenu);
					mainFrame.repaint();
				} else {                   
					menuBar.add(linkMenu);
					mainFrame.repaint();
				}
			}
		});
		//add menu items to menus
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(showWindowMenu);
		fileMenu.addSeparator();
		fileMenu.add(showLinksMenu);       
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);        

		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);

		aboutMenu.add(repository);

		//add menu to menubar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(aboutMenu);       
		menuBar.add(linkMenu);

		//add menubar to the frame
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setVisible(true);     
	}
	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().contains("repository")) {
				try {
					Desktop desktop = java.awt.Desktop.getDesktop();
					URI oURL = new URI("https://github.com/mgoldsmith1/Market-Exchange-Simulator");
					desktop.browse(oURL);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			statusLabel.setText(e.getActionCommand() + " JMenuItem clicked.");
		}    
	}
}
