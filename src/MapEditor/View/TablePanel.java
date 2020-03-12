package MapEditor.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import MapEditor.Model.ConquestMap;

/**
 * this class is GUI for territories and continents table panels. When doing
 * some changes of the map, tables will update all the information.
 */
public class TablePanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private ConquestMap map;
	private JTable terTable, contTable;
	private JButton terAddBtn, terEditBtn, terDelBtn; // ter
	private JButton contAddBtn, contEditBtn, contDelBtn;// cont
	private Object[][] terNames;
	private Object[][] contNames;

	/**
	 * Create the application.
	 */
	public TablePanel(ConquestMap map) {
		this.map = map;
		initialize();
	}

	/**
	 * TablePanel is as a Observer when some changes occur it updates the
	 * changed and show it on the table.
	 */
	public void update(Observable o, Object args) {
		if (map.territories.size() > 0) {
			terNames = new String[map.territories.size()][1];
			for (int i = 0; i < map.territories.size(); i++) {
				terNames[i][0] = map.territories.get(i).getName();
			}

			terTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			terTable.setModel(new DefaultTableModel(terNames, new String[] { "Territory" }) {
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] { false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		} else {
			terTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			terTable.setModel(new DefaultTableModel(null, new String[] { "Territory" }));
		}
		if (map.continents.size() > 0) {
			contNames = new String[map.continents.size()][1];
			for (int i = 0; i < map.continents.size(); i++) {
				contNames[i][0] = map.continents.get(i).getName();
			}

			contTable.setModel(new DefaultTableModel(contNames, new String[] { "Continent" }) {
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] { false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		} else {
			contTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			contTable.setModel(new DefaultTableModel(null, new String[] { "Continent" }));
		}

		if (map.getMapFilePath() != null)
			setVisible(true);
		else
			setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 163, 560);
		add(scrollPane_1);

		terTable = new JTable();
		terTable.setColumnSelectionAllowed(true);
		terTable.setCellSelectionEnabled(true);
		terTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		terTable.setModel(new DefaultTableModel(terNames, new String[] { "Territory" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(terTable);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(183, 10, 163, 560);
		add(scrollPane_2);

		contTable = new JTable();
		contTable.setColumnSelectionAllowed(true);
		contTable.setCellSelectionEnabled(true);
		contTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contTable.setModel(new DefaultTableModel(contNames, new String[] { "Continent" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_2.setViewportView(contTable);

		terAddBtn = new JButton("Add");
		terAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InputTerritoryFrame("", map);
			}
		});
		terAddBtn.setBounds(46, 590, 93, 23);
		add(terAddBtn);

		terEditBtn = new JButton("Edit");
		terEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InputTerritoryFrame((String) terNames[terTable.getSelectedRow()][0], map);
			}
		});
		terEditBtn.setBounds(46, 620, 93, 23);
		add(terEditBtn);

		terDelBtn = new JButton("Delete");
		terDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.deleteTerritory(map.findTerritory((String) terNames[terTable.getSelectedRow()][0]));
			}
		});
		terDelBtn.setBounds(46, 650, 93, 23);
		add(terDelBtn);

		contAddBtn = new JButton("Add");
		contAddBtn.setBounds(218, 590, 93, 23);
		contAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InputContinentFrame("", map);
			}
		});
		add(contAddBtn);

		contEditBtn = new JButton("Edit");
		contEditBtn.setBounds(218, 620, 93, 23);
		contEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InputContinentFrame((String) contNames[contTable.getSelectedRow()][0], map);
			}
		});
		add(contEditBtn);

		contDelBtn = new JButton("Delete");
		contDelBtn.setBounds(218, 650, 93, 23);
		contDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.deleteContinent(map.findContinent((String) contNames[contTable.getSelectedRow()][0]));
			}
		});
		add(contDelBtn);
	}

	/**
	 * get the territories' name of the map.
	 * 
	 * @return all the territories on the table.
	 */
	public Object[][] getTerNames() {
		return terNames;
	}

	/**
	 * any territory that want to be edited, setting it.
	 * 
	 * @param terNames
	 *            return a new territories array of the map.
	 */
	public void setTerNames(Object[][] terNames) {
		this.terNames = terNames;
	}

	/**
	 * getting the continents name of a conquest map.
	 * 
	 * @return all the continents on the table.
	 */
	public Object[][] getContNames() {
		return contNames;
	}

	/**
	 * any continent that wants to be changed, setting it.
	 * 
	 * @param contNames
	 *            return as the new continents array of the map.
	 */
	public void setContNames(Object[][] contNames) {
		this.contNames = contNames;
	}

}
