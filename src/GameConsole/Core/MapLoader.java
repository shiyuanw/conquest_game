package GameConsole.Core;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import GameConsole.Model.Domain.Continent;
import GameConsole.Model.Domain.Country;
import MapEditor.Util.StringUtil;

/**
 * The class is to load a map to the game
 */
public class MapLoader {
	public static enum ScrollOptions {
		HORIZONTAL, VERTICAL, NONE
	}

	private String mapFilePath;
	private String imageFilePath;
	private ScrollOptions scroll;
	private String author;
	private boolean wrap;
	private boolean warn;
	public ArrayList<Continent> continents = new ArrayList<>();
	public ArrayList<Country> countries = new ArrayList<>();

	/**
	 * Constructor method
	 */
	public MapLoader() {
		clear();
	}

	/**
	 * To set the world
	 * 
	 * @param world
	 *            the selected world that will be set as the world where the
	 *            players are in
	 */
	public void setWorld(World world) {
		System.out.println(this.continents.size());
		for (Continent continent : this.continents) {
			world.addContinent(continent);
		}
	}

	/**
	 * Method to clear the values of the corresponding attributes
	 */
	public void clear() {
		this.mapFilePath = null;
		this.imageFilePath = null;
		this.author = null;
		this.scroll = ScrollOptions.HORIZONTAL;
		this.wrap = false;
		this.warn = true;
		this.continents.clear();
		this.countries.clear();
	}

	/**
	 * The method which convert ScrollOptions from a input String
	 * 
	 * @param option
	 *            The input String
	 * @param defaultValue
	 *            The default output ScrollOptions type
	 * @return
	 */
	private ScrollOptions convertScrollOptionsString(String option, ScrollOptions defaultValue) {
		try {
			return ScrollOptions.valueOf(option.toUpperCase());
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * To find the continent from the continent list
	 * 
	 * @param name
	 *            the continent name
	 * @return the continent if the continent is found; otherwise return null
	 */
	public Continent findContinent(String name) {
		for (Continent cont : this.continents) {
			if (name.equalsIgnoreCase(cont.getName())) {
				return cont;
			}
		}
		return null;
	}

	/**
	 * Makes the pointer of LineNumberReader points to the target section.
	 * 
	 * @param in
	 * @param section
	 * @throws IOException
	 */
	public void findSection(LineNumberReader in, String section) throws IOException {
		String head = "[" + section + "]";
		String line;
		do {
			line = in.readLine();
			if (line == null) {
				throw new EOFException("EOF encountered before section " + head + " found");
			}
		} while (!line.equalsIgnoreCase(head));
	}

	/**
	 * To find the country from the country list
	 * 
	 * @param name
	 *            the country name
	 * @return the country if the country is found; otherwise return null
	 */
	public Country findCountry(String name) {
		for (Country ter : this.countries) {
			if (name.equalsIgnoreCase(ter.getName())) {
				return ter;
			}
		}
		return null;
	}

	/**
	 * To get the author
	 * 
	 * @return the author with String type
	 */
	public final String getAuthor() {
		return this.author;
	}

	/**
	 * The method to get the image file name.
	 * 
	 * @return The image file name
	 */
	public String getImageFileName() {
		if (this.imageFilePath == null) {
			return "";
		}
		return new File(this.imageFilePath).getName();
	}

	/**
	 * To get the image file path
	 * 
	 * @return the image file path with String type
	 */
	public String getImageFilePath() {
		return this.imageFilePath;
	}

	/**
	 * To get the map directory
	 * 
	 * @return the map directory with File type
	 */
	public File getMapDirectory() {
		if (this.mapFilePath == null) {
			return null;
		}
		return new File(this.mapFilePath).getParentFile();
	}

	/**
	 * To get the map file path
	 * 
	 * @return the map file path with String type
	 */
	public String getMapFilePath() {
		return this.mapFilePath;
	}

	/**
	 * To get the map name
	 * 
	 * @return the map name with String type
	 */
	public String getMapName() {
		if (this.mapFilePath == null) {
			return "Untitled Map";
		}
		return new File(this.mapFilePath).getName();
	}

	/**
	 * Return the Scroll Options type
	 * 
	 * @return The current Scroll Options type
	 */
	public final ScrollOptions getScroll() {
		return this.scroll;
	}

	/**
	 * Return whether the map needs to be warned if the map is not valid
	 * 
	 * @return The boolean whether the map needed to be vertified
	 */
	public final boolean isWarn() {
		return this.warn;
	}

	/**
	 * Return whether the map is wrap
	 * 
	 * @return The boolean of whether the map is wrap
	 */
	public final boolean isWrap() {
		return this.wrap;
	}

	/**
	 * Method to load the map
	 * 
	 * @param mapFilePath
	 *            the map file path with String type
	 * @throws IOException
	 */
	public void load(String mapFilePath) throws IOException {
		clear();
		this.mapFilePath = mapFilePath;
		LineNumberReader in = new LineNumberReader(new FileReader(mapFilePath));
		loadMapSection(in);
		loadContinents(in);
		loadCountries(in);
	}

	/**
	 * Method to load the continents
	 * 
	 * @param in
	 *            A Reader object to provide the underlying stream
	 * @throws IOException
	 */
	private void loadContinents(LineNumberReader in) throws IOException {
		this.continents.clear();
		for (;;) {
			String line = in.readLine();
			if (line == null) {
				throw new IOException("[Territories] Section expected; found EOF");
			}
			if (!line.trim().equals("")) {
				if (line.startsWith("[")) {
					if (line.equalsIgnoreCase("[Territories]")) {
						return;
					}
					throw new IOException("[Territories] Section expected; found " + line);
				}
				int eqloc = line.indexOf("=");
				if (eqloc == -1) {
					throw new IOException("Invalid continent line: " + line);
				}
				String cname = line.substring(0, eqloc).trim();
				int cbonus = StringUtil.parseInt(line.substring(eqloc + 1).trim(), -99999);
				if ((cname.length() < 1) || (cbonus == -99999)) {
					throw new IOException("Invalid continent line: " + line);
				}
				this.continents.add(new Continent(cbonus, cname));
			}
		}

	}

	/**
	 * Method to load the map section
	 * 
	 * @param in
	 *            A Reader object to provide the underlying stream
	 * @throws IOException
	 */
	private void loadMapSection(LineNumberReader in) throws IOException {
		findSection(in, "Map");
		for (;;) {
			String line = in.readLine();
			if (line == null) {
				throw new IOException("[Continents] Section expected; found EOF");
			}
			if (!line.trim().equals("")) {
				if (line.startsWith("[")) {
					if (line.equalsIgnoreCase("[Continents]")) {
						return;
					}
					throw new IOException("[Continents] Section expected; found " + line);
				}
				String[] pair = line.split("=", 2);
				if ((pair != null) && (pair.length == 2)) {
					String prop = pair[0].toLowerCase();
					String val = pair[1];
					if ("image".equals(prop)) {
						if ((val != null) && (val.length() > 0)) {
							String os = System.getProperty("os.name").toLowerCase();
							if (os.contains("mac")) {
								this.imageFilePath = (new File(this.mapFilePath).getParent() + "/" + val);
							} else {
								this.imageFilePath = (new File(this.mapFilePath).getParent() + "\\" + val);
							}
						}
					} else if ("wrap".equals(prop)) {
						this.wrap = val.equalsIgnoreCase("yes");
					} else if ("scroll".equals(prop)) {
						this.scroll = convertScrollOptionsString(val, ScrollOptions.NONE);
					} else if ("author".equals(prop)) {
						this.author = val;
					} else if ("warn".equals(prop)) {
						this.warn = val.equalsIgnoreCase("yes");
					}
				}
			}
		}
	}

	/**
	 * Method to load the countries
	 * 
	 * @param in
	 *            A Reader object to provide the underlying stream
	 * @throws IOException
	 */
	private void loadCountries(LineNumberReader in) throws IOException {
		this.countries.clear();
		Country ter;
		for (;;) {
			String line = in.readLine();
			if (line == null) {
				break;
			}
			if (!line.trim().equals("")) {
				ter = parseCountryLine(line);
				this.countries.add(ter);
				Continent c = findContinent(ter.getContinent().getName());
				c.addCountry(ter);
			}
		}
		for (Country t : this.countries) {
			buildCountryName(t);
		}
	}

	/**
	 * Method to build country name
	 * 
	 * @param t
	 *            the selected country that will be built the name with Country
	 *            type
	 */
	public void buildCountryName(Country t) {
		if (findCountry(t.getName()) != null) {
			if (t.getLinkNames().size() > 0) {
				for (String linkName : t.getLinkNames()) {
					Country link = findCountry(linkName);
					if (!t.getBorderingCountries().contains(link)) {
						t.addBorderingCountry(link);
					}
				}
			}
		}
	}

	/**
	 * Method to parse the country line
	 * 
	 * @param line
	 *            the object that will be parsed
	 * @return the created country with Country type
	 * @throws IOException
	 */
	private Country parseCountryLine(String line) throws IOException {
		try {
			StringTokenizer st = new StringTokenizer(line, ",");
			Country ter = new Country();
			ter.setName(st.nextToken().trim());
			ter.setXLoc(Integer.parseInt(st.nextToken().trim()));
			ter.setYLoc(Integer.parseInt(st.nextToken().trim()));
			String name = st.nextToken().trim();
			ter.setContinent(findContinent(name));
			while (st.hasMoreTokens()) {
				ter.getLinkNames().add(st.nextToken().trim());
			}
			return ter;
		} catch (Exception e) {
			throw new IOException("Invalid territory line (" + e + "): " + line);
		}
	}

	/**
	 * To set the author
	 * 
	 * @param author
	 *            the desired author that want to be set with String type
	 */
	public final void setAuthor(String author) {
		if (!StringUtil.equal(author, this.author)) {
			this.author = author;
		}
	}

	/**
	 * To set the image file path
	 * 
	 * @param imageFilePath
	 *            The target image file path
	 */
	public void setImageFilePath(String imageFilePath) {
		if (!StringUtil.equal(this.imageFilePath, imageFilePath)) {
			this.imageFilePath = imageFilePath;
		}
	}

	/**
	 * To set the map file path
	 * 
	 * @param mapFilePath
	 *            The target map file path
	 */
	public void setMapFilePath(String mapFilePath) {
		this.mapFilePath = mapFilePath;
	}

	/**
	 * To set map Scroll Options
	 * 
	 * @param scroll
	 *            The target Scroll Options
	 */
	public final void setScroll(ScrollOptions scroll) {
		if (this.scroll != scroll) {
			this.scroll = scroll;
		}
	}

	/**
	 * To set whether the map needs to be warned
	 * 
	 * @param warn
	 *            Whether the map needs to be warned
	 */
	public final void setWarn(boolean warn) {
		if (warn != this.warn) {
			this.warn = warn;
		}
	}

	/**
	 * To set whether the map needs to wrap
	 * 
	 * @param wrap
	 *            Whether the map needs to wrap
	 */
	public final void setWrap(boolean wrap) {
		if (wrap != this.wrap) {
			this.wrap = wrap;
		}
	}

	/**
	 * check map parameters, if a territory has links. check if a map file has a
	 * matched image file. check if a map file and its matched image file are in
	 * the same path. check the territory link information. check if the
	 * territory list is not empty and it can not reach to other territories.
	 * 
	 * @return false if also the check failed
	 *
	 */
	public boolean validityCheck() {
		ArrayList<String> probs = new ArrayList<>();
		if ((this.countries == null) || (this.countries.isEmpty())) {
			probs.add("Map contains no territories");
		}
		if ((this.imageFilePath == null) || (this.imageFilePath.isEmpty())) {
			probs.add("Map image is not specified");
		} else if (getMapDirectory() != null) {
			if (isDisparateImageFileDirectory()) {
				probs.add("Map and image files are not located in the same directory");
			}
		}
		if (hasOneWayLinks()) {
			probs.add("");
		}

		if (this.countries.size() > 0 && !eachTerReachable()) {
			probs.add("There's some teris cannot reach to every other territories!");
		}

		if (probs.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * Check whether the current map contains one way link.
	 * 
	 * @return Return true if the current map contains one way link, return
	 *         false if not.
	 */
	public boolean hasOneWayLinks() {
		for (Country ter : this.countries) {
			if (ter.getBorderingCountries().size() != 0) {
				for (Country ter2 : ter.getBorderingCountries()) {
					if (ter2.getBorderingCountries().size() == 0 || !ter2.getBorderingCountries().contains(ter)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * check whether a map contains two different format files including .map
	 * text file and .bmp image file.
	 * 
	 * @return Return true if two different files are matched exactly, other
	 *         wise return false.
	 */
	public boolean isDisparateImageFileDirectory() {
		if (this.imageFilePath == null) {
			return false;
		}
		if (this.mapFilePath == null) {
			return false;
		}
		File mapDir = new File(this.mapFilePath).getParentFile();
		File imgDir = new File(this.imageFilePath).getParentFile();
		return (mapDir != null) && (imgDir != null) && (mapDir.compareTo(imgDir) != 0);
	}

	/**
	 * method to check if a territory is board to others in the territories
	 * list.
	 * 
	 * @return true if they are boarded, else return false.
	 */
	public boolean eachTerReachable() {
		clearReach();
		Country head = this.countries.get(0);
		DFS(head);
		int count = 0;
		for (Country t : this.countries) {
			if (t.hasReached) {
				count++;
			}
		}
		clearReach();
		if (count == this.countries.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * delete the linked relationships among territories.
	 */
	private void clearReach() {
		for (Country t : this.countries) {
			t.hasReached = false;
		}
	}

	/**
	 * searching all the connections relationship among territories.
	 * 
	 * @param head
	 */
	private void DFS(Country head) {
		head.hasReached = true;
		if (head.getBorderingCountries().size() == 0) {
			return;
		}
		for (Country neighbour : head.getBorderingCountries()) {
			if (!neighbour.hasReached) {
				DFS(neighbour);
			}
		}
	}

}
