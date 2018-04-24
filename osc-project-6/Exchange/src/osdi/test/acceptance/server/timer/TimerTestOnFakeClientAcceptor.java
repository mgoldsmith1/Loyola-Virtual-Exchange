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

package osdi.test.acceptance.server.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import quickfix.fix44.ListStatusRequest;
import quickfix.fix44.TestRequest;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:jhensley@bonddesk.com">John Hensley</a>
 */
public class TimerTestOnFakeClientAcceptor extends MessageCracker implements Application {
    private final Logger log = LoggerFactory.getLogger(osdi.tracker.FIXTracker.class);
    private final SessionSettings settings = new SessionSettings();
    private final CountDownLatch shutdownLatch = new CountDownLatch(1);
    private boolean failed;

    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound,
            IncorrectDataFormat, IncorrectTagValue, RejectLogon {
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
    }

    public void onMessage(ListStatusRequest message, SessionID sessionID) {
        log.info("got ListStatusRequest");
    }

    private void stop(boolean failed) {
        this.failed = failed;
        shutdownLatch.countDown();
    }

    public void run() throws ConfigError, SessionNotFound, InterruptedException {
        HashMap<Object, Object> defaults = new HashMap<>();
        defaults.put("ConnectionType", "initiator");
        defaults.put("HeartBtInt", "2");
        defaults.put("SocketConnectHost", "localhost");
        defaults.put("SocketConnectPort", "19888");
        defaults.put("SocketTcpNoDelay", "Y");
        defaults.put("StartTime", "00:00:00");
        defaults.put("EndTime", "00:00:00");
        defaults.put("SenderCompID", "CLIENT");
        defaults.put("TargetCompID", "FIXTRACKER");
        defaults.put("FileStorePath", "target/data/timer_test");
        defaults.put("ValidateUserDefinedFields", "Y");
        settings.set(defaults);

        SessionID sessionID = new SessionID(FixVersions.BEGINSTRING_FIX44, "FIXTRACKER", "CLIENT");
        settings.setString(sessionID, "BeginString", FixVersions.BEGINSTRING_FIX44);
        settings.setString(sessionID, "DataDictionary", "FIX42.xml"); //FIX44.xml

        MessageStoreFactory storeFactory = new MemoryStoreFactory();
        Initiator initiator = new SocketInitiator(this, storeFactory, settings,
                new SLF4JLogFactory(settings), new DefaultMessageFactory());
        initiator.start();

        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    stop(false);
                }
            }, 5000);

            try {
                shutdownLatch.await();
            } catch (InterruptedException e) {
            }

            if (failed) {
                String message = "TimerTestClient had to send a test request, indicating that the test server was not reliably sending heartbeats.";
                log.error(message);
                throw new RuntimeError(message);
            }
        } finally {
            initiator.stop();
        }
    }

    public void toAdmin(Message message, SessionID sessionId) {
        if (message instanceof TestRequest) {
            stop(true);
        }
    }

    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
    }

    public static void main(String[] args) throws ConfigError, SessionNotFound,
            InterruptedException {
        TimerTestOnFakeClientAcceptor ttc = new TimerTestOnFakeClientAcceptor();
        ttc.run();
    }
}
