package osdi.serverapi;

import quickfix.*;

public class toAppFacadeMaker {

	private toApp toAppImp;

  public toAppFacadeMaker() {
		toAppImp = new toApp();
		// rectangle = new Rectangle();
		// square = new Square();
	}

  public void toApp(Message message, SessionID sessionId) throws DoNotSend {
    toAppImp.toApp(message, sessionId);
  }

}
