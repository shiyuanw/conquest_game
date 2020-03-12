package GameConsole.Core;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * The main entrance of the application
 *
 */
public class Driver extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
			WindowMain w = new WindowMain();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}