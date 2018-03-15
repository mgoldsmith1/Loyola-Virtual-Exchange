package osdi.serverapi;

import quickfix.*;

public class CreateFacadeMaker {
	
	private Create create;
	// private Shape rectangle;
	// private Shape square;

	public CreateFacadeMaker() {
		create = new Create();
		// rectangle = new Rectangle();
		// square = new Square();
	}

	public void doCreate(SessionID sessionID) {
		create.onCreate(sessionID);
	}

	/*
	 * public void drawCircle(){ 
	 * 		circle.draw(); 
	 * } 
	 * public void drawRectangle(){
	 * 		rectangle.draw(); 
	 * } 
	 * public void drawSquare(){ 
	 * 		square.draw(); 
	 * }
	 */
}
