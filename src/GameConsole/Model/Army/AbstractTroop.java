package GameConsole.Model.Army;

import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;

/**
 * 
 * This class is the abstract class of troop, which defines some members and functions.
 *
 */
public abstract class AbstractTroop {
	protected Player player;
	protected String color;
	protected Country country;
}
