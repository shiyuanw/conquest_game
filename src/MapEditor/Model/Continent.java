package MapEditor.Model;

/**
 * 
 * This class create methods set and get Continents names and bonus and override
 * the continents toString method.
 *
 */
public class Continent {
	String name;
	int bonus;

	/**
	 * constructor method extends super method
	 */
	public Continent() {
	}

	/**
	 * constructor method with incoming parameters.
	 * 
	 * @param name
	 *            continent name with String type
	 * @param bonus
	 *            a continent's bonus after conquest it
	 */
	public Continent(String name, int bonus) {
		this.name = name;
		this.bonus = bonus;
	}

	/**
	 * method to get a continent's bonus.
	 * 
	 * @return continent's bonus
	 */
	public int getBonus() {
		return this.bonus;
	}

	/**
	 * method to get a continent's name
	 * 
	 * @return continent's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * method to create one continent's bonus.
	 * 
	 * @param bonus
	 *            input a bonus that want to set
	 */
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	/**
	 * method to set continent's name
	 * 
	 * @param name
	 *            input a continent name that want to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * method to override the Continent toString() method.
	 */
	public String toString() {
		return this.name;
	}
}
