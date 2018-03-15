package osdi.serverapi;
import quickfix.*;

public class LogonFacadeMaker {
	private Logon logon;
	
	public LogonFacadeMaker() {
		logon = new Logon();
		
	}

	public void doLogon(SessionID sessionID) {
		logon.onLogon(sessionID);
	}
}
