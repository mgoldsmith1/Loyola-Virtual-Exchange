package osdi.listener;

public class ClientLogMessageRecorder {
	   public static void main(String[] args) {
	      Subject subject = new Subject();

	      new ClientLogMessageListener(subject);

	      System.out.println("First state change: ");	
	      subject.setFIXTrackerMessageState("LRF");
	      System.out.println("Second state change: ");	
	      subject.setFIXTrackerMessageState("MRF");
	   }
	}
