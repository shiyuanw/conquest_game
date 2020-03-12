package GameConsole.Strategy;

import GameConsole.Core.GameState;
import GameConsole.Model.Player.Player;

/**
 * A concrete Strategy that implements original strategy operation
 */
public class OriginalStrategy implements Strategy {
	private GameState gameState;
	private Player player;
	private String name;

	/**
	 * Method to attack.
	 */
	public void attack() {

	}

	/**
	 * Method to reinforce.
	 */
	public void reinforce() {

	}

	/**
	 * Method to fortify
	 */
	public void fortify() {

	}

	/**
	 * Method to get game state.
	 * 
	 * @return game state which is GameState type
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Method to set game state
	 * 
	 * @param gameState
	 *            the desired set phase of game which is GameState type
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * Method to get player
	 * 
	 * @return the player that got which is Player type
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Method to set player
	 * 
	 * @param player
	 *            the desired set player which is Player type
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Method to get player's name
	 * 
	 * @return the player's name which is String type
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set player's name
	 * 
	 * @param name
	 *            the desired name the user wants to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
