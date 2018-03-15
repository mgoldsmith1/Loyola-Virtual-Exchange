package osdi.serverapi;

import quickfix.Message;
import quickfix.SessionID;

public interface toAdminInterface {
	
	void toAdmin(Message message, SessionID sessionId);
	
}
