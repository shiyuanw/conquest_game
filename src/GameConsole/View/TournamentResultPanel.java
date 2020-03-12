package GameConsole.View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 * This class is the user interface for the beginning of the tournament mode
 * where the user can select maps, computer players, number of games and turns
 */
public class TournamentResultPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	public JButton returnToMainFrame;

	/**
	 * Create the panel.
	 */
	public TournamentResultPanel(Object[][] result) {
		setLayout(null);
		setBounds(0, 0, 1200, 900);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(300, 348, 608, 108);
		add(scrollPane);
		returnToMainFrame = new JButton("Return");
		add(returnToMainFrame);
		returnToMainFrame.setBounds(400, 800, 200, 30);

		table = new JTable();
		scrollPane.setViewportView(table);

		int row = result.length;
		int col = result[0].length;

		Object[][] table1 = new Object[row][col + 1];
		for (int i = 0; i < row; i++) {
			table1[i][0] = "Map " + (i + 1);
		}

		String[] table2 = new String[col + 1];
		for (int i = 0; i < col + 1; i++) {
			if (i == 0)
				table2[0] = "";
			else
				table2[i] = "Game " + i;
		}

		for (int i = 0; i < table1.length; i++) {
			for (int j = 1; j < table1[0].length; j++) {
				table1[i][j] = result[i][j - 1];
			}
		}

		table.setModel(new DefaultTableModel(table1, table2) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, true, true, true, true, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		JLabel lblNewLabel = new JLabel("Tournamet Result");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel.setBounds(20, 62, 655, 98);
		add(lblNewLabel);
		setVisible(true);
	}
}
