package MapEditor.View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;
import MapEditor.Model.Territory;
import MapEditor.Util.MyStringUtil;

/**
 * this class is the GUI for inputing Territory.
 */
public class InputTerritoryFrame {
	private JFrame frmInputTerritory;
	private JTextField neighbourNames;
	private JTextArea tName, tCenterX, tCenterY;
	private JLabel errMsg = new JLabel();
	private JComboBox<Continent> comboBox;
	private JButton confirmBtn, cancelBtn;
	private Territory unchanged;
	private Territory changed;
	private ConquestMap map;

	/**
	 * construction method receiving Territory name and a conquest map file.
	 * 
	 * @param unchangedName
	 *            territory name
	 * @param map
	 *            conquest map file.
	 */
	public InputTerritoryFrame(String unchangedName, ConquestMap map) {
		this.map = map;
		this.unchanged = map.findTerritory(unchangedName);
		initialize();
	}

	/**
	 * method to initialize the input territory window.
	 */
	private void initialize() {
		frmInputTerritory = new JFrame();
		frmInputTerritory.setTitle("Input Territory");
		frmInputTerritory.setBounds(100, 100, 528, 656);
		frmInputTerritory.getContentPane().setLayout(null);

		errMsg.setBounds(60, 65, 400, 15);
		errMsg.setForeground(Color.RED);
		frmInputTerritory.getContentPane().add(errMsg);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(60, 90, 54, 15);
		frmInputTerritory.getContentPane().add(lblName);

		tName = new JTextArea();
		tName.setBounds(193, 89, 114, 24);
		frmInputTerritory.getContentPane().add(tName);

		JLabel lblLocation = new JLabel("location\uFF1A");
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setBounds(38, 210, 94, 15);
		frmInputTerritory.getContentPane().add(lblLocation);

		tCenterX = new JTextArea();
		tCenterX.setBounds(193, 211, 54, 24);
		frmInputTerritory.getContentPane().add(tCenterX);

		JLabel lblNewLabel = new JLabel("Continent:");
		lblNewLabel.setBounds(53, 330, 61, 15);
		frmInputTerritory.getContentPane().add(lblNewLabel);

		comboBox = new JComboBox<>();
		comboBox.setBounds(193, 329, 128, 24);
		Continent[] values = new Continent[map.continents.size() + 1];
		values[0] = null;
		for (int index = 1; index < values.length; index++) {
			values[index] = map.continents.get(index - 1);
		}

		comboBox.setModel(new DefaultComboBoxModel<>(values));
		frmInputTerritory.getContentPane().add(comboBox);

		JLabel lblNewLabel_1 = new JLabel(",");
		lblNewLabel_1.setBounds(257, 220, 54, 15);
		frmInputTerritory.getContentPane().add(lblNewLabel_1);

		tCenterY = new JTextArea();
		tCenterY.setBounds(275, 211, 54, 24);
		frmInputTerritory.getContentPane().add(tCenterY);

		JLabel lblNewLabel_2 = new JLabel("Neighbours(split with \",\"):");
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(38, 417, 198, 48);
		frmInputTerritory.getContentPane().add(lblNewLabel_2);

		neighbourNames = new JTextField();
		neighbourNames.setToolTipText("");
		neighbourNames.setBounds(60, 475, 387, 74);
		frmInputTerritory.getContentPane().add(neighbourNames);
		neighbourNames.setColumns(10);

		confirmBtn = new JButton("comfirm");
		confirmBtn.setBounds(60, 584, 93, 23);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errMsg.setText("");
				if (validateInput()) {
					changed = new Territory();
					String name = tName.getText().trim();
					int centerX = Integer.parseInt(tCenterX.getText().trim());
					int centerY = Integer.parseInt(tCenterY.getText().trim());
					String[] linkNames = neighbourNames.getText().trim().split(",");
					Continent c = (Continent) comboBox.getSelectedItem();
					if (linkNames.length > 0) {
						for (String linkName : linkNames) {
							changed.getLinkNames().add(linkName);
						}
					}
					changed.setName(name);
					changed.setCenter(centerX, centerY);
					changed.setContinent(c);

					map.deleteTerritory(unchanged);
					map.addTerritory(changed);
					map.buildTerritoryLinks(changed);
					frmInputTerritory.setVisible(false);
				}
			}

		});
		frmInputTerritory.getContentPane().add(confirmBtn);

		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmInputTerritory.setVisible(false);
			}
		});
		cancelBtn.setBounds(354, 584, 93, 23);

		frmInputTerritory.getContentPane().add(cancelBtn);

		if (unchanged != null) {
			tName.setText(unchanged.getName());
			tCenterX.setText(String.valueOf(unchanged.getCenterX()));
			tCenterY.setText(String.valueOf(unchanged.getCenterY()));
			comboBox.setSelectedItem(unchanged.getContinent());
			if (unchanged.getLinkNames().size() > 0) {
				neighbourNames.setText(MyStringUtil.joinString(
						unchanged.getLinkNames().toArray(new String[unchanged.getLinkNames().size()]), ","));
			}
		}

		frmInputTerritory.setVisible(true);
	}

	/**
	 * territory that before editing.
	 * 
	 * @return unchanged territory
	 */
	public Territory getUnchanged() {
		return unchanged;
	}

	/**
	 * set the unchanged territory.
	 * 
	 * @param unchanged
	 *            original territory information
	 */
	public void setUnchanged(Territory unchanged) {
		this.unchanged = unchanged;
	}

	/**
	 * method to get the territory information if the territory has been
	 * changed.
	 * 
	 * @return new territory
	 */
	public Territory getChanged() {
		return changed;
	}

	/**
	 * method to set the new territory of the edited territory information
	 * 
	 * @param changed
	 *            the edited territory
	 */
	public void setChanged(Territory changed) {
		this.changed = changed;
	}

	/**
	 * check the editions of changing information of the territory if valid.
	 * 
	 * @return true if changing is valid, otherwise false and out put the error
	 *         message
	 */
	private boolean validateInput() {
		if (!MyStringUtil.hasLength(tName.getText()) || !MyStringUtil.hasLength(tCenterX.getText())
				|| !MyStringUtil.hasLength(tCenterY.getText())) {
			errMsg.setText("Name and Location cannot be empty!");
			return false;
		}

		if ((unchanged != null && !tName.getText().trim().equals(unchanged.getName())
				&& map.findTerritory(tName.getText().trim()) != null)
				|| (unchanged == null && map.findTerritory(tName.getText().trim()) != null)) {
			errMsg.setText("The name is already in use!");
			return false;
		}

		if (!MyStringUtil.isNumeric(tCenterX.getText()) || !MyStringUtil.isNumeric(tCenterY.getText())) {
			errMsg.setText("Please enter the number in (X, Y) location!");
			return false;
		}
		if (neighbourNames.getText().contains(tName.getText())) {
			errMsg.setText("Cannot have a connection with itself!");
			return false;
		}

		if (MyStringUtil.hasLength(neighbourNames.getText())) {
			List<String> neighbourList = Arrays.asList(neighbourNames.getText().split(","));
			for (String name : neighbourList) {
				if (map.findTerritory(name) == null) {
					errMsg.setText("Please enter the correct name of Linked Territory!");
					return false;
				}
			}
		}
		return true;
	}

}
