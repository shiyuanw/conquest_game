package GameConsole.Model.Domain;

import GameConsole.Model.Player.Player;

/**
 * This class handles the troop associated with its number, the player and the corresponding country
 */
public class CountryDecorator {
	private Country country;
	private int numStars;
	private Player player;

	/**
	 * constructor method.
	 * @param country the country with Country type
	 * @param numStars the number the troop with int type
	 */
	public CountryDecorator(Country country, int numStars){
		this.country = country;
		this.numStars = numStars;
		player = null;
	}

	/**
	 * Method to set a country
	 * @param c input the country that want to be set with Country type
	 */
	public void setCountry(Country c){
		country = c;
	}

	/**
	 * Method to get the country
	 * @return the country that associated with the troop with Country type
	 */
	public Country getCountry(){
		return country;
	}

	/**
	 * Method to set the number of the troop
	 * @param stars the desired number of the troop that need to be setted with int type
	 */
	public void setNumStars(int stars){
		numStars = stars;
	}

	/**
	 * Method to get the number of the troop
	 * @return the number of the troop with int type
	 */
	public int getNumStars(){
		return numStars;
	}

	/**
	 * Method to set the player to the associated troop
	 * @param p the player that need to be set with Player type
	 */
	public void setPlayer(Player p){
		player = p;
	}

	/**
	 * Method to get the player
	 * @return the player associated with the corresponding troop with Player type
	 */
	public Player getPlayer(){
		return player;
	}
}
