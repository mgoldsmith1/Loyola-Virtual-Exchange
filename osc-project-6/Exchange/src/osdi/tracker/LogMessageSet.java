/*
 * File     : LogMessageSet.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is a Set of LogMessage objects with utility 
 *            methods to access the individual messages.
 */

package osdi.tracker;

import java.util.ArrayList;

import osdi.locks.Semaphore;
import osdi.locks.SpinLock;
import osdi.trackerui.MessageTableModel;
import quickfix.DataDictionary;
import quickfix.Message;
import quickfix.SessionID;


public class LogMessageSet {
	private static final long serialVersionUID = 1L;
	private ArrayList<LogMessage> messages = null;
	private MessageTableModel model;
	private int messageIndex = 0;
	  //private final int bufferSize;
	    //private final java.util.Queue<T> queue;
	    private final Semaphore full;
	    private final Semaphore empty;
	    private final SpinLock spinLock;

	    

	   /* @Override
	    public void enqueue(T item) {
	        empty.down();
	        try {
	            spinLock.lock();
	            queue.add(item);
	        } finally {
	            spinLock.unlock();
	        }
	        full.up();
	    }*/
	public LogMessageSet() {
		messages = new ArrayList<LogMessage>();
		 // this.bufferSize =executions.getCount();
          //  queue = new java.util.ArrayDeque<>();
            full = new Semaphore(0);
            empty = new Semaphore(100);
            spinLock = new SpinLock();
   // }
		
	}
	
	public void add(Message message, boolean incoming, 
                DataDictionary dictionary, SessionID sessionID) {
		empty.down();
        try {
        	spinLock.lock();
        
		messageIndex++;
		LogMessage msg = 
                        new LogMessage(messageIndex, incoming, sessionID,
				message.toString(), dictionary);
                messages.add(msg);
                int limit = 50;
                try {
                    limit = (int)FIXTracker.getApplication().getSettings()
                            .getLong("FIXimulatorCachedObjects");
                } catch ( Exception e ) {}
                while ( messages.size() > limit ) {
                    messages.remove(0);
                }
		//call back to the model to update
		model.update();		
        } finally {
            spinLock.unlock();
        }
        full.up();
	}
	
	public LogMessage getMessage( int i ) {
		return messages.get(i);
	}
	
	public int getCount(){
		return messages.size();
	}

	public void addCallback(MessageTableModel model) {
		this.model = model;	
	}
}
