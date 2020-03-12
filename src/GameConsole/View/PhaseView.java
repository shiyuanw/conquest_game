package GameConsole.View;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GameConsole.Core.GameState;
import GameConsole.Core.WindowMain;
import GameConsole.Model.Player.Player;

/**
 * This class is the GUI for the Phase View Panel, showing the current phase of
 * conquest game and current player
 */
public class PhaseView implements Observer {
	private JLabel moveStageLabel, attackStageLabel, obtainTroopLabel;
	private JPanel arrow0, arrow1, arrow2;
	private GameState gameState;
	private WindowMain win;

	public JPanel phasePanel;
	public JPanel namePanel;
	public JFormattedTextField player1Name, player2Name, player3Name, player4Name, player5Name;

	private LogPanel lp = LogPanel.getInstance();

	/**
	 * constructor method initialize phase view panel
	 * 
	 * @param state
	 *            current phase of game
	 * @param win
	 *            mainWindow of GameConsole
	 */
	public PhaseView(GameState state, WindowMain win) {
		this.gameState = state;
		this.win = win;
		arrow0 = new JPanel();
		arrow1 = new JPanel();
		arrow2 = new JPanel();
		phasePanel = new JPanel();
		phasePanel.setVisible(true);
		phasePanel.setBackground(Color.LIGHT_GRAY);
		phasePanel.setLayout(null);

		JPanel panel_15 = new JPanel();
		panel_15.setBackground(Color.LIGHT_GRAY);

		panel_15.setBounds(40, 0, 200, 31);
		panel_15.setLayout(null);

		obtainTroopLabel = new JLabel("");
		obtainTroopLabel.setBounds(0, 0, 200, 31);
		panel_15.add(obtainTroopLabel);
		obtainTroopLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JPanel panel_16 = new JPanel();
		panel_16.setBackground(Color.LIGHT_GRAY);
		panel_16.setBounds(40, 40, 200, 31);
		panel_16.setLayout(null);

		attackStageLabel = new JLabel("");
		attackStageLabel.setBounds(0, 0, 200, 31);
		panel_16.add(attackStageLabel);
		attackStageLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		attackStageLabel.setVisible(false);

		JPanel panel_17 = new JPanel();
		panel_17.setBackground(Color.LIGHT_GRAY);
		panel_17.setBounds(40, 80, 200, 31);
		panel_17.setLayout(null);

		moveStageLabel = new JLabel("");
		moveStageLabel.setBounds(0, 0, 200, 31);
		panel_17.add(moveStageLabel);
		moveStageLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		moveStageLabel.setVisible(false);

		arrow0.setBackground(Color.LIGHT_GRAY);
		arrow0.setBounds(0, 0, 30, 30);
		arrow0.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("resources/GimpFiles/arrow.png"));
		lblNewLabel_3.setBounds(0, 0, 30, 30);
		arrow0.add(lblNewLabel_3);

		arrow1.setBounds(0, 40, 30, 30);
		arrow1.setLayout(null);
		arrow1.setBackground(Color.LIGHT_GRAY);

		JLabel label_7 = new JLabel("");
		label_7.setIcon(new ImageIcon("resources/GimpFiles/arrow.png"));
		label_7.setBounds(0, 0, 30, 30);
		arrow1.add(label_7);

		arrow2.setLayout(null);
		arrow2.setBackground(Color.LIGHT_GRAY);
		arrow2.setBounds(0, 80, 30, 30);

		JLabel label_8 = new JLabel("");
		label_8.setIcon(new ImageIcon("resources/GimpFiles/arrow.png"));

		label_8.setBounds(0, 0, 30, 30);
		arrow2.add(label_8);

		obtainTroopLabel.setVisible(true);
		attackStageLabel.setVisible(false);
		moveStageLabel.setVisible(false);
		arrow0.setVisible(true);
		arrow1.setVisible(false);
		arrow2.setVisible(false);
		obtainTroopLabel.setText("Startup phase");

		phasePanel.add(arrow0);
		phasePanel.add(arrow1);
		phasePanel.add(arrow2);
		phasePanel.add(panel_15);
		phasePanel.add(panel_16);
		phasePanel.add(panel_17);

		namePanel = new JPanel();
		namePanel.setLayout(null);
		namePanel.setVisible(true);
		namePanel.setBackground(Color.LIGHT_GRAY);

		player1Name = new JFormattedTextField();
		player1Name.setBackground(Color.GRAY);
		player2Name = new JFormattedTextField();
		player3Name = new JFormattedTextField();
		player4Name = new JFormattedTextField();
		player5Name = new JFormattedTextField();
		player1Name.setVisible(false);
		player2Name.setVisible(false);
		player3Name.setVisible(false);
		player4Name.setVisible(false);
		player5Name.setVisible(false);

		player1Name.setBackground(Color.LIGHT_GRAY);
		player1Name.setForeground(Color.CYAN);
		player1Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player1Name.setHorizontalAlignment(SwingConstants.CENTER);
		player1Name.setEditable(false);
		player1Name.setBounds(0, 0, 175, 60);

		player2Name.setHorizontalAlignment(SwingConstants.CENTER);
		player2Name.setForeground(Color.MAGENTA);
		player2Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player2Name.setEditable(false);
		player2Name.setBackground(Color.LIGHT_GRAY);
		player2Name.setBounds(200, 0, 175, 60);

		player3Name.setHorizontalAlignment(SwingConstants.CENTER);
		player3Name.setForeground(Color.GREEN);
		player3Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player3Name.setEditable(false);
		player3Name.setBackground(Color.LIGHT_GRAY);
		player3Name.setBounds(400, 0, 175, 60);

		player4Name.setHorizontalAlignment(SwingConstants.CENTER);
		player4Name.setForeground(Color.blue);
		player4Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player4Name.setEditable(false);
		player4Name.setBackground(Color.LIGHT_GRAY);
		player4Name.setBounds(600, 0, 175, 60);

		player5Name.setHorizontalAlignment(SwingConstants.CENTER);
		player5Name.setForeground(Color.red);
		player5Name.setFont(new Font("Tahoma", Font.PLAIN, 30));
		player5Name.setEditable(false);
		player5Name.setBackground(Color.LIGHT_GRAY);
		player5Name.setBounds(800, 0, 175, 60);

		namePanel.add(player1Name);
		namePanel.add(player2Name);
		namePanel.add(player3Name);
		namePanel.add(player4Name);
		namePanel.add(player5Name);
	}

	/**
	 * Method to update the phase view
	 */
	public void update(Observable o, Object arg) {
		for (Player p : gameState.getAllPlayers().getPlayers()) {
			p.getPlayerTextName().setBackground(Color.LIGHT_GRAY);
		}
		gameState.getCurrPlayer().getPlayerTextName().setBackground(Color.GRAY);
		if (gameState.getFirstRound() == 1) {
			obtainTroopLabel.setVisible(true);
			attackStageLabel.setVisible(false);
			moveStageLabel.setVisible(false);
			arrow0.setVisible(true);
			arrow1.setVisible(false);
			arrow2.setVisible(false);
			obtainTroopLabel.setText("Startup phase");
			win.unitDisplay.setVisible(true);

		} else {
			attackStageLabel.setVisible(true);
			moveStageLabel.setVisible(true);
			obtainTroopLabel.setText("Reinforcement phase");
			attackStageLabel.setText("Attack phase");
			moveStageLabel.setText("Fortification phase");
			if (gameState.getCurrPhase() == 0) {
				arrow0.setVisible(true);
				arrow1.setVisible(false);
				arrow2.setVisible(false);
				win.unitDisplay.setVisible(true);
			} else if (gameState.getCurrPhase() == 1) {
				arrow0.setVisible(false);
				arrow1.setVisible(true);
				arrow2.setVisible(false);
				win.unitDisplay.setVisible(false);
				lp.addLog("It is the Attack phase!");
			} else if (gameState.getCurrPhase() == 2) {
				arrow0.setVisible(false);
				arrow1.setVisible(false);
				arrow2.setVisible(true);
				win.unitDisplay.setVisible(false);
				lp.addLog("It is the Fortification phase!");
			}
		}
	}

}
