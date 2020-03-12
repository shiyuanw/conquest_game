package GameConsole.Model.Player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GameConsole.Core.GameState;
import GameConsole.Core.World;
import GameConsole.Model.Domain.Card;
import GameConsole.Model.Domain.Continent;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Domain.CountryDecorator;
import GameConsole.Strategy.OriginalStrategy;
import GameConsole.Strategy.Strategy;
import GameConsole.View.LogPanel;

/**
 * This class represents all of the data and funcionality that a player would
 * have. All of the actions that a player would do eventually comes back to this
 * class.
 */
public class Player extends Observable {
	private String name;
	private Color color;
	private ArrayList<Country> countries = new ArrayList<Country>();
	private ArrayList<CountryDecorator> hand = new ArrayList<CountryDecorator>();
	private ArrayList<Card> onhand = new ArrayList<Card>();
	private GameState game;
	private JFormattedTextField playerTextName;
	private int totalCardsExchange = 0;
	private boolean hasMoved = false;
	private int initTroop;
	private boolean isConquered = false;
	private LogPanel lp = LogPanel.getInstance();
	private Strategy strategy;

	/**
	 * Constructor method
	 * 
	 * @param name
	 *            the player name with String type
	 * @param color
	 *            the color of the player with Color type
	 * @param game
	 *            the game state with GameState type
	 */
	public Player(String name, Color color, GameState game, Strategy strategy) {
		this.name = name;
		this.color = color;
		this.game = game;
		this.strategy = strategy;
		((OriginalStrategy) strategy).setGameState(game);
		((OriginalStrategy) strategy).setPlayer(this);
	}

	/**
	 * Constructor method
	 * 
	 * @param name
	 *            the player name with String type
	 * @param color
	 *            the color of the player with Color type
	 * @param game
	 *            the game state with GameState type
	 */
	public Player(String name, Color color, GameState game) {
		this.name = name;
		this.color = color;
		this.game = game;
	}

	/**
	 * Method to get strategy
	 * 
	 * @return The operator to be applied which is Strategy method
	 */
	public Strategy getStrategy() {
		return strategy;
	}

	/**
	 * Plugs in a specific strategy to be used
	 * 
	 * @param strategy
	 *            The operator to be applied
	 */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * To check whether the player has moved the troop or not
	 * 
	 * @return true if the palyer has moved otherwise return flase
	 */
	public boolean isHasMoved() {
		return hasMoved;
	}

	/**
	 * To set the status that the player has moved the troop or not
	 * 
	 * @param hasMoved
	 *            the status that the player has moved the troop or not with
	 *            boolean type
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	/**
	 * To get the player name
	 * 
	 * @return the player name with String type
	 */
	public String getName() {
		return name;
	}

	/**
	 * To get the init troop nums
	 * 
	 * @return the init troop nums
	 */
	public int getInitTroop() {
		return initTroop;
	}

	/**
	 * To set the init troop nums
	 * 
	 * @param initTroop
	 *            the init troop nums
	 */
	public void setInitTroop(int initTroop) {
		this.initTroop = initTroop;
	}

	/**
	 * To set the player name
	 * 
	 * @param name
	 *            the desired the player name that want to be set with String
	 *            type
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * To get the player's color
	 * 
	 * @return the player color with Color type
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * To set the player's color
	 * 
	 * @param color
	 *            the desired the player color that want to be set with Color
	 *            type
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * To get the player's countries list
	 * 
	 * @return the player's countries list with ArrayList type
	 */
	public ArrayList<Country> getCountries() {
		return countries;
	}

	/**
	 * To set the countries list to belong to the player
	 * 
	 * @param countries
	 *            the countries list desired to be set to belong to the player
	 *            with ArrayList type
	 */
	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}

	/**
	 * To get the player's hand cards list
	 * 
	 * @return the player's hand cards list with ArrayList type
	 */
	public ArrayList<CountryDecorator> getHand() {
		return hand;
	}

	/**
	 * To add an infantry for target country of current player
	 * 
	 * @param c
	 *            the target country
	 */
	public void addInfantry(Country c) {
		if (countries.contains(c)) {
			c.addInfrantry(1);
		}
		setChanged();
		notifyObservers();
		lp.addLog(this.getName() + " adds one troop to " + c.getName() + "!");
	}

	/**
	 * To get the player's hand cards list
	 * 
	 * @return the player's hand cards list with ArrayList type
	 */
	public ArrayList<Card> getOnHand() {
		return onhand;
	}

	/**
	 * Add a certain type of card to player
	 * 
	 * @param cards
	 */
	public void addCard(Card cards) {
		this.onhand.add(cards);
	}

	/**
	 * Add all cards of the given player to receive player
	 * 
	 * @param onhand
	 */
	public void addAllCard(ArrayList<Card> onhand) {
		this.onhand.addAll(onhand);
	}

	/**
	 * Indicate whether player conquered at least one country
	 * 
	 * @param _isCQ
	 *            true if conquered one or more countries
	 */
	public void isConquered(boolean _isCQ) {
		this.isConquered = _isCQ;
	}

	public boolean isConquered() {
		return isConquered;
	}

	/**
	 * To get the game state
	 * 
	 * @return the game state with GameState type
	 */
	public GameState getGame() {
		return game;
	}

	/**
	 * To set the game state
	 * 
	 * @param g
	 *            the desired game state wanted to be set with Gamestate type
	 */
	public void setgame(GameState g) {
		this.game = g;
	}

	/**
	 * To check if the country is belong to the player
	 * 
	 * @param countryName
	 *            the country name with String type
	 * @return country if the player owns the country otherwise return null
	 */
	public Country checkIfOwned(String countryName) {
		for (Country c : this.countries) {
			if (c.getName().equals(countryName)) {
				return c;
			}
		}

		return null;
	}

	/**
	 * This class will take in a country as well as the country it is going to
	 * attack. It will return the object of the country it wants to attack if
	 * the player is allowed to attack that country
	 * 
	 */
	public boolean checkIfCanAttack() {
		ArrayList<Country> canAttackCountrys = new ArrayList<>();
		boolean flag = true;
		for (Country c : this.countries) {
			if (c.getTroopNum() > 1) {
				flag = false;
				canAttackCountrys.add(c);
			}
		}
		if (flag) {
			lp.addLog("There's no country can attack, automatically end Attack phase!");
			return false;
		}

		for (Country c : canAttackCountrys) {
			for (Country neighCountry : c.getBorderingCountries()) {
				if (neighCountry.getPlayer() != this) {
					return true;
				}
			}

		}
		lp.addLog("There's no country can attack, automatically end Attack phase!");
		return false;
	}

	/**
	 * To check the selected country is able to move the troop to another
	 * 
	 * @param origin
	 *            the selected country with Country type
	 * @param countryName
	 *            the country name with String type
	 * @return country with Country type if the selected country is able move
	 *         troop otherwise return null
	 */
	public Country checkIfCanMove(Country origin, String countryName) {
		if ((this.checkIfOwned(countryName) == null) || (origin.getTroopNum() == 1)) {
			return null;
		}

		for (Country c1 : origin.getBorderingCountries()) {
			if (c1.getName().equals(countryName)) {
				return c1;
			}
		}

		return null;
	}

	/**
	 * To perform attack action
	 * 
	 * @param c1
	 *            the selected country to perform attack action with Country
	 *            type
	 * @param c2
	 *            the selected target to be attacked with Country type
	 */
	public Map<String, Object> originalAttack(Country c1, Country c2, int decision1, int decision2) throws Exception {
		if (c1.getPlayer() != this || c2.getPlayer() == this) {
			throw new Exception("The attacker and defender is not right!");
		}

		Map<String, Object> result = new HashMap<>();
		lp.addLog(c1.getName() + " is attacking " + c2.getName() + "!");
		Random rand = new Random();
		ArrayList<Integer> attackRoll = new ArrayList<Integer>();
		ArrayList<Integer> defendRoll = new ArrayList<Integer>();
		for (int i = 0; i < decision1; i++) {
			Integer tempInt = new Integer(rand.nextInt(6) + 1);
			attackRoll.add(tempInt);
		}
		for (int i = 0; i < decision2; i++) {
			Integer tempInt = new Integer(rand.nextInt(6) + 1);
			defendRoll.add(tempInt);
		}
		String diceString = "Attacker rolled:\n";
		for (int i = 0; i < attackRoll.size(); i++) {
			if (i != decision1 - 1) {
				diceString += attackRoll.get(i) + ", ";
			} else {
				diceString += attackRoll.get(i);
			}
		}
		diceString += "\nDefender rolled:\n";
		for (int i = 0; i < defendRoll.size(); i++) {
			if (i != decision2 - 1) {
				diceString += defendRoll.get(i) + ", ";
			} else {
				diceString += defendRoll.get(i);
			}
		}
		lp.addLog(diceString);
		diceString += "\n";

		while (!defendRoll.isEmpty() && !attackRoll.isEmpty()) {
			int attackMax = 0;
			int attackIndex = 0;
			for (int i = 0; i < attackRoll.size(); i++) {
				if (attackRoll.get(i) > attackMax) {
					attackMax = attackRoll.get(i);
					attackIndex = i;
				}
			}
			attackRoll.remove(attackIndex);

			int defendMax = 0;
			int defendIndex = 0;
			for (int i = 0; i < defendRoll.size(); i++) {
				if (defendRoll.get(i) > defendMax) {
					defendMax = defendRoll.get(i);
					defendIndex = i;
				}
			}
			defendRoll.remove(defendIndex);

			if (attackMax > defendMax) {

				c2.removeTroops(1);
				lp.addLog("Attacker won!" + "\n");
				diceString += "Attacker won!" + "\n";
			} else {
				c1.removeTroops(1);
				lp.addLog("Defender won!" + "\n");
				diceString += "Defender won!" + "\n";
			}
		}

		result.put("dice", diceString);
		setChanged();
		notifyObservers();

		if (c2.getTroopNum() == 0) {
			c2.getPlayer().removeCountry(c2);
			c2.setPlayer(this);
			this.addCountry(c2);
			this.isConquered(true);
			result.put("result", true);
		} else {
			result.put("result", false);
		}
		return result;
	}

	/**
	 * Method to reinforce by implementing the selected strategy
	 */
	public void reinforce() {
		this.strategy.reinforce();
	}

	/**
	 * Method to fortify by implementing the selected strategy
	 */
	public void fortify() {
		this.strategy.fortify();
	}

	/**
	 * Method to attack by implementing the selected strategy
	 */
	public void attack() {
		this.strategy.attack();
	}

	/**
	 * To perform move troop action
	 * 
	 * @param c1
	 *            the selected country to perform move troops action with
	 *            Country type
	 * @param c2
	 *            the selected target country to receive the moved troop with
	 *            Country type
	 * @param toMove
	 *            the number of the troops that need to be moved
	 */
	public void moveTroops(Country c1, Country c2, int toMove) {
		c1.removeTroops(toMove);
		c2.addInfrantry(toMove);
		setChanged();
		notifyObservers();
		lp.addLog(this.getName() + " moves " + toMove + " troops from " + c1.getName() + " to " + c2.getName());
	}

	/**
	 * Method to check if the player wins the game
	 * 
	 * @return true if the player has the total number of the countries in the
	 *         map otherwise false
	 */
	public boolean checkWinGame() {
		if (this.countries.size() == game.getWorld().getDeck().size())
			return true;
		return false;
	}

	/**
	 * To give the player the hand cards
	 */
	public void giveCards() {
		if (this.isConquered == true) {
			Card c = new Card();
			c.addRandomTypeCard();
			this.onhand.add(c);
			lp.addLog(this.name + " gets a card!");
			System.out.println(c);
			this.isConquered = false;
		}
	}

	/**
	 * To get the initial army bonus in the setup phase
	 * 
	 * @return the number of the got armies
	 */
	public int getBonus() {
		setChanged();
		notifyObservers("card");
		int reward = 0;
		int firstRound = game.getFirstRound();
		if (firstRound == 1) {
			if (game.getAllPlayers().getPlayers().size() == 2) {
				reward = 40 - this.getCountries().size();
			} else if (game.getAllPlayers().getPlayers().size() == 3) {
				reward = 35 - this.getCountries().size();
			} else if (game.getAllPlayers().getPlayers().size() == 4) {
				reward = 30 - this.getCountries().size();
			} else if (game.getAllPlayers().getPlayers().size() == 5) {
				reward = 25 - this.getCountries().size();
			} else {
				reward = 20 - this.getCountries().size();
			}

		} else {

			reward = this.getCountries().size() / 3;
			if (reward < 3)
				reward = 3;
			boolean isLoop = true;

			while (this.onhand.size() >= 3 && isLoop) {
				int cardType0 = 0;
				int cardType1 = 0;
				int cardType2 = 0;

				for (Card c : this.onhand) {
					if (c.getType() == 0) {
						cardType0 = cardType0 + 1;
					}
					if (c.getType() == 1) {
						cardType1 = cardType1 + 1;
					}
					if (c.getType() == 2) {
						cardType2 = cardType2 + 1;
					}
				}

				JPanel numPanel = new JPanel();
				numPanel.add(new JLabel("Chose Cards!"));
				DefaultComboBoxModel<String> selection = new DefaultComboBoxModel<String>();
				if (cardType0 > 2) {
					selection.addElement("Change 3*Type0");
				}
				if (cardType1 > 2) {
					selection.addElement("Change 3*Type1");
				}
				if (cardType2 > 2) {
					selection.addElement("Change 3*Type2");
				}
				if (cardType0 > 0 && cardType1 > 0 && cardType2 > 0) {
					selection.addElement("Change 1*Type0&Type1&Type2");
				}

				JComboBox<String> comboBox = new JComboBox<String>(selection);
				numPanel.add(comboBox);

				if (selection.getSize() > 0) {
					int result = JOptionPane.showConfirmDialog(null, numPanel, "Use Cards to Exchange Troops",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == 1) {
						if (this.onhand.size() < 5)
							isLoop = false;
					} else {

						String selected = comboBox.getSelectedItem().toString();

						totalCardsExchange = totalCardsExchange + 1;
						reward = reward + (totalCardsExchange * 5);
						lp.addLog(this.getName() + " changes cards for " + (totalCardsExchange * 5) + " troops!");

						switch (selected) {
						case ("Change 3*Type0"): {
							int i = 0;
							Iterator<Card> it = onhand.iterator();
							while (it.hasNext()) {
								Card c = it.next();
								if (c.getType() == 0 && i < 3) {
									it.remove();
									i++;
								}
							}
							break;
						}
						case ("Change 3*Type1"): {
							int i = 0;
							Iterator<Card> it = onhand.iterator();
							while (it.hasNext()) {
								Card c = it.next();
								if (c.getType() == 1 && i < 3) {
									it.remove();
									i++;
								}
							}
						}
						case ("Change 3*Type2"): {
							int i = 0;
							Iterator<Card> it = onhand.iterator();
							while (it.hasNext()) {
								Card c = it.next();
								if (c.getType() == 2 && i < 3) {
									it.remove();
									i++;
								}
							}
							break;
						}
						case ("Change 1*Type0&Type1&Type2"): {
							int i = 0;
							int j = 0;
							int[] deleted = { 9, 9, 9 };
							Iterator<Card> it = onhand.iterator();
							while (it.hasNext()) {
								Card c = it.next();
								if ((c.getType() != deleted[0] && c.getType() != deleted[1]) && i != 3) {
									i++;
									deleted[j] = c.getType();
									j++;
									it.remove();
								}
							}
							break;
						}
						}
						System.out.println("after change===========");
						for (Card c : onhand) {
							System.out.println(c);
						}
						System.out.println("after change===========");
						setChanged();
						notifyObservers("card");
					}
				} else
					break;
			}
			boolean owned = true;

			World world = game.getWorld();
			System.out.println(world.getContinents().size());

			if (world.getContinents().size() > 0) {
				for (Continent con : world.getContinents()) {
					owned = true;
					for (Country cou : con.getCountries()) {
						System.out.println(cou.getName());
						System.out.println(cou.getPlayer());
						if (cou.getPlayer() == null || !(cou.getPlayer().equals(this))) {
							owned = false;
							break;
						}
					}
					System.out.println(con.getName());
					if (owned) {
						reward += con.getBonus();
					}
				}
			}
			System.out.println("reward " + reward);
			System.out.println("size " + this.countries.size());
			lp.addLog(this.getName() + " gets total of " + reward + " troops!");

		}

		this.initTroop = reward;
		return reward;

	}

	/**
	 * To get the initial army bonus in the setup phase
	 * 
	 * @return the number of the got armies
	 */
	public int getBonusAndChangeCard() {

		int firstRound = game.getFirstRound();
		if (firstRound == 1) {
			if (game.getAllPlayers().getPlayers().size() == 2) {
				return 40 - this.getCountries().size();
			} else if (game.getAllPlayers().getPlayers().size() == 3) {
				return 35 - this.getCountries().size();
			} else if (game.getAllPlayers().getPlayers().size() == 4) {
				return 30 - this.getCountries().size();
			} else if (game.getAllPlayers().getPlayers().size() == 5) {
				return 25 - this.getCountries().size();
			} else {
				return 20 - this.getCountries().size();
			}

		} else {

			int reward = this.getCountries().size() / 3;
			if (reward < 3)
				reward = 3;

			while (this.onhand.size() >= 5) {
				ArrayList<Card> cardType0 = new ArrayList<>();
				ArrayList<Card> cardType1 = new ArrayList<>();
				ArrayList<Card> cardType2 = new ArrayList<>();

				for (Card c : this.onhand) {
					if (c.getType() == 0) {
						cardType0.add(c);
					}
					if (c.getType() == 1) {
						cardType1.add(c);
					}
					if (c.getType() == 2) {
						cardType2.add(c);
					}
				}

				if (cardType0.size() >= 3) {
					ArrayList<Card> temp = new ArrayList<>();
					for (int i = 0; i < 3; i++) {
						Card t = cardType0.remove(0);
						temp.add(t);
					}
					this.onhand.removeAll(temp);
					totalCardsExchange++;
					reward = reward + (totalCardsExchange * 5);
				}
				if (cardType1.size() >= 3) {
					ArrayList<Card> temp = new ArrayList<>();
					for (int i = 0; i < 3; i++) {
						Card t = cardType1.remove(0);
						temp.add(t);
					}
					this.onhand.removeAll(temp);
					totalCardsExchange++;
					reward = reward + (totalCardsExchange * 5);
				}
				if (cardType2.size() >= 3) {
					ArrayList<Card> temp = new ArrayList<>();
					for (int i = 0; i < 3; i++) {
						Card t = cardType2.remove(0);
						temp.add(t);
					}
					this.onhand.removeAll(temp);
					totalCardsExchange++;
					reward = reward + (totalCardsExchange * 5);
				}
				if (cardType0.size() >= 1 && cardType1.size() >= 1 && cardType2.size() >= 1) {
					ArrayList<Card> temp = new ArrayList<>();
					Card t = cardType0.remove(0);
					temp.add(t);
					t = cardType1.remove(0);
					temp.add(t);
					t = cardType2.remove(0);
					temp.add(t);
					this.onhand.removeAll(temp);
					totalCardsExchange++;
					reward = reward + (totalCardsExchange * 5);
				}

			}

			boolean owned;
			World world = game.getWorld();
			if (world.getContinents().size() > 0) {
				for (Continent con : world.getContinents()) {
					owned = true;
					for (Country cou : con.getCountries()) {
						if (cou.getPlayer() == null || !(cou.getPlayer().equals(this))) {
							owned = false;
							break;
						}
					}
					if (owned) {
						reward += con.getBonus();
					}
				}
			}

			return reward;
		}
	}

	/**
	 * To add a country to the player's countries list
	 * 
	 * @param c
	 *            a country that need to be added with Country type
	 */
	public void addCountry(Country c) {
		this.countries.add(c);
		setChanged();
		notifyObservers();
	}

	/**
	 * To remove a country from the player's countries list
	 * 
	 * @param c
	 *            a country that need to be removed with Country type
	 */
	public void removeCountry(Country c) {
		this.countries.remove(c);
	}

	/**
	 * To get the playerName in the text
	 * 
	 * @return playertextname with JFormattedTextField type
	 */
	public JFormattedTextField getPlayerTextName() {
		return playerTextName;
	}

	/**
	 * To set the playerTextName
	 * 
	 * @param playerTextName
	 *            the playerTextName need to be set
	 */
	public void setPlayerTextName(JFormattedTextField playerTextName) {
		this.playerTextName = playerTextName;
	}

	/**
	 * Method to specify the state changes
	 */
	public void changed() {
		setChanged();
		notifyObservers("card");
		notifyObservers();
	}
}
