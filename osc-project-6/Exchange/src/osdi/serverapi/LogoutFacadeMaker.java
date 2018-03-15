package osdi.serverapi;
import quickfix.*;

public class LogoutFacadeMaker {
	private Logout logout;
	
	public LogoutFacadeMaker() {
		logout = new Logout();
	}
	
	public void doLogout(SessionID sessionId) {
		logout.onLogout(sessionId);
	}
}
