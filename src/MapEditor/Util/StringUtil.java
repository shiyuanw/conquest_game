package MapEditor.Util;

import java.awt.Rectangle;

/**
 * this class is used for functions to comparison and determination of all
 * String types contents in the .map file.
 */
public class StringUtil {
	/**
	 * input a rectangle and return the rectangle's Attributes.
	 * 
	 * @param rec
	 * @return rectangle's coordinate and width and height
	 */
	public static String fromRectangle(Rectangle rec) {
		if (rec == null) {
			return "";
		}
		return rec.x + " " + rec.y + " " + rec.width + " " + rec.height;
	}

	/**
	 * this method is using to check two files names and contents are the same
	 * or not author name if null or not.
	 * 
	 * @param a
	 *            one file name or content name
	 * @param b
	 *            another file name or content na
	 * @return true these names are same, otherwise false.
	 */
	public static boolean equal(String a, String b) {
		if (a == b) {
			return true;
		}
		if ((a == null) || (b == null)) {
			return false;
		}
		return a.equals(b);
	}

	/**
	 * this method is using to check two files names and contents are the same
	 * or not author name if null or not.
	 * 
	 * @param a
	 *            name of string type
	 * @param b
	 *            name of string type
	 * @param equateNullAndEmptyString
	 * @return true if two names are the same, otherwise false
	 */
	public static boolean equal(String a, String b, boolean equateNullAndEmptyString) {
		if (a == b) {
			return true;
		}
		if (equateNullAndEmptyString) {
			if ((a == null) && (b.isEmpty())) {
				return true;
			}
			if ((b == null) && (a.isEmpty())) {
				return true;
			}
		}
		if ((a == null) || (b == null)) {
			return false;
		}
		return a.equals(b);
	}
	
	/**
	 * method to check a string is null or length is 0.
	 * @param s one string
	 * @return true if teh string is null or length is 0, otherwise false.
	 */
	public static boolean isBlank(String s) {
		return (s == null) || (s.length() == 0);
	}

	/**
	 * method to reading contents in the .map file
	 * 
	 * @param str
	 *            contents in the specific line
	 * @param def
	 *            line number
	 * @return return the specific line number.
	 */
	public static int parseInt(String str, int def) {
		if (str == null) {
			return def;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}
		return def;
	}

}
