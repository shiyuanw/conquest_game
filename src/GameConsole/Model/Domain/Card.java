package GameConsole.Model.Domain;

import GameConsole.Model.Player.Player;

/**
 * This class defines the Cards that player can exchange to troops
 *
 */
public class Card {

	private int type;


	/**
	 * Constructor for Card class
	 */
	public Card(){}

	/**
	 * Constructor for Card class with coming parameter
	 * @param type the type of the card with int type
	 */
	public Card(int type){
		this.type = type;
	}
	
	/**
	 * The override toString function
	 */
	public String toString() {
		return "This is a " + type + " card";

	}

	/**
	 * If player conquered at least one territory during the attack phase, he
	 * will be given a card
	 * 
	 */
	public void addRandomTypeCard() {
		this.type = (int) (Math.random() * 3);
	}

	/**
	 * Gives all of the killed Player's cards to their killer
	 * 
	 * @param giver
	 *            Player who was killed
	 * @param receiver
	 *            Player who killed giver
	 */
	public void handOverCards(Player giver, Player receiver) {
		receiver.addAllCard(giver.getOnHand());
	}

	/**
	 * To get the type of the hand card
	 * 
	 * @return the type of the hand card with int type
	 */
	public int getType() {
		return type;
	}

}
