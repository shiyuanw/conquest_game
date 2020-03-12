package GameConsole.Strategy;

import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GameConsole.View.LogPanel;

/**
 * A concrete Strategy that implements human strategy operation
 */
public class HumanStrategy extends OriginalStrategy implements Strategy {

	/**
	 * Constructor for HumanStrategy
	 */
	public HumanStrategy() {
		super();
		this.setName("Human");
	}

	/**
	 * Method to attack.
	 */
	@Override
	public void attack() {
		LogPanel lp = LogPanel.getInstance();
		while (getGameState().getCurrPhase() == 1) {
			if (!getPlayer().checkIfCanAttack()) {
				return;
			}
			System.out.println((getGameState().getCountry1() == null) + ":" + (getGameState().getCountry2() == null));
			if (getGameState().getCountry1() != null && getGameState().getCountry2() != null) {

				JPanel numdice1 = new JPanel();
				JLabel label = new JLabel("Attacker selects how many dice to roll");
				numdice1.add(label);
				DefaultComboBoxModel<String> select1 = new DefaultComboBoxModel<>();
				for (int i = Math.min(getGameState().getCountry1().getTroopNum(), 3); i >= 1; i--) {
					select1.addElement(Integer.toString(i));
				}
				JComboBox<String> list1 = new JComboBox<>(select1);
				numdice1.add(list1);
				int message1 = -1;
				while (message1 != JOptionPane.OK_OPTION) {
					message1 = JOptionPane.showConfirmDialog(null, numdice1, "Number of Dices",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				}
				int decision1 = Integer.parseInt(list1.getSelectedItem().toString());

				numdice1.remove(label);
				numdice1.remove(list1);
				int decision2 = 1;
				if (getGameState().getCountry2().getPlayer().getStrategy().getName().equals("Human")) {
					numdice1.add(new JLabel("Defender selects how many dice to roll"));
					DefaultComboBoxModel<String> select2 = new DefaultComboBoxModel<>();
					for (int i = 1; i <= Math.min(getGameState().getCountry2().getTroopNum(), 2); i++) {
						select2.addElement(Integer.toString(i));
					}
					JComboBox<String> list2 = new JComboBox<>(select2);
					numdice1.add(list2);
					int message2 = -1;
					while (message2 != JOptionPane.OK_OPTION) {
						message2 = JOptionPane.showConfirmDialog(null, numdice1, "Number of Dices",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					}
					decision2 = Integer.parseInt(list2.getSelectedItem().toString());
				}
				try {
					Map<String, Object> result = getGameState().getCurrPlayer().originalAttack(
							getGameState().getCountry1(), getGameState().getCountry2(), decision1, decision2);
					JOptionPane.showConfirmDialog(null, (String) result.get("dice"), "Dice Result",
							JOptionPane.OK_OPTION);

					if ((Boolean) result.get("result")) {
						int moveNum = 0;
						JPanel numPanel = new JPanel();
						numPanel.add(new JLabel("Congrats you conquered " + getGameState().getCountry2().getName()
								+ " with " + getGameState().getCountry1().getName()
								+ ". How many troops would you like to add?"));
						lp.addLog("Congrats " + getGameState().getCurrPlayer().getName() + " conquered "
								+ getGameState().getCountry2().getName() + " with "
								+ getGameState().getCountry1().getName());
						DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
						for (int i = getGameState().getCountry1().getTroopNum() - 1; i >= 1; i--) {
							selection.addElement(Integer.toString(i));
						}
						JComboBox<String> comboBox = new JComboBox<String>(selection);
						numPanel.add(comboBox);

						int result1 = -1;
						while (result1 != JOptionPane.OK_OPTION) {
							result1 = JOptionPane.showConfirmDialog(null, numPanel, "Number of Troops",
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						}
						moveNum = Integer.parseInt(comboBox.getSelectedItem().toString());
						getGameState().getCountry2().addInfrantry(moveNum);
						getGameState().getCountry1().removeTroops(moveNum);
						lp.addLog(getGameState().getCurrPlayer().getName() + " leaves " + moveNum + " troops!");

					}
				} catch (Exception e) {

				}
				clearLabel();
				updateLabel();
			}

		}

	}

	/**
	 * Method to reinforce.
	 */
	@Override
	public void reinforce() {
		if (getGameState().getFirstRound() == 1 && getPlayer().getInitTroop() > 0) {
			while (true) {
				System.out.println("null");
				if (this.getGameState().getCurrClick() != null) {
					this.getPlayer().addInfantry(this.getGameState().getCurrClick());
					this.getPlayer().setInitTroop(getPlayer().getInitTroop() - 1);
					clearLabel();
					return;
				}
			}
		} else {
			while (getPlayer().getInitTroop() > 0) {
				System.out.println(this.getGameState().getCurrClick());
				if (this.getGameState().getCurrClick() != null) {
					this.getPlayer().addInfantry(this.getGameState().getCurrClick());
					this.getPlayer().setInitTroop(getPlayer().getInitTroop() - 1);
					clearLabel();
					updateLabel();
				}
			}
		}
	}

	/**
	 * Method to fortify
	 */
	@Override
	public void fortify() {
		getPlayer().giveCards();
		getPlayer().setHasMoved(false);
		while (getGameState().getCurrPhase() == 2) {
			System.out.println("fortify!!!!!!");
			if (getGameState().getCountry1() != null && getGameState().getCountry2() != null
					&& !getPlayer().isHasMoved()) {
				JPanel numPanel = new JPanel();
				numPanel.add(new JLabel("Select how many troops to add"));
				DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
				for (int i = 1; i < getGameState().getCountry1().getTroopNum(); i++) {
					selection.addElement(Integer.toString(i));
				}
				JComboBox<String> comboBox = new JComboBox<String>(selection);
				numPanel.add(comboBox);
				int result = -1;
				while (result != JOptionPane.OK_OPTION && result != JOptionPane.CANCEL_OPTION) {
					result = JOptionPane.showConfirmDialog(null, numPanel, "Number of Troops",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				}
				if (result == JOptionPane.OK_OPTION) {
					getPlayer().moveTroops(getGameState().getCountry1(), getGameState().getCountry2(),
							Integer.parseInt(comboBox.getSelectedItem().toString()));
					getPlayer().setHasMoved(true);
				} else {
					JOptionPane.showMessageDialog(null, "Move was cancelled.");
					LogPanel.getInstance().addLog("Move was cancelled.");
					getGameState().setCountry1(null);
					getGameState().setCountry2(null);
					getGameState().getWindow().country1.setText((String) null);
					getGameState().getWindow().country2.setText((String) null);
				}
				clearLabel();
				updateLabel();
			}

		}

	}

	/**
	 * Method to update some labels
	 */
	public void updateLabel() {
		getGameState().updateCountryLabels();
		getGameState().getWindow().country1.setText((String) null);
		getGameState().getWindow().country2.setText((String) null);
		getGameState().getWindow().troopsLeft = getPlayer().getInitTroop();
		getGameState().getWindow().numberOfTroops.setText("" + getGameState().getWindow().troopsLeft);

	}

	/**
	 * Method to clear some labels
	 */
	public void clearLabel() {
		getGameState().setCountry1(null);
		getGameState().setCountry2(null);
		getGameState().setCurrClick(null);
	}

}
