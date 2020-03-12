package GameConsole.View;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * this class is the GUI for the logpanel, to show the any message when editing
 * a conquest map. The LogPanel is Singleton
 */
public class LogPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public JTextArea log = new JTextArea();
	private final String NEWLINE = "\n";
	private static LogPanel newInstance = new LogPanel();

	/**
	 * private constructor method, setting the logPanel location.
	 */
	private LogPanel() {
		super(new BorderLayout());

		log = new JTextArea(30, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);
		add(logScrollPane, BorderLayout.CENTER);
	}

	/**
	 * To get the only instance of the class
	 * 
	 * @return The singleton instance of the class
	 */
	public static LogPanel getInstance() {
		if (newInstance == null)
			newInstance = new LogPanel();
		return newInstance;
	}

	/**
	 * The method to attach a log to the panel
	 * 
	 * @param msg
	 *            The input log message
	 */
	public synchronized void addLog(String msg) {
		log.append(msg + NEWLINE);
	}

}
