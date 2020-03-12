package GameConsole.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import GameConsole.Core.GameState;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;

/**
 * This class is the GUI for the domination View Panel, showing the world
 * domination situation of the list of players
 */
public class DomiInfoPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private ArrayList<JScrollPane> tables = new ArrayList<>();
	private int playerNum;
	//private GameState state;
	private List<Player> allPlayers = new ArrayList<>();
	/**
	 * Create the application.
	 */
	public DomiInfoPanel(GameState state) {
		//this.state = state;
		playerNum = state.getAllPlayers().getPlayers().size();
		allPlayers.addAll(state.getAllPlayers().getPlayers());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 350, playerNum * 180);
		this.setLayout(null);

		for (int i = 0; i < playerNum; i++) {
			JLabel newLabel = new JLabel(allPlayers.get(i).getName());
			newLabel.setBounds(10, 55 + 170 * i, 54, 15);
			this.add(newLabel);
		}

		for (int i = 0; i < playerNum; i++) {

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(60, 26 + 170 * i, 250, 140);
			this.add(scrollPane);

			Player cur = allPlayers.get(i);
			ArrayList<Country> countries = cur.getCountries();

			for (int row = 0; row < countries.size(); row++) {
				System.out.println(countries.get(row));
			}

			Object[][] model = new Object[countries.size()][3];
			for (int row = 0; row < countries.size(); row++) {
				model[row][0] = countries.get(row).getName();
				model[row][1] = countries.get(row).getTroopNum();
				model[row][2] = countries.get(row).getContinent().getName();
			}

			JTable table_11 = new JTable();
			table_11.setModel(new DefaultTableModel(model, new String[] { "CountryName", "TroopNum", "Continent" }) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			scrollPane.setViewportView(table_11);
			tables.add(scrollPane);
		}

		for (JScrollPane jTable : tables) {
			this.add(jTable);

		}

	}

	/**
	 * The override update function for Observer, would refresh the panel when
	 * the state is changed
	 */
	@Override
	public void update(Observable o, Object arg) {
		for (int i = 0; i < playerNum; i++) {
			Player cur = allPlayers.get(i);
			ArrayList<Country> countries = cur.getCountries();
			Object[][] model = new Object[countries.size()][3];
			for (int row = 0; row < countries.size(); row++) {
				model[row][0] = countries.get(row).getName();
				model[row][1] = countries.get(row).getTroopNum();
				model[row][2] = countries.get(row).getContinent().getName();
			}
			JScrollPane scrollPane = tables.get(i);
			JTable table_11 = new JTable();
			table_11.setModel(new DefaultTableModel(model, new String[] { "CountryName", "TroopNum", "Continent" }) {
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			scrollPane.setViewportView(table_11);
		}

	}
}
