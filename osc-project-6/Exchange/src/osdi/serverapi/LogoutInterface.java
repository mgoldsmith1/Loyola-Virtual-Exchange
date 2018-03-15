package osdi.serverapi;
import quickfix.*;

public interface LogoutInterface {

	void onLogout(SessionID sessionId);
	
}
