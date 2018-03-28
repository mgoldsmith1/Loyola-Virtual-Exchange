/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved.
 *
 * This file is part of the QuickFIX FIX Engine
 *
 * This file may be distributed under the terms of the quickfixengine.org
 * license as defined by quickfixengine.org and appearing in the file
 * LICENSE included in the packaging of this file.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE.
 *
 * See http://www.quickfixengine.org/LICENSE for licensing information.
 *
 * Contact ask@quickfixengine.org if any conditions of this licensing
 * are not clear to you.
 ******************************************************************************/

package osdi.clientui;


import osdi.clientui.ClientPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import osdi.client.Client;
import osdi.client.ClientApplication;
import osdi.client.ExecutionTableModel;
import osdi.client.OrderTableModel;



/**
 * Main application window
 */
public class ClientFrame extends JFrame {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	
	private final osdi.client.ClientApplication application;
	private final osdi.client.OrderTableModel orderTableModel;
	private final osdi.client.ExecutionTableModel executionTableModel;
	private boolean foo = false;

    public ClientFrame(osdi.client.OrderTableModel orderTableModel, osdi.client.ExecutionTableModel executionTableModel,
            final osdi.client.ClientApplication application) {
  
        super();
        
        this.application = application;
        this.orderTableModel = orderTableModel;
        this.executionTableModel = executionTableModel;
        
      	prepareGUI();
      	showMenuDemo();
      	
        /*setTitle("Order Entry");
        setSize(600, 400);

        if (System.getProperties().containsKey("openfix")) {
            createMenuBar(application);
        }
        getContentPane().add(new BanzaiPanel(orderTableModel, executionTableModel, application),
                BorderLayout.CENTER);
        setVisible(true);*/
    }

    private void createMenuBar(final ClientApplication application) {
        JMenuBar menubar = new JMenuBar();

        JMenu sessionMenu = new JMenu("Session");
        menubar.add(sessionMenu);

        JMenuItem logonItem = new JMenuItem("Logon");
        logonItem.addActionListener(e -> Client.get().logon());
        sessionMenu.add(logonItem);

        JMenuItem logoffItem = new JMenuItem("Logoff");
        logoffItem.addActionListener(e -> Client.get().logout());
        sessionMenu.add(logoffItem);

        JMenu appMenu = new JMenu("Application");
        menubar.add(appMenu);

        JMenuItem appAvailableItem = new JCheckBoxMenuItem("Available");
        appAvailableItem.setSelected(application.isAvailable());
        appAvailableItem.addActionListener(e -> application.setAvailable(((JCheckBoxMenuItem) e.getSource()).isSelected()));
        appMenu.add(appAvailableItem);

        JMenuItem sendMissingFieldRejectItem = new JCheckBoxMenuItem("Send Missing Field Reject");
        sendMissingFieldRejectItem.setSelected(application.isMissingField());
        sendMissingFieldRejectItem.addActionListener(e -> application.setMissingField(((JCheckBoxMenuItem) e.getSource()).isSelected()));
        appMenu.add(sendMissingFieldRejectItem);

        setJMenuBar(menubar);
    }
    private void prepareGUI(){
        mainFrame = new JFrame("Market Exchange Sim");
        
        mainFrame.setSize(1080,600);
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
        final JMenu orderMenu = new JMenu("Order");
        final JMenu connectMenu = new JMenu("Connect");
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
        
        //Client connect to executor server
        JMenuItem serverExecMenuItem = new JMenuItem("Server..."); //executor
        serverExecMenuItem.setActionCommand("Server..."); //executor
        
        JMenuItem orderMenuItem = new JMenuItem("Order Entry..."); //executor
        orderMenuItem.setActionCommand("Order Entry..."); //executor

        MenuItemListener menuItemListener = new MenuItemListener();

        newMenuItem.addActionListener(menuItemListener);
        openMenuItem.addActionListener(menuItemListener);
        saveMenuItem.addActionListener(menuItemListener);
        exitMenuItem.addActionListener(menuItemListener);
        cutMenuItem.addActionListener(menuItemListener);
        copyMenuItem.addActionListener(menuItemListener);
        pasteMenuItem.addActionListener(menuItemListener);
        
        // new
        orderMenuItem.addActionListener(menuItemListener);
        serverExecMenuItem.addActionListener(menuItemListener);
        
        

        final JCheckBoxMenuItem showWindowMenu = new JCheckBoxMenuItem(
           "Show About", true);
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
        //new
        connectMenu.add(serverExecMenuItem);
        orderMenu.add(orderMenuItem);

        //add menu to menubar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(aboutMenu);       
       // menuBar.add(linkMenu);
        menuBar.add(orderMenu);
        menuBar.add(connectMenu);
       

        //add menubar to the frame
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setVisible(true);   
        mainFrame.setLocationRelativeTo(null); // centers the GUI

     }
     class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {            
           statusLabel.setText(e.getActionCommand() + " JMenuItem clicked.");
           if(e.getActionCommand().contains("Server...") && foo == false){ //(Executor) 
        	  
        	   String Path = new File("").getAbsolutePath();
        	   ProcessBuilder pb = new ProcessBuilder("java", "-jar", Path +"/Exchange/jars/server.jar");
        	  // System.out.println(Path);
        	   try {
			    Process p = pb.start();
		       } catch (IOException e1) {
		           // TODO Auto-generated catch block
			  	   e1.printStackTrace();
			   }
        	   foo = true;
           }
           /////////////////////////////////////////////////
           if(e.getActionCommand().contains("Order Entry...")){
        	   //This used to be in the constructor
        	   setTitle("Order Entry");
               setSize(600, 400);

               if (System.getProperties().containsKey("openfix")) {
                   createMenuBar(application);
               }
               getContentPane().add(new ClientPanel(orderTableModel, executionTableModel, application),
                       BorderLayout.CENTER);
               setVisible(true);
           }
        }    
     }
}
