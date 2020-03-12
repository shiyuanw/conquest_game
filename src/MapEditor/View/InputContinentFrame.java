package MapEditor.View;

import java.awt.Color;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;
import MapEditor.Util.MyStringUtil;

/**
 * this class is GUI for input continent window.
 */
public class InputContinentFrame {
	private JFrame frmInputcontinent;
	private JTextField tName, tBonus;
	private JButton confirmBtn, cancelBtn;
	private JLabel errMsg = new JLabel();

	private ConquestMap map;
	private Continent unchanged;
	private Continent changed;

	/**
	 * Create the application.
	 * 
	 * @param unchangedName
	 *            continent's name
	 * @param map
	 *            conquest map
	 */

	public InputContinentFrame(String unchangedName, ConquestMap map) {
		this.map = map;
		this.unchanged = map.findContinent(unchangedName);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInputcontinent = new JFrame();
		frmInputcontinent.setType(Type.UTILITY);
		frmInputcontinent.setTitle("InputContinent");
		frmInputcontinent.setBounds(100, 100, 520, 428);
		frmInputcontinent.getContentPane().setLayout(null);

		errMsg.setBounds(94, 60, 400, 15);
		errMsg.setForeground(Color.RED);
		frmInputcontinent.getContentPane().add(errMsg);

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(94, 84, 54, 15);
		frmInputcontinent.getContentPane().add(lblNewLabel);

		tName = new JTextField();
		tName.setBounds(189, 81, 118, 21);
		frmInputcontinent.getContentPane().add(tName);
		tName.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Bonus:");
		lblNewLabel_1.setBounds(94, 152, 54, 15);
		frmInputcontinent.getContentPane().add(lblNewLabel_1);

		tBonus = new JTextField();
		tBonus.setBounds(189, 149, 118, 21);
		frmInputcontinent.getContentPane().add(tBonus);
		tBonus.setColumns(10);

		confirmBtn = new JButton("Confirm");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errMsg.setText("");
				if (validateInput()) {
					changed = new Continent();
					String name = tName.getText().trim();
					int bonus = Integer.parseInt(tBonus.getText().trim());
					changed.setName(name);
					changed.setBonus(bonus);

					if (unchanged == null) {
						map.addContinent(changed);
					} else {
						map.updateContinent(unchanged.getName(), name, bonus);
					}

					unchanged = changed;
					for (Continent t : map.continents) {
						System.out.println(t);
					}
					frmInputcontinent.setVisible(false);
				}
			}

		});
		confirmBtn.setBounds(71, 302, 93, 23);
		frmInputcontinent.getContentPane().add(confirmBtn);

		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmInputcontinent.setVisible(false);
			}
		});
		cancelBtn.setBounds(284, 302, 93, 23);
		frmInputcontinent.getContentPane().add(cancelBtn);

		if (unchanged != null) {
			tName.setText(unchanged.getName());
			tBonus.setText(String.valueOf(unchanged.getBonus()));
		}

		frmInputcontinent.setVisible(true);
	}

	/**
	 * method to check the input is the specific Integer type and it cannot be
	 * null.
	 * 
	 * @return true if input is valid, otherwise false.
	 */
	private boolean validateInput() {
		if (!MyStringUtil.isNumeric(tBonus.getText())) {
			errMsg.setText("Bonus must be a Integer!");
			return false;
		}
		if (!MyStringUtil.hasLength(tName.getText()) || !MyStringUtil.hasLength(tBonus.getText())) {
			errMsg.setText("Name and Bonus cannot be empty!");
			return false;
		}
		if (map.findContinent(tName.getText()) != null
				&& (unchanged == null || !tName.getText().equals(unchanged.getName()))) {
			errMsg.setText("The name has already existed!");
			return false;
		}

		return true;
	}

}
