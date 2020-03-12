package GameConsole.Strategy;

/**
 * The classes that implement a concrete strategy should implement this. The
 * Player class uses this to use a concrete strategy.
 */
public interface Strategy {

	/**
	 * Method to attack.
	 */
	void attack();

	/**
	 * Method to reinforce.
	 */
	void reinforce();

	/**
	 * Method to fortify
	 */
	void fortify();

	/**
	 * Method to get player's name
	 * 
	 * @return the player's name which is String type
	 */
	String getName();

	/**
	 * Method to set player's name
	 * 
	 * @param name
	 *            the desired name the user wants to set
	 */
	void setName(String name);

}
