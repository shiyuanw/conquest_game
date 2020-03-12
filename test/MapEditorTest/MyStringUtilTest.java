package MapEditorTest;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import MapEditor.Util.MyStringUtil;

/**
 * 
 * This class is a test class for class MyStringUtil, this is a util class which
 * contains some assistant functions.
 *
 */
public class MyStringUtilTest {

	/**
	 * test class MyStringUtil function: isNumeric(); check if the system can
	 * distinguish input string number or not.
	 */
	@Test
	public void testIsNumeric() {
		assertEquals(true, MyStringUtil.isNumeric("12"));
		assertEquals(false, MyStringUtil.isNumeric("abc"));
	}

	/**
	 * test class MyStringUtil function: checkType(); check if the system can
	 * distinguish a right type file from wrong.
	 */
	@Test
	public void testCheckType() {
		String mapPath = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.map").getPath()
				.substring(1);
		String imagePath = this.getClass().getClassLoader().getResource("ConquestMaps/Atlantis.bmp").getPath()
				.substring(1);
		File file1 = new File(mapPath);
		File file2 = new File(imagePath);
		assertEquals(false, MyStringUtil.checkType(file1));
		assertEquals(true, MyStringUtil.checkType(file2));
	}

	/**
	 * test class MyStringUtil, function: hsLength(); check distinguish
	 * function.
	 */
	@Test
	public void testHasLength() {
		assertEquals(true, MyStringUtil.hasLength("12"));
		assertEquals(false, MyStringUtil.hasLength(""));
		assertEquals(false, MyStringUtil.hasLength(null));
	}

	/**
	 * test class MyStringUtil, function joinString(), check the override
	 * toString() method.
	 */
	@Test
	public void testJoinString() {
		String[] array = new String[] { "a", "b", "c" };
		assertEquals("a,b,c", MyStringUtil.joinString(array, ","));
	}

}
