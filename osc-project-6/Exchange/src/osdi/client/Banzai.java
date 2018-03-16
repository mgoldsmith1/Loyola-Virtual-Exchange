package osdi.client;
import osdi.clientui.*;

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



import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.quickfixj.jmx.JmxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.ScreenLogFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import org.apache.mina.*;
import org.quickfixj.jmx.mbean.connector.*;
import org.quickfixj.jmx.mbean.JmxSupport;
import org.quickfixj.jmx.mbean.session.*;

//import osdi.client.BanzaiApplication
import osdi.clientui.BanzaiFrame;

/**
 * Entry point for the Banzai application.
 */
public class Banzai {
    private static final CountDownLatch shutdownLatch = new CountDownLatch(1);
    private ArrayList<SessionID> sessions;
    private static final Logger log = LoggerFactory.getLogger(Banzai.class);
    private static Banzai banzai;
    private boolean initiatorStarted = false;
    private Initiator initiator = null;
    private JFrame frame = null;

    public Banzai(String[] args) throws Exception {
        InputStream inputStream = null;
        if (args.length == 0) {
            inputStream = Banzai.class.getResourceAsStream("client.cfg");
        } else if (args.length == 1) {
            inputStream = new FileInputStream(args[0]);
        }
        if (inputStream == null) {
            System.out.println("usage: " + Banzai.class.getName() + " [configFile].");
            return;
        }
        SessionSettings settings = new SessionSettings(inputStream);
        inputStream.close();

        boolean logHeartbeats = Boolean.valueOf(System.getProperty("logHeartbeats", "true"));

        OrderTableModel orderTableModel = new OrderTableModel();
        ExecutionTableModel executionTableModel = new ExecutionTableModel();
        BanzaiApplication application = new BanzaiApplication(orderTableModel, executionTableModel);
        MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true, logHeartbeats);
        MessageFactory messageFactory = new DefaultMessageFactory();

        initiator = new SocketInitiator(application, messageStoreFactory, settings, logFactory,
                messageFactory);

        JmxExporter exporter = new JmxExporter();
        exporter.export(initiator); 
        // tried exporter.export(initiator) which works but its not doing what it says should work

        frame = new BanzaiFrame(orderTableModel, executionTableModel, application);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public synchronized void logon() {
        if (!initiatorStarted) {
            try {
                initiator.start();
                initiatorStarted = true;
                sessions = initiator.getSessions();
            } catch (Exception e) {
                log.error("Logon failed", e);
                
            }
        } else {
           for (SessionID sessionId : sessions) {
                Session.lookupSession(sessionId).logon();
            }
        
        }
    }

    public void logout() {
        for (SessionID sessionId : sessions) {
            Session.lookupSession(sessionId).logout("user requested");
       }
    }

    public void stop() {
        shutdownLatch.countDown();
    }

    public JFrame getFrame() {
        return frame;
    }

    public static Banzai get() {
        return banzai;
    }

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        banzai = new Banzai(args);
        if (!System.getProperties().containsKey("openfix")) {
        	//banzai.frame.setVisible(true);
        	//Thread.sleep(5000);
        	banzai.frame.isShowing();
        	Thread.sleep(5000);
            banzai.logon();
        }
        shutdownLatch.await();
    }

}
