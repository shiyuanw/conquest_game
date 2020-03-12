package GameConsoleTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;
import MapEditor.Model.Territory;

/**
 * 
 * This class is a test class for class ConquestMap, for the functions which can
 * check whether the map is valid when loading & saveing map.
 *
 */
public class MapLoaderTest {
	private ConquestMap map;

	/**
	 * Set up function, to do some initial work.
	 * 
	 * @throws Exception
	 *             If the target map is not valid, it would throw an exception.
	 */
	@Before
	public void setUp() throws Exception {
		map = new ConquestMap();
		map.load("resources/ConquestMaps/Atlantis.map");
	}



	/**
	 * test class: ConquestMap, function eachTerReachable(), check the
	 * territories of the map is boarding each other when we load a map, check a
	 * new territory is boarding to each other we want when we add it in the
	 * map.
	 */
	@Test
	public void testEachTerReachable() {
		assertEquals(true, map.eachTerReachable());
		Territory newTerritory = new Territory();
		newTerritory.setName("newTerritory");
		newTerritory.setCenter(1, 1);
		newTerritory.setCont(map.continents.get(0));
		map.addTerritory(newTerritory);
		assertEquals(false, map.eachTerReachable());

	}
	
	/**
	 * test class: ConquestMap, function eachTerInContReachable(), check the
	 * territories of the map is boarding each other when given a target continent, check a
	 * new territory is boarding to each other we want when we add it in the
	 * map.
	 */
	@Test
	public void testEachTerInContReachable() {
		Continent kala = map.findContinent("Kala");
		assertEquals(true, map.eachTerInContReachable(kala));
		Territory newTerritory = new Territory();
		newTerritory.setName("newTerritory");
		newTerritory.setCenter(1, 1);
		newTerritory.setContinent(kala);
		ArrayList<Territory> neighList = new ArrayList<>();
		neighList.add(map.findTerritory("Clokan"));
		neighList.add(map.findTerritory("Horkan"));
		newTerritory.setLinks(neighList);
		map.addTerritory(newTerritory);
		assertEquals(false, map.eachTerInContReachable(kala));
	}

}
