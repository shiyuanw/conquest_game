package MapEditorTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import MapEditor.Model.ConquestMap;
import MapEditor.Model.Continent;
import MapEditor.Model.Territory;
import MapEditor.View.TablePanel;

/**
 * 
 * This class is a test class for class TablePanel, mostly test the function
 * whether the Observer can do corresponding change to the state change in
 * Observable.
 *
 */
public class TablePanelTest {
	private ConquestMap map;
	private TablePanel table;

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
		table = new TablePanel(map);
		map.addObserver(table);
	}

	/**
	 * test class Territory, function: set continent(). test class ConquestMap,
	 * addContinent(). check when we add a new territory to the continent of a
	 * conquest map, the tablepanel can update the information that territories
	 * size is pulsing.
	 */
	@Test
	public void testUpdate() {
		Territory newTerritory = new Territory();
		newTerritory.setName("newTerritory");
		newTerritory.setCenter(1, 1);
		newTerritory.setCont(map.continents.get(0));
		map.addTerritory(newTerritory);
		Object[][] terNames = table.getTerNames();
		int length = terNames.length;
		assertEquals(43, length);

		Continent newContinent = new Continent("newContinent", 1);
		map.addContinent(newContinent);
		assertEquals(7, table.getContNames().length);
	}

}
