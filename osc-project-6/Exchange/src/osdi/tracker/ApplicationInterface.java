package osdi.tracker;

import quickfix.*; 


public interface ApplicationInterface {
	  void onCreate(SessionID sessionId);
	  void onLogon(SessionID sessionId);
	  void onLogout(SessionID sessionId);
	  void toAdmin(Message message, SessionID sessionId);
	  void toApp(Message message, SessionID sessionId)
	    throws DoNotSend;
	  void fromAdmin(Message message, SessionID sessionId)
	    throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon;
	  void fromApp(Message message, SessionID sessionId)
	    throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType;
	}
