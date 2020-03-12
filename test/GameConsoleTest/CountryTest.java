package GameConsoleTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameConsole.Model.Army.AbstractTroop;
import GameConsole.Model.Domain.Country;

/**
 * this class is a test class for class Country
 */
@SuppressWarnings("all")
public class CountryTest {
	private Country country;
	private ArrayList<AbstractTroop> troops;
	
	/**
	 * Set up function, to do some initial work.
	 * 
	 * @throws Exception
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		country = new Country();
		troops = new ArrayList<AbstractTroop>();
		country.addInfrantry(3);
	}

	/**
	 * test class: Country, function: getTroops(). Check if the continent have
	 * the same troop as the expected result.
	 */
	@Test
	public void testAddTroop() {
		assertEquals(3, country.getTroopNum());

	}

}
