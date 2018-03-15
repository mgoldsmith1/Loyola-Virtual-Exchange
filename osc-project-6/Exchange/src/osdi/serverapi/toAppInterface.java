
package osdi.serverapi;
import quickfix.*;


public interface toAppInterface {

  void toApp(Message message, SessionID sessionId)
	throws DoNotSend;
}
