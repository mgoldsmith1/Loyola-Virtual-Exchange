
package osdi.serverapi;
import quickfix.*;

public interface CreateInterface {
	
  void onCreate(SessionID sessionId);

}
/*
 //https://www.tutorialspoint.com/design_pattern/facade_pattern.htm
  
  //DONE
  void onCreate(SessionID sessionId); 
  void onLogon(SessionID sessionId);
  
  //TO DO
  void onLogout(SessionID sessionId);  
  void toAdmin(Message message, SessionID sessionId);
  void toApp(Message message, SessionID sessionId)
    throws DoNotSend;
  void fromAdmin(Message message, SessionID sessionId)
    throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon;
  void fromApp(Message message, SessionID sessionId)
    throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType;
 */