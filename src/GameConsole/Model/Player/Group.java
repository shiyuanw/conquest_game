package GameConsole.Model.Player;

import java.util.ArrayList;
import java.util.List;

import GameConsole.Core.World;

/**
 * This class handles the list of players and manages to perform actions
 * associated with the list of players
 */
public class Group {
	private List<Player> players = new ArrayList<Player>();
	private World world;
	private String groupName;

	/**
	 * Method to get the players list
	 * @return the list of players with Arraylist type
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * To set the players list
	 * @param players the players list need to be set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/**
	 * To add a player to the players list
	 * @param p1 the palyer need to be added with Player type
	 */
	public void addPlayer(Player p1) {
		this.players.add(p1);
	}

	/**
	 * To get the world
	 * @return the world with World type
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * To set the desired world
	 * @param world the world wanted to be set
	 */
	public void setWorld(World world) {
		this.world = world;
	}


	/**
	 * To get the group name
	 * @return the group name with String type
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * To set the group name
	 * @param groupName the group name that want to be set with String type
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
