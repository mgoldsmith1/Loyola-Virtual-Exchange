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

package osdi.test.acceptanceresynch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import osdi.client.Client;
import osdi.tracker.FIXTracker;
import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.FixVersions;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Initiator;
import quickfix.MemoryStoreFactory;
import quickfix.Message;
import quickfix.MessageCracker;
import quickfix.MessageStoreFactory;
import quickfix.RejectLogon;
import quickfix.RuntimeError;
import quickfix.SLF4JLogFactory;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.UnsupportedMessageType;
import quickfix.fix44.Heartbeat;
import quickfix.fix44.Logon;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class ResynchTestClient extends MessageCracker implements Application {
    private final Logger log = LoggerFactory.getLogger(FIXTracker.class);//ResynchTestServer.class);
    private final SessionSettings settings = new SessionSettings();
    private final CountDownLatch shutdownLatch = new CountDownLatch(1);
    private boolean failed;

    private boolean unsynchMode = false;
    private boolean forceResynch = false;

    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound,
            IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        try {
            crack(message, sessionId);
        } catch (UnsupportedMessageType e) {
            log.error(e.getMessage());
        }
    }

    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound,
            IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        crack(message, sessionID);
    }

    public void onCreate(SessionID sessionId) {
    }

    public void onLogon(SessionID sessionId) {
    }

    public void onLogout(SessionID sessionId) {
        if (unsynchMode && !forceResynch) {
            stop(false);
        }
    }

    // Cracked
    public void onMessage(Heartbeat message, SessionID sessionID) throws FieldNotFound,
            UnsupportedMessageType, IncorrectTagValue {
        log.info("Received Heartbeat: {}", message);
        stop(false);
    }

    private void stop(boolean failed) {
        this.failed = failed;
        shutdownLatch.countDown();
    }

    public void run() throws ConfigError, SessionNotFound, InterruptedException {
        HashMap<Object, Object> defaults = new HashMap<>();
        defaults.put("ConnectionType", "initiator");
        defaults.put("HeartBtInt", "3");
        defaults.put("SocketConnectHost", "localhost");
        defaults.put("SocketConnectPort", "9878");//"19889");
        defaults.put("SocketTcpNoDelay", "Y");
        defaults.put("ReconnectInterval", "3");
        defaults.put("StartTime", "00:00:00");
        defaults.put("EndTime", "00:00:00");
        defaults.put("SenderCompID", "CLIENT");
        defaults.put("TargetCompID", "FIXTRACKER");
        defaults.put("FileStorePath", "target/data/resynch_test");
        defaults.put("ValidateUserDefinedFields", "Y");
        settings.set(defaults);

        SessionID sessionID = new SessionID(FixVersions.BEGINSTRING_FIX42, "CLIENT", "FIXTRACKER");
        settings.setString(sessionID, "BeginString", FixVersions.BEGINSTRING_FIX42);
        settings.setString(sessionID, "DataDictionary", "FIX42.xml");

        MessageStoreFactory storeFactory = new MemoryStoreFactory();
        Initiator initiator = new SocketInitiator(this, storeFactory, settings,
                new SLF4JLogFactory(settings), new DefaultMessageFactory());
       
      
        initiator.start();

        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    stop(true);
                	// Client a = new Client();
                	//a.get().stop();
                }
            }, 10000);

            try {
                shutdownLatch.await();
            } catch (InterruptedException e) {
            }

            if (failed) {
                String message = "Heartbeat not sent";
                log.error(message);
                throw new RuntimeError(message);
            }
        } finally {
            initiator.stop();
        }
    }

    public void toAdmin(Message message, SessionID sessionId) {
        if (message instanceof Logon) {
            System.out.println("Sending logon message: " + message);
        }
    }

    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
    }

    public static void main(String[] args) throws ConfigError, SessionNotFound,
            InterruptedException {
        ResynchTestClient ttc = new ResynchTestClient();
        ttc.run();
    }

    public void setUnsynchMode(boolean unsynchMode) {
        this.unsynchMode = unsynchMode;
    }

    public boolean isUnsynchMode() {
        return unsynchMode;
    }

    public void setForceResynch(boolean forceResynch) {
        this.forceResynch = forceResynch;
    }

    public boolean isForceResynch() {
        return forceResynch;
    }
}
