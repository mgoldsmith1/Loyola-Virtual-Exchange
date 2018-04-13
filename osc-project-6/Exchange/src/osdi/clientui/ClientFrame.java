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
import osdi.server.Server;

import quickfix.ConfigError;
import quickfix.FieldConvertError;
import quickfix.Initiator;
import quickfix.RuntimeError;
import quickfix.SessionSettings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SplashScreen;
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
import java.net.MalformedURLException;
import java.net.URL;

import javax.management.JMException;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import osdi.client.Client;
import osdi.client.ClientApplication;
import osdi.client.ExecutionTableModel;
import osdi.client.OrderTableModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

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
	private Initiator initiator = null;
	private SessionSettings settings = null;
	private boolean foo = false;
    private SplashScreen screen;
    private String _path = new File("").getAbsolutePath();
    
    public ClientFrame(osdi.client.OrderTableModel orderTableModel, osdi.client.ExecutionTableModel executionTableModel,
            final osdi.client.ClientApplication application, Initiator initiator, SessionSettings settings) {

        super();
       
 	    setTitle("Order Entry");
        if (System.getProperties().containsKey("openfix")) {
            createMenuBar(application);
        }
        getContentPane().add(new ClientPanel(orderTableModel, executionTableModel, application),
                BorderLayout.CENTER);
        
        this.application = application;
        this.orderTableModel = orderTableModel;
        this.executionTableModel = executionTableModel;
        this.initiator = initiator;
        this.settings = settings;
        splashScreenInit();
      	initializeMainMenuGUI();
      	
       /* try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        setSize(600, 400);
      	displayMainMenuGUI();
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

	public void splashScreenInit() {
		JWindow window = new JWindow();

		window.getContentPane().add(new JLabel(new javax.swing.ImageIcon(_path + "/logo.png"), SwingConstants.CENTER)).setBackground(Color.WHITE);;

		window.setLocationRelativeTo(null);
		window.setBounds(500, 150, 300, 200);

		window.setBackground(Color.WHITE);
		window.setVisible(true);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// JFrame frame = new JFrame();
		// frame.add(new JLabel("Welcome"));
		// frame.setLocationRelativeTo(null);
		// frame.setVisible(true);
		// frame.setSize(300,100);
		window.dispose();
	}

    private void initializeMainMenuGUI(){
        mainFrame = new JFrame("Loyola Virtual Exchange");
        
        mainFrame.setSize(1080,600);
        mainFrame.getContentPane().setLayout(new GridLayout(3, 1));

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

        mainFrame.getContentPane().add(headerLabel);
        
        JPopupMenu popupMenu = new JPopupMenu();
        addPopup(headerLabel, popupMenu);
        mainFrame.getContentPane().add(controlPanel);
        mainFrame.getContentPane().add(statusLabel);
        mainFrame.setLocationRelativeTo(null); // centers the GUI
        mainFrame.setVisible(true);  
     }
     private void displayMainMenuGUI(){
        //create a menu bar
        final JMenuBar menuBar = new JMenuBar();
      
        //create menus
        JMenu fileMenu = new JMenu("File");
        //JMenu editMenu = new JMenu("Edit");       
        final JMenu orderMenu = new JMenu("Trade");
        JMenu orderBookMenu = new JMenu("Order Book"); 
        JMenu chartMenu = new JMenu("Charts");
        final JMenu serverMenu = new JMenu("Server");
        final JMenu aboutMenu = new JMenu("About");

        
        // Provides the ability to open a configuration file from them main gui
        // and edit the contents (IP,port,etc.
        JMenu configurationMenu = new JMenu("Configuration"); 
        
        final JMenu linkMenu = new JMenu("Links");
         
        //create menu items
        //JMenuItem newMenuItem = new JMenuItem("New");
        //newMenuItem.setMnemonic(KeyEvent.VK_N);
        //newMenuItem.setActionCommand("New");

        JMenuItem clientStartLogonMenuItem = new JMenuItem("Logon @LocalHost..."); //Open Workspace
       
        clientStartLogonMenuItem.setActionCommand("Start Client Logon...");

        JMenuItem clientLogoutMenuItem = new JMenuItem("Logout..."); 
        clientLogoutMenuItem.setActionCommand("Client Logout...");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");

        //JMenuItem cutMenuItem = new JMenuItem("Cut");
        //cutMenuItem.setActionCommand("Cut");

        //JMenuItem copyMenuItem = new JMenuItem("Copy");
        //copyMenuItem.setActionCommand("Copy");

        //JMenuItem pasteMenuItem = new JMenuItem("Paste");
        //pasteMenuItem.setActionCommand("Paste");
        
      
        
        JMenuItem orderMenuItem = new JMenuItem("Order Entry..."); //executor
        orderMenuItem.setActionCommand("Order Entry..."); //executor
        
        JMenuItem orderBookMenuItem = new JMenuItem("Generate...");
        orderBookMenuItem.setActionCommand("Generate...");

        /*
         * Temporarily putting this here. This starts the FIXTracker if user presses the start button
         startExecutorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startExecutorButtonActionPerformed(evt);
            }
        });
         */
        
        //Client connect to executor server
        //Connect to Firm
        //Disconnect to Firm
        //need a menu dialog with scroll bar of a list of firms to connect
        // and configure to
        // configuration will include ip address and prefered port
        // need a separat configuration for your own configs
        JMenuItem connectExecMenuItem = new JMenuItem("Connect..."); //executor
        connectExecMenuItem.setActionCommand("Connect..."); //executor
        
        JMenuItem disconnectExecMenuItem = new JMenuItem("Disconnect..."); //executor
        disconnectExecMenuItem.setActionCommand("Disconnect..."); //executor
        
        JMenuItem chartMenuItem = new JMenuItem("Select Instrument...");
        chartMenuItem.setActionCommand("Select Instrument...");
        
       // JMenuItem newConfigurationMenuItem = new JMenuItem("New Config");
       // newConfigurationMenuItem.setActionCommand("New");
        
        JMenuItem openConfigurationMenuItem = new JMenuItem("Open Config");
        openConfigurationMenuItem.setActionCommand("Open Config");
        
      
        MenuItemListener menuItemListener = new MenuItemListener();

   //     newMenuItem.addActionListener(menuItemListener);
        clientStartLogonMenuItem.addActionListener(menuItemListener);
        clientLogoutMenuItem.addActionListener(menuItemListener);
        exitMenuItem.addActionListener(menuItemListener);
      
        
        // new
        orderMenuItem.addActionListener(menuItemListener);
        connectExecMenuItem.addActionListener(menuItemListener);
        disconnectExecMenuItem.addActionListener(menuItemListener);
        openConfigurationMenuItem.addActionListener(menuItemListener);
        
        
      //Remember to import SwingUtilities before Run it
        chartMenuItem.addActionListener(ev -> {
        	SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new ChartFrame().setVisible(true);
				}
			});
        });
        
        orderBookMenuItem.addActionListener(ev -> {
        	SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new OrderBookFrame();
				}
			});
        });
        //add menu items to menus
      //  fileMenu.add(newMenuItem);
      
        
        JMenu mnLogon = new JMenu("Session");  //Client//User
        //fileMenu.add(mnLogon);
        JMenuItem mntmLogon = new JMenuItem("Connect");
      
      
        mnLogon.add(mntmLogon);
        fileMenu.add(clientStartLogonMenuItem);
        fileMenu.addSeparator();
        JMenuItem mntmLogoutlocalhost = new JMenuItem("Disconnect");
       // mntmLogoutlocalhost.setActionCommand("disconnectclient");
        mnLogon.add(mntmLogoutlocalhost);
       
        fileMenu.add(clientLogoutMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);       
        
        orderMenu.add(orderMenuItem);
        orderBookMenu.add(orderBookMenuItem);
        chartMenu.add(chartMenuItem);
        serverMenu.add(connectExecMenuItem);
        serverMenu.addSeparator();
        serverMenu.add(disconnectExecMenuItem);
        configurationMenu.add(openConfigurationMenuItem);
        
      
        //add menu to menubar
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);  
        menuBar.add(orderMenu);
        menuBar.add(orderBookMenu);
        menuBar.add(serverMenu);
        menuBar.add(configurationMenu);

        //add menubar to the frame
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setLocationRelativeTo(null); // centers the GUI
        mainFrame.setVisible(true);   
       
        
     }
     class MenuItemListener implements ActionListener {
    
        public void actionPerformed(ActionEvent e) {            
           if(e.getActionCommand().contains("Start Client Logon...") ){ //(Executor) 
        	   statusLabel.setText("Connecting to LocalHost...");	 
   				try{
                   Client.get().logon();
                   foo = true;
   				}catch(Exception e1 ){
   					e1.getMessage();
   				}finally{
   					try {
   						if( foo == true)
   						initiator.start();
   					} catch (RuntimeError e1) {
   						// TODO Auto-generated catch block
   						e1.printStackTrace();
   						
   					} catch (ConfigError e1) {
   						// TODO Auto-generated catch block
   						e1.printStackTrace();
   					}
   				}
   				if( foo == true){
   					statusLabel.setText("Connected.");
   				}
              }
           if(e.getActionCommand().contains("Client Logout...") ){ 
        	   initiator.stop();
        	   statusLabel.setText("Disconnected Client");
           }
           if(e.getActionCommand().contains("Connect...") ){ //(Connect to Executor Firm) 
        	   statusLabel.setText("Not yet implemented");
        	   //foo = true;
           }
           /////////////////////////////////////////
           if(e.getActionCommand().contains("Disconnect...")) {
        	  // statusLabel.setText("Disconnected Server");
        	   statusLabel.setText("Not yet implemented");
        	 //  initiator.stop();
        	  // foo = false;
           }
           
           /////////////////////////////////////////////////
           if(e.getActionCommand().contains("Order Entry...")){
               setVisible(true);
           }
           if(e.getActionCommand().contains("Exit")){
        	   //Revisions needed
        	   System.exit(0);
           }
           if(e.getActionCommand().contains("Open Config")) {
        	   
           }
           
        }    
     }
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}