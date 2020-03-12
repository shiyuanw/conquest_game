package GameConsole.View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GameConsole.Core.GameState;
import GameConsole.Model.Player.Player;

/**
 * 
 * this class is responsible for displaying conquestRatio of a player. Based on
 * one player own countries/total countries.
 *
 */
public class ConquestRatio extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private GameState state;
	private int playerNum;
	private ArrayList<JLabel> lable = new ArrayList<>();
	public static final int ratio = 15;

	private List<Player> players = new ArrayList<>();

	/**
	 * constructor method
	 * 
	 * @param state
	 *            game state with GameState type
	 */
	public ConquestRatio(GameState state) {
		this.state = state;
		playerNum = state.getAllPlayers().getPlayers().size();
		players.addAll(state.getAllPlayers().getPlayers());
		this.setLayout(null);
		// this.setBounds(0, 0, state.getWorld().getDeck().size() * ratio, 25);
		int loc = 0;
		for (int i = 0; i < playerNum; i++) {
			Player cur = state.getAllPlayers().getPlayers().get(i);
			int oldLoc = loc;
			loc += ratio * cur.getCountries().size();
			JLabel lb1 = new JLabel(cur.getCountries().size() + "");
			lb1.setBounds((loc + oldLoc) / 2, 0, 25, 20);
			lb1.setBackground(cur.getColor());
			lb1.setOpaque(true);
			this.add(lb1);
			lable.add(lb1);

		}

	}

	/**
	 * this method is using to override paintComponent method and draw
	 * rectangles to show the ratio.
	 */
	public void paintComponent(Graphics g) {
		Color color = g.getColor();
		int loc = 0;
		if (playerNum > 1) {
			for (int i = 0; i < playerNum; i++) {
				if (players.get(i).getCountries().size() > 0) {
					Player cur = players.get(i);
					g.setColor(cur.getColor());
					g.fillRect(loc, 0, ratio * cur.getCountries().size(), 20);
					loc += ratio * cur.getCountries().size();
				}
			}
		}
		g.setColor(color);
	}

	/**
	 * The override update function for Observer
	 */
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		int loc = 0;
		for (int i = 0; i < playerNum; i++) {
			if (players.get(i).getCountries().size() > 0) {
				Player cur = players.get(i);
				int oldLoc = loc;
				loc += ratio * cur.getCountries().size();
				JLabel lb1 = lable.get(i);
				lb1.setText("" + cur.getCountries().size());
				lb1.setBounds((loc + oldLoc) / 2, 0, 25, 20);
				this.add(lb1);
			}
		}
		this.repaint();
	}

}
