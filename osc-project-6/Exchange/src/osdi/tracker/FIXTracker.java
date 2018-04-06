/*
 * File     : FIXimulator.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This is the class that initializes the application.
 * 
 */

package osdi.tracker;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import quickfix.Acceptor;
import quickfix.CompositeLogFactory;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FieldConvertError;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.JdbcLogFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.ScreenLogFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;

public class FIXTracker {
    private static final long serialVersionUID = 1L;
    private Acceptor acceptor = null;
    private static FIXTrackerApplication application = null;
    private static InstrumentSet instruments = null;
    private static LogMessageSet messages = null;
    private String _path = new File("").getAbsolutePath();
    public FIXTracker() {
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream( 
                            new FileInputStream( 
                            new File( _path +"/Exchange/src/osdi/tracker/FIXTracker.cfg" )));
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.exit(0);
        }
        instruments = new InstrumentSet(new File(_path +"/Exchange/src/osdi/tracker/university.xml")); //instruments.xml
        messages = new LogMessageSet();
        try {
            SessionSettings settings = new SessionSettings( inputStream );
            application = new FIXTrackerApplication( settings, messages );
            MessageStoreFactory messageStoreFactory = 
                    new FileStoreFactory( settings );
            boolean logToFile = false;
            boolean logToDB = false;
            LogFactory logFactory;
            try {
                logToFile = settings.getBool("FIXTrackerLogToFile");
                logToDB = settings.getBool("FIXTrackerLogToDB");
            } catch (FieldConvertError ex) {}
            if ( logToFile && logToDB ) {
                logFactory = new CompositeLogFactory(
                    new LogFactory[] { new ScreenLogFactory(settings), 
                                       new FileLogFactory(settings),
                                       new JdbcLogFactory(settings)});
            } else if ( logToFile ) {
                logFactory = new CompositeLogFactory(
                    new LogFactory[] { new ScreenLogFactory(settings), 
                                       new FileLogFactory(settings)});
            } else if ( logToDB ) {
                logFactory = new CompositeLogFactory(
                    new LogFactory[] { new ScreenLogFactory(settings), 
                                       new JdbcLogFactory(settings)});
            } else {
                logFactory = new ScreenLogFactory(settings);
            }
            MessageFactory messageFactory = new DefaultMessageFactory();
            acceptor = new SocketAcceptor
                    ( application, messageStoreFactory, 
                            settings, logFactory, messageFactory );
        } catch (ConfigError e) {
            e.printStackTrace();
        }		
    }

    public static InstrumentSet getInstruments() {
        return instruments;
    }
    
    public static FIXTrackerApplication getApplication() {
        return application;
    }
	
    public static LogMessageSet getMessageSet() {
        return messages;
    }
    
    public void start() {
        try {
            acceptor.start();
        } catch ( Exception e ) {
            System.out.println( e );
        }
    }
}

