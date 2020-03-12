package GameConsole.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import GameConsole.Model.Army.Troop;
import GameConsole.Model.Domain.Continent;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Domain.CountryDecorator;
import GameConsole.Model.Player.Group;
import GameConsole.Model.Player.Player;

/**
 * This class handles with world the player are in and mange the corresponding behaviors
 */
public class World {
	private ArrayList<Continent> continents = new ArrayList<Continent>();
	private ArrayList<CountryDecorator> deck = new ArrayList<>();
	private MapLoader mapLoader;

	/**
	 * Construction method
	 * @param path a passing parameter with String type
	 * @throws Exception
	 */
	public World(String path) throws Exception {
		mapLoader = new MapLoader();
		mapLoader.load(path);
		System.out.println("map load");
		mapLoader.setWorld(this);

		if (mapLoader.isWarn() && !mapLoader.validityCheck()) {
			throw new RuntimeException("the map is not valid!");
		}

		for (Continent continent : this.continents) {
			System.out.println("continents:" + continent.getName());
			for (Country country : continent.getCountries()) {
				System.out.println("country:" + country.getName());
			}
		}
	}

	/**
	 * The override toString function
	 */
	public String toString() {
		String retString = "";
		for (Continent c : this.continents) {
			retString += c.toString();
		}
		return retString;
	}

	/**
	 * To add a continent to the continent list
	 * @param c the continent that will be added to the continent list with Continent type
	 */
	public void addContinent(Continent c) {
		continents.add(c);
	}

	/**
	 * To remove a continent to the continent list
	 * @param c the selected continent that will be removed from the continent list with Continent type
	 */
	public void removeContinent(Continent c) {
		for (Continent A : continents) {
			if (A.equals(c)) {
				continents.remove(A);
				break;
			}
		}
	}

	/**
	 * To get the continent list
	 * @return the continent list with ArrayList type
	 */
	public ArrayList<Continent> getContinents() {
		return continents;
	}


	/**
	 * To add the card to the deck
	 * @param c the card will be added to the deck with Card type
	 */
	public void addToDeck(CountryDecorator c) {
		deck.add(c);
	}

	/**
	 * To remove the card from the deck
	 * @param c the selected card will be remove from the deck with Card type
	 */
	public void removeFromDeck(CountryDecorator c) {
		for (CountryDecorator A : deck) {
			if (A.equals(c)) {
				deck.remove(A);
				break;
			}
		}
	}

	/**
	 * To get deck
	 * @return the deck with ArrayList type
	 */
	public ArrayList<CountryDecorator> getDeck() {
		return deck;
	}

	/**
	 * Method to start the game
	 * @param players the group of players who will participate in the game with Group type
	 */
	public void startGame(Group players) {
		/*
		 * Creating a card for every country, giving it a country and a star
		 * amount from 1 - 3
		 */
		for (Continent con : this.continents) {
			for (Country cou : con.getCountries()) {
				CountryDecorator tempCard = new CountryDecorator(cou, new Troop().getStrength());
				this.deck.add(tempCard);
			}
		}

		/*
		 * Shuffling the deck and then giving each player a card unit there are
		 * no cards left.
		 */
		this.shuffleDeck();
		for (int i = 0; i < this.deck.size(); i++) {
			players.getPlayers().get(i % players.getPlayers().size()).getHand().add(this.deck.get(i));
		}

		/*
		 * Iterating through the player's hands and assigning them the country
		 * Still need to add giving the troops equal to the number of stars to
		 * that country.
		 */
		for (Player p : players.getPlayers()) {
			for (CountryDecorator c : p.getHand()) {
				c.getCountry().setPlayer(p);
				c.getCountry().addInfrantry(c.getNumStars());
				p.addCountry(c.getCountry());
			}
			p.getHand().clear(); // putting it back in the deck
		}
	}


	/**
	 * To check if the player owns the world
	 * @param p the selected player who will be checked if he owns the world with Playe type
	 * @return true if the player owns the world otherwise return false
	 */
	public boolean checkIfWorldOwned(Player p) {
		for (Continent con : this.continents) {
			for (Country cou : con.getCountries()) {
				if (cou.getPlayer() == p) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * To shuffle the deck
	 */
	public void shuffleDeck() {
		long seed = System.nanoTime(); // shuffling
		Collections.shuffle(this.deck, new Random(seed));
	}

	/**
	 * To get map loader
	 * @return map loader with Maploader type
	 */
	public MapLoader getMapLoader() {
		return mapLoader;
	}

	/**
	 * To set mapLoader
	 * @param mapLoader the selected maploader will be set with MapLoader type
	 */
	public void setMapLoader(MapLoader mapLoader) {
		this.mapLoader = mapLoader;
	}

}
