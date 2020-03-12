package MapEditorTest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;
import MapEditor.Model.Territory;

/**
 * 
 * This class is a test class for class ConquestMap, for the functions which can
 * edit the map.
 *
 */
public class ConquestMapTest {
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
	 * test class: ConquestMap. function: addContinent(). Check if add a new
	 * continent the size of the continents list is changed or not.
	 */
	@Test
	public void testAddContinent() {
		Continent newContinent = new Continent("newContinent", 1);
		map.addContinent(newContinent);
		assertEquals(7, map.continents.size());
	}

	/**
	 * test class: ConquestMap. Function: addTerritory(). Check if when adding a
	 * new territory to the conquest map, the territory list contains the new
	 * territory.
	 */
	@Test
	public void testAddTerritory() {
		Territory newTerritory = new Territory();
		newTerritory.setName("newTerritory");
		newTerritory.setCenter(1, 1);
		newTerritory.setCont(map.continents.get(0));
		map.addTerritory(newTerritory);
		assertEquals(true, map.territories.contains(newTerritory));
	}

	/**
	 * test class: ConquestMap, function clear(). check when we clear the map,
	 * if the continent list and the territories list size are 0.
	 */
	@Test
	public void testClear() {
		map.clear();
		assertEquals(true, map.territories.size() == 0);
		assertEquals(true, map.continents.size() == 0);
	}

	/**
	 * test class: ConquestMap, function: countTerritories(). check if the
	 * specific continent"Kala" has 7 territories.
	 */
	@Test
	public void testCountTerritories() {
		assertEquals(7, map.countTerritories(map.findContinent("Kala")));
	}

	/**
	 * test class: ConquestMap, function: deleteContinent(). Check if there is
	 * not Continent for territories when deleting the continent "Kala".
	 */
	@Test
	public void testDeleteContinent() {
		Continent kala = map.findContinent("Kala");
		map.deleteContinent(kala);
		assertEquals(false, map.continents.contains(kala));
		assertEquals(null, map.findTerritory("Forgoth").getContinent());
	}

	/**
	 * test class: ConquestMap, functionL: findTerritory(), check if there is
	 * the specific territory in the territories list when deleting it.
	 */
	@Test
	public void testDeleteTerritory() {
		Territory forgoth = map.findTerritory("Forgoth");
		map.deleteTerritory(forgoth);
		assertEquals(false, map.territories.contains(forgoth));
		assertEquals(false, map.findTerritory("Rove").getLinks().contains(forgoth));
	}

	/**
	 * test class: ConquestMap, function: FindTerritory(), check if a
	 * territories list has the specific territory.
	 */
	@Test
	public void testFindTerritory() {
		Territory forgoth = map.findTerritory("Forgoth");
		assertEquals(true, map.territories.contains(forgoth));
		assertEquals("Forgoth", forgoth.getName());
		assertEquals("Kala", forgoth.getContinent().getName());
	}

	/**
	 * test class: ConquestMap,function: findContinent(), check if we can find a
	 * new continent when we add it.
	 */
	@Test
	public void testFindContinent() {
		Continent kala = map.findContinent("Kala");
		assertEquals(true, map.continents.contains(kala));
		assertEquals("Kala", kala.getName());
		assertEquals(6, kala.getBonus());
	}

	/**
	 * test class: ConquestMap, function: buildTerrtoryLicks(), check if we can
	 * build licks of territories.
	 */
	@Test
	public void testBuildTerritoryLinks() {
		Territory forgoth = map.findTerritory("Forgoth");
		Territory rove = map.findTerritory("Rove");
		map.buildTerritoryLinks(forgoth);
		assertEquals(2, forgoth.getLinkNames().size());
		assertEquals(2, forgoth.getLinks().size());
		assertEquals(true, forgoth.getLinks().contains(rove));
	}

	/**
	 * test class: ConquestMap, function: updateContinent(), check if the
	 * continent information is updated or not when we change the continent's
	 * name and bonus.
	 */
	@Test
	public void testUpdateContinent() {
		Continent kala = map.findContinent("Kala");
		map.updateContinent("kala", "newKala", 1);
		assertEquals("newKala", kala.getName());
		assertEquals(1, kala.getBonus());
	}

	/**
	 * test class: ConquestMap, function: ConquestMap.territories().size(), test
	 * class: Territory, function: getContinent(), test class: Continent,
	 * function: getName(). check if a map including a specific terrtories list,
	 * and if there is a specific territory.
	 */
	@Test
	public void testLoad() throws Exception {
		map.load("resources/ConquestMaps/Atlantis.map");
		assertEquals(42, map.territories.size());
		assertEquals(2, map.findTerritory("Forgoth").getLinks().size());
		assertEquals("Kala", map.findTerritory("Forgoth").getContinent().getName());
	}

	/**
	 * test class: ConquestMap, function load(). check if there is a error
	 * message when we load a invalid map file.
	 */
	@Test
	public void testLoadInvalidMap() {
		try {
			map.load("resources/ConquestMaps/Atlantis(invalid).map");
		} catch (Exception ex) {
			assertThat(ex.getMessage(), containsString("There's some teris cannot reach to every other territories!"));
		}
	}

	/**
	 * test class ConquestMap, function: addContinent(), function save(). check
	 * if we can add a new continent and save as a new map file in the local
	 */
	@Test
	public void testSaveString() throws Exception {
		map.load("resources/ConquestMaps/Atlantis.map");
		map.addContinent(new Continent("newContinent", 1));
		map.save("resources/TestResources/1.map");
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
	 * territories of the map is boarding each other when given a target
	 * continent, check a new territory is boarding to each other we want when
	 * we add it in the map.
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
