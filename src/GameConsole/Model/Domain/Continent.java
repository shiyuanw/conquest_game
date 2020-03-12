package GameConsole.Model.Domain;


import java.util.ArrayList;

import GameConsole.Core.World;

/**
 *
 * This class create methods to add/remove countries to Continents, get/set Continents bonus and so on
 * the toString method lists the countries on the continent.
 *
 */
public class Continent {
	private ArrayList<Country> countries = new ArrayList<Country>();
	private int bonus;
	private String color;
	private String name;
	private World world;


	/**
	 * Construction method with incoming parameters.
	 * @param color the continent color with Color type
	 * @param name  continent name with String type
	 * @param bonus a bonus the player can get after conquest the continent
	 * @param world the world the players are in
	 */
	public Continent(String color, String name, int bonus, World world){
		this.bonus = bonus;
		this.color = color;
		this.name = name;
		this.world = world;
	}

	/**
	 * constructor method with incoming parameters.
	 *
	 * @param bonus a continent's bonus after conquest it
	 * @param name continent name with String type
	 *
	 */
	public Continent(int bonus, String name) {
		super();
		this.bonus = bonus;
		this.name = name;
	}


	/**
	 * To get the country list in String layout
	 * @return the list of countries' name with String type
	 */
	public String toString() {
		String retString = "";
		for(Country c: this.countries) {
			retString += c.getName() + " ";
		}
		return retString;
	}

	/**
	 * add a country to Continent
	 * @param c the country was added
	 */
	public void addCountry(Country c){
		c.setContinent(this);
		countries.add(c);
	}

	/**
	 * remove a country from Continent
	 * @param c the country was removed
	 */
	public void removeCountry(Country c){
		for(Country A : countries){
			if(A.equals(c)){
				countries.remove(A);
				break;
			}
		}
	}

	/**
	 * To get the country list
	 * @return the list of countried with ArrayList type
	 */
	public ArrayList<Country> getCountries(){
		return countries;
	}

	/**
	 * To set the number of the troops
	 * @param bonus the number of the troops
	 */
	public void setBonus(int bonus){
		this.bonus = bonus;
	}

	/**
	 * To get the number of the troop
	 * @return the number of the troop with int type
	 */
	public int getBonus(){
		return bonus;
	}

	/**
	 * To set the color to the continent
	 * @param col the color that want to be set to the continent with String type
	 */
	public void setColor(String col){
		color = col;
	}

	/**
	 * To get the color of the continent
	 * @return the color of the continent with String type
	 */
	public String getColor(){
		return color;
	}

	/**
	 * To set a name to  the continent
	 * @param name the desired name that user wants to set to the continent with String type
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * To get the name of the continent
	 * @return the name of the continent with String type
	 */
	public String getName(){
		return name;
	}

	/**
	 * To set the world where the continent will be contained
	 * @param w the desired world that will contain the continent
	 */
	public void setWorld(World w){
		world = w;
	}

	/**
	 * To get the world where contain the continent
	 * @return the world that contains the continent with World type
	 */
	public World getWorld(){
		return world;
	}
}
