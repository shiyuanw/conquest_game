package GameConsoleTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import MapEditorTest.ConquestMapTest;
import MapEditorTest.MyStringUtilTest;
import MapEditorTest.TablePanelTest;

/**
 * 
 * Using Suite as a runner to manually build a suite containing tests from many
 * classes.
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
		TournamentStimulaterTest.class, 
		CountryTest.class, 
		GroupTest.class, 
		PlayerTest.class, 
		GameStateTest.class, 
		CardsTest.class,
		AggressiveStrategyTest.class, 
		CheaterStrategyTest.class, 
		MapLoaderTest.class, 
		ConquestMapTest.class, 
		MyStringUtilTest.class, 
		TablePanelTest.class, 
		GameLoaderTest.class,
		GameSaverTest.class,

})
public class SuiteTest {

}
