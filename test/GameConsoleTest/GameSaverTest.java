package GameConsoleTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameLoader;
import GameConsole.Core.GameSaver;
import GameConsole.Core.GameState;
import GameConsole.Model.Player.Player;
import GameConsole.Strategy.AggressiveStrategy;
import GameConsole.Strategy.HumanStrategy;
/**
 * 
 * This class is a test class for class GameSaver and the methods in it.
 *
 */
public class GameSaverTest {

	GameLoader gl;
	GameState gameState;
	GameSaver gs;
	Player p1;
	Player p2;
	Player p3;
	HumanStrategy s1;
	AggressiveStrategy s2;
	
	/**
	 * Set up function, to do some initial work.
	 * 
	 * @throws Exception
	 *             If the target file is not valid, it would throw an exception.
	 */
	@Before
	public void setUp() throws Exception {
		gameState = new GameState(null, "resources/ConquestMaps/Atlantis.map");
		s1 = new HumanStrategy();
		s2 = new AggressiveStrategy();
		p1 = new Player("p1", null, gameState, s1);
		p2 = new Player("p2", null, gameState, s2);
		p3 = new Player("p3", null, gameState, s1);
		gameState.addPlayer(p1);
		gameState.addPlayer(p2);
		gameState.addPlayer(p3);
		gameState.setCurrPlayer(p1);
		gs = new GameSaver(gameState, null);
		gs.setFlagForTest(false);
	}
	
	/**
	 * test function: save(). Check if the method
	 * can save the map correctly.
	 * @throws Exception 
	 */
	@Test
	public void testSave() throws Exception {
		gs.save("resources/GimpFiles/testSave.txt");
		gl = new GameLoader(null, "resources/GimpFiles/testSave.txt");
		assertEquals(3, gl.getGameState().getAllPlayers().getPlayers().size());
	}
	


}
