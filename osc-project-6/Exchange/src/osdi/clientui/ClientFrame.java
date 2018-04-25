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
import osdi.loyola.index.LoyolaRamblerIndex;
import osdi.server.Server;
import osdi.tracker.FIXTracker;
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
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Timer;

import javax.management.JMException;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.sun.awt.AWTUtilities;

import osdi.client.Client;
import osdi.client.ClientApplication;
import osdi.client.ExecutionTableModel;
import osdi.client.OrderTableModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import java.awt.TextArea;

/**
 * Main application window
 */

/**
 * Displays the GUI for ClientFrame
 * 
 * @param osdi.client.OrderTableModel
 *            orderTableModel, osdi.client.ExecutionTableModel
 *            executionTableModel, final osdi.client.ClientApplication
 *            application, Initiator initiator, SessionSettings settings
 * @return JFrame
 * @author Matthew Goldsmith, Eric Lymberopoulos, Yang Li, Chris Elliott,
 *         Yonathan Gordon
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
	private LoyolaRamblerIndex loyolaRamblerIndexRealTimeWorker = null;
	Thread chartThread = null;
	private boolean orderBookFrameVisible;

	public ClientFrame(osdi.client.OrderTableModel orderTableModel, osdi.client.ExecutionTableModel executionTableModel,
			final osdi.client.ClientApplication application, Initiator initiator, SessionSettings settings) {

		super();

		setTitle("Order Entry");
		if (System.getProperties().containsKey("openfix")) {
			createMenuBar(application);
		}
		getContentPane().add(new ClientPanel(orderTableModel, executionTableModel, application), BorderLayout.CENTER);

		this.application = application;
		this.orderTableModel = orderTableModel;
		this.executionTableModel = executionTableModel;
		this.initiator = initiator;
		this.settings = settings;
		splashScreenInit();
		initializeMainMenuGUI();

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
		appAvailableItem
				.addActionListener(e -> application.setAvailable(((JCheckBoxMenuItem) e.getSource()).isSelected()));
		appMenu.add(appAvailableItem);

		JMenuItem sendMissingFieldRejectItem = new JCheckBoxMenuItem("Send Missing Field Reject");
		sendMissingFieldRejectItem.setSelected(application.isMissingField());
		sendMissingFieldRejectItem
				.addActionListener(e -> application.setMissingField(((JCheckBoxMenuItem) e.getSource()).isSelected()));
		appMenu.add(sendMissingFieldRejectItem);

		setJMenuBar(menubar);
	}

	/**
	 * Displays image logo
	 * 
	 * @param none
	 * @return nothing
	 * @author Matthew Goldsmith
	 */
	public void splashScreenInit() {
		JWindow window = new JWindow();

		window.getContentPane()
				.add(new JLabel(new javax.swing.ImageIcon(Client.class.getResource("testlogo.png")), SwingConstants.CENTER))
				.setBackground(Color.WHITE);

		window.setSize(1080, 600);

		// previously deleted. we'll keep setBounds for now until
		// we add one of the other images that are larger.
		window.setBounds(600, 500, 500, 400); //400
		window.setLocationRelativeTo(null);

		window.setBackground(Color.WHITE);
		window.setVisible(true);

		//Timer to fade out the splash screen
		Timer timer = new Timer();
		timer.schedule(new FadeSplash(window),2500,5);
		while(window.isShowing()) {
			String delay = new String();
			delay = "Waiting for splashscreen to fade.";
		}
	}

	/**
	 * Initializes the GUI for ClientFrame
	 * 
	 * @param none
	 * @return nothing
	 * @author Matthew Goldsmith
	 */
	private void initializeMainMenuGUI() {
		mainFrame = new JFrame("Loyola Virtual Exchange");

		mainFrame.setSize(1080, 600); // mainFrame.setSize(2000,1800);
		mainFrame.getContentPane().setLayout(new GridLayout(3, 1));

		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		statusLabel.setSize(350, 100);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
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

	/**
	 * Displays the GUI for ClientFrame
	 * 
	 * @param none
	 * @return nothing
	 * @author Matthew Goldsmith, Eric Lymberopoulos, Yang Li, Chris Elliott,
     *         Yonathan Gordon
	 */
	private void displayMainMenuGUI() {

		// Initializing MenuBar creates menu bar
		final JMenuBar menuBar = new JMenuBar();

		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////

		// Initializing Menus

		JMenu fileMenu = new JMenu("File");
		final JMenu aboutMenu = new JMenu("About");
		final JMenu tradeMenu = new JMenu("Trade");
		JMenu orderBookMenu = new JMenu("Order Book");
		// JMenu chartMenu = new JMenu("Charts");
		final JMenu serverMenu = new JMenu("Server");

		// Provides the ability to open a configuration file from the main gui
		// and edit its contents (IP,port,etc.)
		JMenu configurationMenu = new JMenu("Configuration");

		JMenu loyolaChartMenu = new JMenu("Chart");

		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////

		// Initializing Menu Items

		JMenuItem clientStartLogonMenuItem = new JMenuItem("Logon -> LocalHost"); // Open
																					// Workspace
		clientStartLogonMenuItem.setActionCommand("Start Client Logon...");

		JMenuItem clientLogoutMenuItem = new JMenuItem("Logout...");
		clientLogoutMenuItem.setActionCommand("Client Logout...");

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setActionCommand("Exit");

		JMenuItem tradeMenuItem = new JMenuItem("Order Entry..."); // executor
		tradeMenuItem.setActionCommand("Order Entry..."); // executor

		JMenuItem orderBookMenuItem = new JMenuItem("Generate...");
		orderBookMenuItem.setActionCommand("Generate...");

		////////////////////////////////////////////////////////////////////////////////
		// UNUSED

		// Client connect to executor server Firm
		JMenuItem connectExecMenuItem = new JMenuItem("Connect..."); // executor
		connectExecMenuItem.setActionCommand("Connect..."); // executor

		JMenuItem disconnectExecMenuItem = new JMenuItem("Disconnect..."); // executor
		disconnectExecMenuItem.setActionCommand("Disconnect..."); // executor
		////////////////////////////////////////////////////////////////////////////////
		JMenuItem chartMenuItem = new JMenuItem("Select Instrument...");
		chartMenuItem.setActionCommand("Select Instrument...");

		JMenuItem openConfigurationMenuItem = new JMenuItem("Open Config");
		openConfigurationMenuItem.setActionCommand("Open Config");

		JMenuItem repositoryMenuItem = new JMenuItem("Repository");
		repositoryMenuItem.setActionCommand("Repository");
		aboutMenu.add(repositoryMenuItem);

		JMenuItem loyolaRamblerIndexMenuItem = new JMenuItem("Loyola Rambler Index");
		loyolaRamblerIndexMenuItem.setActionCommand("LoyolaRamblerIndex");

		MenuItemListener menuItemListener = new MenuItemListener();

		// File Menu drop down menu items added to their relevant
		// actionListeners
		clientStartLogonMenuItem.addActionListener(menuItemListener);
		clientLogoutMenuItem.addActionListener(menuItemListener);
		exitMenuItem.addActionListener(menuItemListener);

		// Other Menu Items added to their relevant actionListeners
		tradeMenuItem.addActionListener(menuItemListener);
		connectExecMenuItem.addActionListener(menuItemListener);
		disconnectExecMenuItem.addActionListener(menuItemListener);
		openConfigurationMenuItem.addActionListener(menuItemListener);
		repositoryMenuItem.addActionListener(menuItemListener);
		loyolaRamblerIndexMenuItem.addActionListener(menuItemListener);

		// unused
		chartMenuItem.addActionListener(ev -> {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new ChartFrame().setVisible(true);
				}
			});
		});

		tradeMenuItem.addActionListener(ev -> {
			if (foo == true && orderBookFrameVisible == true) {
				// if foo was triggered as connected by logon
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						setVisible(true);
					}
				});

			} else if (foo == false) {
				int confirm = JOptionPane.showOptionDialog(null, "Not Connected. (Order Book->Generate).",
						"Exit Confirmation", JOptionPane.OK_CANCEL_OPTION, // .YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null, null, null);
				if (confirm == 0) {
					/*
					 * SwingUtilities.invokeLater(new Runnable() { public void
					 * run() { new OrderBookFrame(); } });
					 */
				} else {
					// does nothing
				}
			} else if (foo == true && orderBookFrameVisible == false) {
			int confirm = JOptionPane.showOptionDialog(null, "Not Connected. (Order Book->Generate).",
					"Exit Confirmation", JOptionPane.OK_CANCEL_OPTION, // .YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, null, null, null);
			if (confirm == 0) {
				/*
				 * SwingUtilities.invokeLater(new Runnable() { public void
				 * run() { new OrderBookFrame(); } });
				 */
			} else {
				// does nothing
			}
		}

		});
		orderBookMenuItem.addActionListener(ev -> {
			if (foo == true) {
				// if foo was triggered as connected by logon
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new OrderBookFrame();
						orderBookFrameVisible = true;
					}
				});

			} else if (foo == false) {
				int confirm = JOptionPane.showOptionDialog(null, "Not Connected.\n(File->Connect->Logon).",
						"Exit Confirmation", JOptionPane.OK_CANCEL_OPTION, // .YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE, null, null, null);
				if (confirm == 0) {
					/*
					 * SwingUtilities.invokeLater(new Runnable() { public void
					 * run() { new OrderBookFrame(); } });
					 */
				} else {
					// does nothing
				}
			}

		});

		// Adds File Menu
		JMenu connectSubMenuForLogon = new JMenu("Connect"); // Client//User
		fileMenu.add(connectSubMenuForLogon);
		connectSubMenuForLogon.add(clientStartLogonMenuItem);
		connectSubMenuForLogon.add(clientLogoutMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		// Adds OrderBook Menu
		tradeMenu.add(tradeMenuItem);
		orderBookMenu.add(orderBookMenuItem);

		// Adds Server Menu (Unused)
		serverMenu.add(connectExecMenuItem);
		serverMenu.addSeparator();
		serverMenu.add(disconnectExecMenuItem);

		// Adds Configuration Menu for Configuration File Editor
		configurationMenu.add(openConfigurationMenuItem);
		loyolaChartMenu.add(loyolaRamblerIndexMenuItem);

		// Adds all Menus to ClientFrame menuBar
		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);
		menuBar.add(tradeMenu);
		menuBar.add(orderBookMenu);
		menuBar.add(loyolaChartMenu);
		// menuBar.add(serverMenu); //commented out for now (unused)
		menuBar.add(configurationMenu);

		// add menubar to the frame
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setLocationRelativeTo(null); // centers the GUI
		mainFrame.setVisible(true);

	}

	class MenuItemListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().contains("Start Client Logon...")) { // (Executor)
				statusLabel.setText("Connecting to LocalHost...");
				try {
					foo = true;
					Client.get().logon();

				} catch (Exception e1) {
					e1.getMessage();
				} finally {
					try {
						if (foo == true)
							initiator.start();
					} catch (RuntimeError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();

					} catch (ConfigError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (foo == true) {
					statusLabel.setText("Connected.");
				}
			}
			if (e.getActionCommand().contains("Client Logout...")) {
				initiator.stop();
				statusLabel.setText("Disconnected Client");
				foo = false;
			}
			if (e.getActionCommand().contains("Connect...")) { // (Connect to
																// Executor
																// Firm)
				statusLabel.setText("Not yet implemented");
			}
			/////////////////////////////////////////
			if (e.getActionCommand().contains("Disconnect...")) {
				// statusLabel.setText("Disconnected Server");
				statusLabel.setText("Not yet implemented");
			}

			/////////////////////////////////////////////////
			if (e.getActionCommand().contains("Order Entry...")) { //trade menu
				//setVisible(true);
			}
			if (e.getActionCommand().contains("Exit")) {
				// New 4/19
				if (initiator.isLoggedOn() == true) {

				} else {
					int confirm = JOptionPane.showOptionDialog(null, "Do you want to disconnect from current session?",
							"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null,
							null);
					if (confirm == 0) {
						initiator.stop();
						statusLabel.setText("Disconnected Client");
						foo = false;

					} else {
						// does nothing
					}
					System.exit(0);

				}
			}
			if (e.getActionCommand().contains("Open Config")) {
				osdi.configEditor.ConfigurationDocumentViewer callConfigurationDocumentViewer = new osdi.configEditor.ConfigurationDocumentViewer();
			}
			if (e.getActionCommand().contains("Repository")) {
				try {
					Desktop desktop = java.awt.Desktop.getDesktop();
					URI oURL = new URI("https://github.com/mgoldsmith1/Loyola-Virtual-Exchange");
					desktop.browse(oURL);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (e.getActionCommand().contains("LoyolaRamblerIndex")) {
				loyolaRamblerIndexRealTimeWorker = new LoyolaRamblerIndex();
				chartThread = new Thread(() -> loyolaRamblerIndexRealTimeWorker.go());
				chartThread.start();

			}
			/*
			 * if(e.getActionCommand().contains("Stopper")){ //
			 * loyolaRamblerIndexRealTimeWorker.stopWithCancel();
			 * chartThread.interrupt(); try { chartThread.join(); } catch
			 * (InterruptedException e1) { // TODO Auto-generated catch block
			 * e1.printStackTrace(); } }
			 */

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