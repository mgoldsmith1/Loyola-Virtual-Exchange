package osdi.listener;

public class ClientLogMessageListener extends Observer{

	   public ClientLogMessageListener(Subject subject){
	      this.subject = subject;
	      this.subject.attach(this);
	   }

	   //once setState method in Subject.java is set,
	   //this update method is called and passed the 
	   //new information
	   @Override
	   public void update() {
	      System.out.println( "Client recieved updated message: " + subject.getFIXTrackerMessageState()  ); 
	   }
	}
