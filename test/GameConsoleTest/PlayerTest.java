package GameConsoleTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameState;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Group;
import GameConsole.Model.Player.Player;
import GameConsole.Strategy.HumanStrategy;

/**
 * this class is a test class for testing if player owns the corresponding
 * counties and can correctly get bonus from initial phase.
 */
public class PlayerTest {
	private Player player;
	private Player player2;
	private Player player3;
	private GameState state;
	private Group group;

	/**
	 * setUp initial game, loading a new map and adding new players.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		group = new Group();

		state = new GameState(null, "resources/ConquestMaps/Atlantis.map");
		state.setAllPlayers(group);


		player = new Player("testPlayer", null, state, new HumanStrategy());
		ArrayList<Country> temp = new ArrayList<Country>();
		for (int i = 0; i < 14; ++i) {
			temp.add(new Country());
		}
		player.setCountries(temp);
		player.setInitTroop(temp.size());
		

		player2 = new Player("testPlayerDefender", null, state, new HumanStrategy());
		ArrayList<Country> temp2 = new ArrayList<Country>();
		for (int i = 0; i < 2; ++i) {
			temp2.add(new Country());
		}
		player2.setCountries(temp2);
		player2.setInitTroop(temp2.size());
	
		player3 = new Player("testPlayer3", null, state, new HumanStrategy());
		ArrayList<Country> temp3 = new ArrayList<Country>();
		for (int i = 0; i < 2; ++i) {
			temp3.add(new Country());
		}
		player3.setCountries(temp3);
		player3.setInitTroop(temp3.size());
		
		group.addPlayer(player);
		group.addPlayer(player2);
		group.addPlayer(player3);
	}

	/**
	 * test class: Player, function: getBonus(). check the new game have the
	 * correctly corresponding players. check if player can get the bonus in the
	 * initial phase.
	 */
	@Test
	public void testGetBonus() {
		System.out.println("gamestate:" + state.getAllPlayers().getPlayers().size());
		System.out.println(player.getCountries().size());
		System.out.println(player.getBonus());
		assertEquals(21, player.getBonus());
	}
	
	/**
	 * test class: Player, function: moveTroops(). check the valid move after player
	 * conquered a country
	 * 
	 */
	@Test
	public void testMoveTroops() {
		Country c1 = player.getCountries().get(0);
		Country c2 = player.getCountries().get(1);
		c1.addInfrantry(5);
		c2.addInfrantry(4);
		player.moveTroops(c1, c2, 2);
		assertEquals(3, c1.getTroopNum());
		assertEquals(6, c2.getTroopNum());
	}
	
	
	
	/**
	 * test class: Player. check the end of the game
	 * when a player became the only player
	 * 
	 */
	@Test
	public void testWinGame() {
		for (int i=1; i<group.getPlayers().size();i++){
			group.getPlayers().remove(i);
		}
		state.setCurrPlayer(group.getPlayers().get(0));
		assertEquals(state.checkWinner(), state.getCurrPlayer());
	}
	
	/**
	 * test class: Player, function attack(). check if the attacker and
	 * defender are valid.
	 */
	@Test
	public void testAttack() {
		Country c1 = player.getCountries().get(0);
		c1.setPlayer(player);
		Country c2 = player2.getCountries().get(0);
		c2.setPlayer(player2);
		Country c3 = player.getCountries().get(1);
		c3.setPlayer(player);
		
		
		try {
			player.originalAttack(c1, c2, 1, 1);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			assertNotEquals(ex.getMessage(), "The attacker and defender is not right!");
		}
		
		try {
			player.originalAttack(c1, c3, 1, 1);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			assertEquals(ex.getMessage(), "The attacker and defender is not right!");
		}
	}
	
	/**
	 * test class: Player. check the fortification
	 * phase
	 * 
	 */
	@Test
	public void testHumanFortify() {
		state.setCurrPlayer(group.getPlayers().get(0));
		Player curPlayer = state.getCurrPlayer();
		curPlayer.getCountries().get(0).setTroopNum(5);
		curPlayer.getCountries().get(1).setTroopNum(8);

		curPlayer.moveTroops(curPlayer.getCountries().get(0), curPlayer.getCountries().get(1), 2);
		
		assertEquals(3, curPlayer.getCountries().get(0).getTroopNum());
		assertEquals(10, curPlayer.getCountries().get(1).getTroopNum());
	}
	
	/**
	 * test class: Player. check the startup
	 * phase
	 * 
	 */
	@Test
	public void testStartupPhase() {
		state.setCurrPlayer(group.getPlayers().get(0));
		Player curPlayer = state.getCurrPlayer();
		state.setFirstRound(1); //startup phase
		
		int beforeInitTroops = curPlayer.getInitTroop();
		state.setCurrClick(curPlayer.getCountries().get(0));
		curPlayer.reinforce();
		int afterInitTroops = curPlayer.getInitTroop();
		
		assertEquals(beforeInitTroops -1, afterInitTroops);
	}
	
	

}
