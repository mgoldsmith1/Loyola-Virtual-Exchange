package osdi.listener;

public abstract class Observer {
	   protected Subject subject;
	   public abstract void update();
}