package MapEditor.Util;

import java.io.File;

/**
 * this class is for creating methods for reading the map files and parsing the
 * specific format, and check the image format
 */
public class MyStringUtil {
	private static final String[] TYPES = new String[] { ".jpeg", ".png", "jpg", ".bmp" };

	/**
	 * checking a .map file if it is matched the specific formation.
	 * 
	 * @param str
	 * @return false if it does not fit the format, otherwise return true.
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Achieving the map file absolute path, and use the map name to the .map
	 * file name.
	 * 
	 * @param path
	 * @return the new .map file name.
	 */
	public static String getMapPath(File path) {
		File dirf = path.getParentFile();
		String dir = dirf.getAbsolutePath() + File.separator;
		String fn = path.getName();
		if (fn.indexOf('.') == -1) {
			fn = fn + ".map";
		} else {
			fn = fn.substring(0, fn.indexOf('.'));
			fn += ".map";
		}
		return dir + fn;
	}

	/**
	 * check whether the map file format is in the String arr TYPES or not.
	 * 
	 * @param path
	 *            input the map path
	 * @return true if the image is in the TYPES, false if it is not.
	 */
	public static boolean checkType(File path) {
		for (String type : TYPES) {
			if (path.getName().contains(type))
				return true;
		}
		return false;
	}

	/**
	 * checking a file name is not null.
	 * 
	 * @param str
	 *            image file name
	 * @return true if name is not null, otherwise false.
	 */
	public static boolean hasLength(String str) {
		return str != null && !"".equals(str.trim());
	}

	/**
	 * this method is when we finish territories linked connection, using the
	 * method to show all the connection territories' names.
	 * 
	 * @param array
	 *            array of territories' names.
	 * @param symbol
	 *            use symbol to separate names.
	 * @return return all the territories name.
	 */
	public static String joinString(String[] array, String symbol) {
		String result = "";
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				String temp = array[i];
				if (temp != null && temp.trim().length() > 0)
					result += (temp + symbol);
			}
			if (result.length() > 1)
				result = result.substring(0, result.length() - 1);
		}
		return result;
	}

}
