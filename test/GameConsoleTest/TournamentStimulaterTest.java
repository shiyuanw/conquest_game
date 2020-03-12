package GameConsoleTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameLoader;
import GameConsole.Core.GameState;
import GameConsole.Core.TournamentStimulater;
import GameConsole.Model.Player.Player;

/**
 * 
 * This class is a test class for class TournamentStimulater.
 *
 */
public class TournamentStimulaterTest {
	GameState gs;
	GameLoader gl;
	Player p1;
	Player p2;
	Player p3;
	Player p4;

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
	}

	/**
	 * test function: execute(). Check if the tournament can be executed.
	 */
	@Test
	public void testExecute() {

		TournamentStimulater gameSt = new TournamentStimulater(gs, gs.getAllPlayers().getPlayers(), 10, false, false);

		List<String> result = new ArrayList<>();
		result.add("draw");
		result.add("Cheater");
		result.add("Aggresive");
		result.add("Random");
		result.add("Human");
		assertEquals(true, result.contains(gameSt.execute()));
	}
}
