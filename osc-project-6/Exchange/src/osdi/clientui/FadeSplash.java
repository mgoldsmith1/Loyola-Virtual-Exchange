package osdi.clientui;

import java.util.TimerTask;
import javax.swing.JWindow;
import com.sun.awt.AWTUtilities;

public class FadeSplash extends TimerTask {

	private JWindow window;

	public FadeSplash(JWindow window) {
		this.window = window;
	}

	@Override
	public void run() {
		// The opacity is goes down by 0.01f 
		// When opacity equals 0 (invisible), window is disposed.
		if (AWTUtilities.getWindowOpacity(window) > 0.01f) {
			AWTUtilities.setWindowOpacity(window, AWTUtilities.getWindowOpacity(window) - 0.01f);
		} else window.dispose();
	}
}