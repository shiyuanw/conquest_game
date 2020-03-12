package GameConsoleTest;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Core.GameState;
import GameConsole.Core.GameLoader;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;
import GameConsole.Strategy.CheaterStrategy;
/**
 * 
 * This class is a test class for class CheaterStrategy, for the methods in it.
 *
 */
public class CheaterStrategyTest {
	GameLoader gl;
	GameState gs;
	Player p1;
	CheaterStrategy s1;
	
	/**
	 * Set up function, to do some initial work.
	 * 
	 * @throws Exception
	 *             If the target file is not valid, it would throw an exception.
	 */
	@Before
	public void setUp() throws Exception {
		gl = new GameLoader(null, "resources/TestResources/32.txt");
		gs = gl.getGameState();
		p1 = gs.getAllPlayers().getPlayers().get(0);
		
				
	}
	/**
	 * test function: attack(). Check if the attacker
	 * will conquer every bordering country .
	 */
	@Test
	public void testAttack() {
		Set<Country> neighbours = new HashSet<>();
		for(Country c: p1.getCountries()){
			for(Country neighbour: c.getBorderingCountries()){
				if(neighbour.getPlayer() != p1){
					neighbours.add(neighbour);
				}
			}
		}

		p1.attack();
		
		for (Country neighbour : neighbours) {
			assertEquals(true,p1.getCountries().contains(neighbour));
		}

	}

}
