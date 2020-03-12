package MapEditor.Model;

import java.util.ArrayList;

/**
 * This class handle territory linked relationship, and manage to set and get
 * territories names.
 */
public class Territory {
	public String name;
	Continent cont;
	private int centerY = -1;
	private int centerX = -1;
	public ArrayList<Territory> links = new ArrayList<>();
	public ArrayList<String> linkNames = new ArrayList<>();
	public boolean hasReached = false;

	/**
	 * constructor method.
	 */
	public Territory() {
	}

	/**
	 * method to add connection between territories.
	 * 
	 * @param t
	 *            input territory that want to create link.
	 */
	public void addLink(Territory t) {
		if (t == this) {
			return;
		}
		if (!this.links.contains(t)) {
			this.links.add(t);
		}
	}

	/**
	 * To get the absolute coordinateX of a territory.
	 * 
	 * @return return X coordinate of this territory
	 */
	public int getCenterX() {
		return (int) this.centerX;
	}

	/**
	 * To get the absolute coordinateY of a territory.
	 * 
	 * @return return Y coordinate of this territory
	 */
	public int getCenterY() {
		return (int) this.centerY;
	}

	/**
	 * To get the continent that a territory is in.
	 * 
	 * @return return the territory's continent
	 */
	public Continent getContinent() {
		return this.cont;
	}

	/**
	 * use list to storage a territory connected territories.
	 * 
	 * @return return the arraylist
	 */
	public ArrayList<Territory> getLinks() {
		return this.links;
	}

	/**
	 * delete a link relationship of a territory from the
	 * 
	 * @param t
	 *            choose one territory that wants to delete the connection.
	 */
	public void removeLink(Territory t) {
		if (this.links.contains(t)) {
			this.links.remove(t);
		}
	}

	/**
	 * create the center where the territory locates, based on the coordinates
	 * (x,y).
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public void setCenter(int x, int y) {
		this.centerX = x;
		this.centerY = y;
	}

	/**
	 * setting the continent where the territory locates
	 * 
	 * @param cont
	 *            a continent that where the territory is in.
	 */
	public void setContinent(Continent cont) {
		this.cont = cont;
	}

	/**
	 * override the toString method.
	 */
	@Override
	public String toString() {
		return "Territory [name=" + name + ", continent=" + cont + ", centerY=" + centerY + ", centerX=" + centerX
				+ ", linkes=" + linkNames + "]";
	}

	/**
	 * To get a territory's name.
	 * 
	 * @return the territory's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * To set the territory's name.
	 * 
	 * @param name
	 *            the name that is wanted to input.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * To get a continent where the territory is in.
	 * 
	 * @return territory's continent.
	 */
	public Continent getCont() {
		return cont;
	}

	/**
	 * setting the continent where the territory locates
	 * 
	 * @param cont
	 *            a continent that where the territory is in.
	 */
	public void setCont(Continent cont) {
		this.cont = cont;
	}

	/**
	 * To get all the connection relationships of a territory.
	 * 
	 * @return all the linked territories.
	 */
	public ArrayList<String> getLinkNames() {
		return linkNames;
	}

	/**
	 * Setting a linked connection of a territory to another one
	 * 
	 * @param linkNames
	 *            input a name of territory that wants to set a linking.
	 */
	public void setLinkNames(ArrayList<String> linkNames) {
		this.linkNames = linkNames;
	}

	/**
	 * Setting the coordinateY of the territory that wants to set a connection
	 * 
	 * @param centerY
	 *            coordinate of Y
	 */
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	/**
	 * Setting the coordinateX of the territory that wants to set a connection
	 * 
	 * @param centerX
	 *            centerY coordinate of X
	 */
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	/**
	 * Creating the links and storage the linked connection in the arraylist.
	 * 
	 * @param links
	 */
	public void setLinks(ArrayList<Territory> links) {
		this.links = links;
	}

}
