package GameConsole.Model.Army;

/**
 * This class handles the infantry and manage the corresponding operations of
 * the class
 */
public class Troop extends AbstractTroop {
	private int strength;

	/**
	 * Constructor method
	 */
	public Troop() {
		this.strength = 1;
	}

	/**
	 * To get the number of the infantries
	 * 
	 * @return the number of the infantries with int type
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * To set the number of the infantries
	 * 
	 * @param strength
	 *            the number of the infantries that need to be set with int type
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/**
	 * Method extends from the super class and provide implementations for die()
	 * method. The specific implementation will be decided when the future
	 * requirements need.
	 */
	public void die() {

	}
}
