package GameConsoleTest;

/**
 * this class is a test class for class GameState. Check if we can correctly get current state of a game.
 */
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameState;

public class GameStateTest {
	private GameState state;
	/**
	 * Set up function, to do some initial work.
	 * 
	 * @throws Exception
	 *             If the target file is not valid, it would throw an exception.
	 */
	@Before
	public void setUp() throws Exception {
		state = new GameState(null, "resources/ConquestMaps/Atlantis.map");

	}

	/**
	 * test class: GameState, function: getCurrPhase(). Check of the current
	 * phase is the initial phase when reading in a new map file.
	 */
	@Test
	public void testGetCurrPhase() {
		System.out.println("currentphase:" + state.getCurrPhase());
		assertEquals(0, state.getCurrPhase());
	}

}
