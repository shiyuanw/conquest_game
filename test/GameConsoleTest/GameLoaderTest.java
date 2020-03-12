package GameConsoleTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameLoader;
import GameConsole.Core.GameState;
import GameConsole.Model.Player.Player;
import GameConsole.Strategy.Strategy;
/**
 * 
 * This class is a test class for class GameLoader and the methods in it.
 *
 */
public class GameLoaderTest {
	GameLoader gl;
	GameState gs;
	Player p1;
	Player p2;
	
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
		p2 = gs.getAllPlayers().getPlayers().get(1);
	}
	
	/**
	 * test function: loadPlayers(). Check if the method
	 * can get the correct number of the players.
	 */
	@Test
	public void testLoadPlayers() {
		assertEquals(3, gs.getAllPlayers().getPlayers().size());
		assertEquals("p1", p1.getName());
		assertEquals("p2", p2.getName());
	}
	
	/**
	 * test function: loadCountries(). Check if the method
	 * can get the correct info of the countries.
	 */
	@Test
	public void testloadCountries() {

		assertEquals(14, p1.getCountries().size());
		assertEquals(14, p2.getCountries().size());
		
	}

}
