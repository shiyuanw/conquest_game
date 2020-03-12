package GameConsoleTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameLoader;
import GameConsole.Core.GameState;
import GameConsole.Model.Player.Player;
import GameConsole.Strategy.AggressiveStrategy;

/**
 * 
 * This class is a test class for class AggressiveStrategy, for the methods in
 * it.
 *
 */
public class AggressiveStrategyTest {
	GameLoader gl;
	GameState gs;
	Player p1;
	AggressiveStrategy s1;

	/**
	 * Set up function, to do some initial work.
	 * 
	 * @throws Exception
	 *             If the target file is not valid, it would throw an exception.
	 */
	@Before
	public void setUp() throws Exception {
		gl = new GameLoader(null, "resources/TestResources/31.txt");
		gs = gl.getGameState();
		p1 = gs.getAllPlayers().getPlayers().get(0);

	}

	/**
	 * test function: attack(). Check if the attacker keeps attack until can't
	 * attack anymore.
	 */
	@Test
	public void testAttack() {
		p1.attack();
		assertEquals(false, p1.checkIfCanAttack());

	}

}
