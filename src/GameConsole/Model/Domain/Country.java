package GameConsole.Model.Domain;

import java.util.ArrayList;

import GameConsole.Model.Player.Player;
import GameConsole.View.CountryButton;

/**
 * This class is to handle the information of country and manage various
 * behavious associated with the country
 */
public class Country {
	private Continent continent;
	private Player player;
	private int troopNum;
	private String name;
	private int xLoc;
	private int yLoc;
	private ArrayList<Country> borderingCountries = new ArrayList<Country>();
	private ArrayList<String> linkNames = new ArrayList<>();
	private CountryButton button;
	public boolean hasReached;

	/**
	 * Constructor method
	 */
	public Country() {
	}

	/**
	 * Constructor method to initial the attributes
	 * 
	 * @param name
	 *            country name with String type
	 */
	public Country(String name) {
		this.continent = null;
		this.player = null;
		this.name = name;
		this.xLoc = 0;
		this.yLoc = 0;
		this.button = null;
	}

	/**
	 * To set the continent that will contain the country
	 * 
	 * @param c
	 *            the desired continent want to be set with Continent type
	 */
	public void setContinent(Continent c) {
		continent = c;
	}

	/**
	 * Override method to get country information in string layout
	 * 
	 * @return country information with String type
	 */
	@Override
	public String toString() {
		ArrayList<String> nameList = new ArrayList<>();
		if (borderingCountries.size() > 0) {
			for (Country c : borderingCountries) {
				nameList.add(c.getName());
			}
		}
		return "Country= " + this.getName() + " player=" + player.getName();
	}

	/**
	 * To get the continent
	 * 
	 * @return continent with Continent type
	 */
	public Continent getContinent() {
		return continent;
	}

	/**
	 * To set the player to the continent
	 * 
	 * @param p
	 *            the player the want to set to the contient with Player type
	 */
	public void setPlayer(Player p) {
		player = p;
	}

	/**
	 * To get the player
	 * 
	 * @return the player with Player type
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * To add the troop to the continent
	 * 
	 * @param numTroops
	 *            the number of the troops that need to be added with int type
	 */
	public void addInfrantry(int numTroops) {

		this.troopNum += numTroops;
	}

	/**
	 * To remove the troops from the country
	 * 
	 * @param numToRemove
	 *            the number of the troops that want to be removed from the
	 *            continent
	 */
	public void removeTroops(int numToRemove) {

		this.troopNum -= numToRemove;
	}

	/**
	 * To set a name to the country
	 * 
	 * @param name
	 *            the desired name that want to set to the country with String
	 *            type
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * To get the name of the country
	 * 
	 * @return the name of the country with String type
	 */
	public String getName() {
		return name;
	}

	/**
	 * To set the x coordinate postion of the country
	 * 
	 * @param x
	 *            the desired x coordinate postion of the country
	 */
	public void setXLoc(int x) {
		xLoc = x;
	}

	/**
	 * To get the x coordinate location of the country
	 * 
	 * @return the x coordinate location of the country with int type
	 */
	public int getXLoc() {
		return xLoc;
	}

	/**
	 * To set the y coordinate location of the country
	 * 
	 * @param y
	 *            the desired y coordinate location of the country
	 */
	public void setYLoc(int y) {
		yLoc = y;
	}

	/**
	 * To get the y coordinate location of the country
	 * 
	 * @return the y coordinate location of the country with int type
	 */
	public int getYLoc() {
		return yLoc;
	}

	/**
	 * To add the bordering country to the current object
	 * 
	 * @param c1
	 *            the selected bordering country with Country type
	 */
	public void addBorderingCountry(Country c1) {
		this.borderingCountries.add(c1);
		c1.getBorderingCountries().add(this);
	}

	/**
	 * To get the list of the bordering countries
	 * 
	 * @return the list of the bordering countries with ArrayList type
	 */
	public ArrayList<Country> getBorderingCountries() {
		return borderingCountries;
	}

	/**
	 * To set the button to the corresponding country
	 * 
	 * @param b
	 *            the button need to set with CountryButton type
	 */
	public void setButton(CountryButton b) {
		this.button = b;
	}

	/**
	 * To get the button that associated the corresponding country
	 * 
	 * @return the button that associated the corresponding country with
	 *         CountryButton type
	 */
	public CountryButton getButton() {
		return this.button;
	}

	/**
	 * To check the current object is adjacent to the selected country
	 * 
	 * @param c1
	 *            the selected country that need to be checked with Country type
	 * @return true if the selected country is adjacent to the current object
	 *         otherwise return false
	 */
	public boolean checkAdjacent(Country c1) {
		return this.borderingCountries.contains(c1);
	}

	/**
	 * To get the adjacent country list of the current object
	 * 
	 * @return the adjacent country list of the current object with ArrayList
	 *         type
	 */
	public ArrayList<String> getLinkNames() {
		return linkNames;
	}

	/**
	 * To set the adjacent country list to the current object
	 * 
	 * @param linkNames
	 *            the adjacent country list with ArrayList type
	 */
	public void setLinkNames(ArrayList<String> linkNames) {
		this.linkNames = linkNames;
	}

	/**
	 * Method to get the number of troop
	 * 
	 * @return the number of troop
	 */
	public int getTroopNum() {
		return troopNum;
	}

	/**
	 * Method to set the number of the troop
	 * 
	 * @param troopNum
	 *            the desired number that needs to be set
	 */
	public void setTroopNum(int troopNum) {
		this.troopNum = troopNum;
	}

}
