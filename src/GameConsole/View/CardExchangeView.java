package GameConsole.View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import GameConsole.Core.GameState;
import GameConsole.Model.Domain.Card;
import GameConsole.Model.Player.Player;

/**
 * This class is the GUI for the Card Exchange View Panel, showing player
 * exchange card for infantry if the player has more than 3 cards
 */
public class CardExchangeView implements Observer {
	private GameState gameState;
	private JFrame frame;

	/**
	 * Constructor with coming parameter
	 * 
	 * @param state
	 *            the game state with GameState type
	 */
	public CardExchangeView(GameState state) {
		this.gameState = state;
		this.frame = new JFrame();
		this.frame.setBounds(300, 500, 900, 100);
		this.frame.setLayout(null);
		this.frame.setResizable(false);
	}

	/**
	 * Method to update the hand cards view
	 */
	public void update(Observable o, Object arg) {

		if (arg != null && (gameState.getFirstRound() > 1 && gameState.getCurrPhase() == 0 && arg.equals("card"))) {
			Player currentPlayer = gameState.getCurrPlayer();
			this.frame.setVisible(true);
			this.frame.setTitle(currentPlayer.getName());
			if (currentPlayer != null) {
				ArrayList<Card> onHand = currentPlayer.getOnHand();
				this.frame.getContentPane().removeAll();
				for (int i = 0; i < onHand.size(); i++) {
					JLabel card = new JLabel();
					switch (onHand.get(i).getType()) {
					case 0:
						card.setIcon(new ImageIcon("resources/CardImages/type1.png"));
						break;
					case 1:
						card.setIcon(new ImageIcon("resources/CardImages/type2.png"));
						break;
					case 2:
						card.setIcon(new ImageIcon("resources/CardImages/type3.png"));
						break;
					}
					card.setBounds(80 * i - 40 + 40, 0, 60, 40);
					this.frame.getContentPane().add(card);
				}
				if (this.frame.getContentPane().getComponentCount() == 0) {
					JLabel card = new JLabel("There's no card!");
					card.setBounds(0, 0, 100, 40);
					this.frame.getContentPane().add(card);
				}

				System.out.println("there's " + this.frame.getContentPane().getComponentCount() + " cards!");
				this.frame.repaint();
			}
		} else
			this.frame.setVisible(false);
	}

}
