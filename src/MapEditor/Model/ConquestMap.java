package MapEditor.Model;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.StringTokenizer;

import MapEditor.Util.MyStringUtil;
import MapEditor.Util.StringUtil;
import MapEditor.View.LogPanel;

/**
 * 
 * The Map model. Handles with the data change, also takes care of load & save
 * function. In Observer pattern, plays the role of subject.
 *
 */
public class ConquestMap extends Observable implements Comparator<Object> {
	public static enum ScrollOptions {
		HORIZONTAL, VERTICAL, NONE;
	}

	private String mapFilePath;
	private String imageFilePath;
	private ScrollOptions scroll;
	private String author;
	private boolean wrap;
	private boolean warn;
	public ArrayList<Continent> continents = new ArrayList<>();
	public ArrayList<Territory> territories = new ArrayList<>();

	/**
	 * Create a new and empty map. If need to load existing map, please see
	 * function load().
	 */
	public ConquestMap() {
		clear();
	}

	/**
	 * If the data has been changed, call this function to notify observers.
	 */
	public void changeState() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Add a qualified continent to the local variables.
	 * 
	 * @param cont
	 *            The input continent.
	 */
	public void addContinent(Continent cont) {
		if (findContinent(cont.getName()) == null) {
			this.continents.add(cont);
			changeState();
		}
	}

	/**
	 * Add a qualified territory to the local variables, then build the
	 * neighbour links.
	 * 
	 * @param ter
	 *            The input territory.
	 * 
	 */
	public void addTerritory(Territory ter) {
		if (findTerritory(ter.name) == null) {
			this.territories.add(ter);
			ArrayList<String> linkNames = ter.getLinkNames();
			if (linkNames.size() > 0) {
				for (String name : linkNames) {
					Territory neighbour = findTerritory(name);
					if (neighbour != null) {
						neighbour.getLinkNames().add(ter.getName());
						buildTerritoryLinks(neighbour);
					}
				}
			}
			changeState();
		}
	}

	/**
	 * Initial function of the class, normalize the local variables.
	 */
	public void clear() {
		this.mapFilePath = null;
		this.imageFilePath = null;
		this.author = null;
		this.scroll = ScrollOptions.HORIZONTAL;
		this.wrap = false;
		this.warn = true;
		this.continents.clear();
		this.territories.clear();
		changeState();

	}

	/**
	 * The override function for Comparator, designed to compare the order of
	 * two territory.
	 */
	public int compare(Object o1, Object o2) {
		if ((o1 == null) && (o2 == null)) {
			return 0;
		}
		if (o1 == null) {
			return -1;
		}
		if (o2 == null) {
			return 1;
		}
		if (((o1 instanceof Territory)) && ((o2 instanceof Territory))) {
			Territory a = (Territory) o1;
			Territory b = (Territory) o2;
			if (a.getContinent() != b.getContinent()) {
				return compare(a.getContinent(), b.getContinent());
			}
			return a.name.compareTo(b.name);
		}
		return o1.toString().compareTo(o2.toString());
	}

	/**
	 * Convert the string to ScrollOptions enum.
	 * 
	 * @param option
	 *            The input string.
	 * @param defaultValue
	 *            If the string is not valid, return the default enum.
	 * @return The output ScrollOptions enum.
	 */
	private ScrollOptions convertScrollOptionsString(String option, ScrollOptions defaultValue) {
		try {
			return ScrollOptions.valueOf(option.toUpperCase());
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Count the number of current territories in target continent.
	 * 
	 * @param cont
	 *            The target continent.
	 * @return The number of current territories.
	 */
	public int countTerritories(Continent cont) {
		int total = 0;
		for (Territory ter : this.territories) {
			if (ter.getContinent() == cont) {
				total++;
			}
		}
		return total;
	}

	/**
	 * Delete the input continent, and remove the link between continent and its
	 * territories,set null continent to these territories.
	 * 
	 * @param cont
	 *            The target continent needed to remove.
	 */
	public void deleteContinent(Continent cont) {
		if (this.continents.contains(cont)) {
			this.continents.remove(cont);
			ArrayList<Territory> temp = new ArrayList<>();
			for (Territory ter : this.territories) {
				if (ter.getContinent() == cont) {
					temp.add(ter);
				}
			}
			for (Territory ter : temp) {
				ter.setContinent(null);
			}
			changeState();

		}
	}

	/**
	 * Delete the target territory, also remove all links in its neighbours.
	 * 
	 * @param ter
	 *            The target territory needed to be removed.
	 */
	public void deleteTerritory(Territory ter) {
		if (this.territories.contains(ter)) {
			this.territories.remove(ter);
			ArrayList<String> linkNames = ter.getLinkNames();
			if (linkNames.size() > 0) {
				for (String name : linkNames) {
					Territory neighbour = findTerritory(name);
					if (neighbour != null) {
						neighbour.getLinkNames().remove(ter.getName());
						buildTerritoryLinks(neighbour);
					}
				}
			}
		}
		changeState();
	}

	/**
	 * Input a name of the continent, if valid, return the continent.
	 * 
	 * @param name
	 *            The input name.
	 * @return If name is valid, return the continent. If not valid, return
	 *         null.
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
	 * Return the index of continent in local variables.
	 * 
	 * @param name
	 *            The target name of continent wanted to find out.
	 * @return If name is valid, return the index. If not, return -1.
	 */
	public int findContinentIndex(String name) {
		for (int i = 0; i < this.continents.size(); i++) {
			Continent cont = (Continent) this.continents.get(i);
			if (name.equalsIgnoreCase(cont.getName())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Makes the pointer of LineNumberReader points to the target section.
	 * 
	 * @param in
	 *            The input LineNumberReader.
	 * @param section
	 *            The section among "Map", "Continent" and "Territory".
	 * @throws IOException
	 */
	private int findSection(LineNumberReader in, String section) throws IOException {
		String head = "[" + section + "]";
		String line;
		do {
			line = in.readLine();
			if (line == null) {
				throw new EOFException("EOF encountered before section " + head + " found");
			}
		} while (!line.equalsIgnoreCase(head));
		return in.getLineNumber();
	}

	/**
	 * Input a name of the territory, if valid, return the territory.
	 * 
	 * @param name
	 *            The input name.
	 * @return If name is valid, return the territory. If not valid, return
	 *         null.
	 */
	public Territory findTerritory(String name) {
		for (Territory ter : this.territories) {
			if (name.equalsIgnoreCase(ter.name)) {
				return ter;
			}
		}
		return null;
	}

	/**
	 * Return the index of territory in local variables.
	 * 
	 * @param name
	 *            The target name of territory wanted to find out.
	 * @return If name is valid, return the index. If not, return -1.
	 */
	public int findTerritoryIndex(String name) {
		for (int i = 0; i < this.territories.size(); i++) {
			Territory ter = (Territory) this.territories.get(i);
			if (name.equalsIgnoreCase(ter.name)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return the current Author.
	 * 
	 * @return The current Author.
	 */
	public final String getAuthor() {
		return this.author;
	}

	/**
	 * Return a unrepeated territory set of target continent.
	 * 
	 * @param cont
	 *            The target continent.
	 * @return Set of territories in target continent.
	 */
	public HashSet<Territory> getContinentTerritories(Continent cont) {
		HashSet<Territory> ters = new HashSet<>();
		for (Territory ter : this.territories) {
			if (ter.getContinent() == cont) {
				ters.add(ter);
			}
		}
		return ters;
	}

	/**
	 * Return the current ImageFileName.
	 * 
	 * @return The current ImageFileName.
	 */
	public String getImageFileName() {
		if (this.imageFilePath == null) {
			return "";
		}
		return new File(this.imageFilePath).getName();
	}

	/**
	 * Return the current ImageFilePath.
	 * 
	 * @return The current ImageFilePath.
	 */
	public String getImageFilePath() {
		return this.imageFilePath;
	}

	/**
	 * Return the current MapDirectory.
	 * 
	 * @return The current MapDirectory.
	 */
	public File getMapDirectory() {
		if (this.mapFilePath == null) {
			return null;
		}
		return new File(this.mapFilePath).getParentFile();
	}

	/**
	 * Return the current MapFilePath.
	 * 
	 * @return The current MapFilePath.
	 */
	public String getMapFilePath() {
		return this.mapFilePath;
	}

	/**
	 * Return the current MapName.
	 * 
	 * @return The current MapName.
	 */
	public String getMapName() {
		if (this.mapFilePath == null) {
			return "Untitled Map";
		}
		return new File(this.mapFilePath).getName();
	}

	/**
	 * Return the current SaveImageFilePath.
	 * 
	 * @return The current SaveImageFilePath.
	 */
	public String getSaveImageFilePath() {
		if (this.imageFilePath == null) {
			return "";
		}
		if (isDisparateImageFileDirectory()) {
			return this.imageFilePath;
		}
		return getImageFileName();
	}

	/**
	 * Return the current ScrollOptions.
	 * 
	 * @return The current ScrollOptions.
	 */
	public final ScrollOptions getScroll() {
		return this.scroll;
	}

	/**
	 * Check whether the current map contains one way link.
	 * 
	 * @return Return true if the current map contains one way link, return
	 *         false if not.
	 */
	public boolean hasOneWayLinks() {
		for (Territory ter : this.territories) {
			if (ter.getLinks().size() != 0) {
				for (Territory ter2 : ter.getLinks()) {
					if (ter2.getLinks().size() == 0 || !ter2.getLinks().contains(ter)) {
						LogPanel.addLog(ter2.getName() + " has no link with " + ter.getName()
								+ ", please check your map file!");
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
	 * check whether is Warn or not
	 * 
	 * @return Return warn
	 */
	public final boolean isWarn() {
		return this.warn;
	}

	/**
	 * check whether is Warp or not
	 * 
	 * @return Return warp
	 */

	public final boolean isWrap() {
		return this.wrap;
	}

	/**
	 * loading the map in absolute path and if a map file does not match the
	 * format, throwing the exception and resetting. parsing map, reading
	 * information of Continents and Territories, finishing map reading and
	 * change current state.
	 * 
	 * @param mapFilePath
	 *            path where map file locates.
	 * @throws IOException
	 * @see validityCheck()
	 * @see clear()
	 * @see changeState()
	 */
	public void load(String mapFilePath) throws Exception {
		clear();
		this.mapFilePath = mapFilePath;
		LineNumberReader in = new LineNumberReader(new FileReader(mapFilePath));
		loadMapSection(in);
		loadContinents(in);
		loadTerritories(in);

		ArrayList<String> errMsg;
		if ((errMsg = loadingCheck()).isEmpty())
			changeState();
		else {
			for (String string : errMsg) {
				throw new RuntimeException(string);
			}
		}

	}

	/**
	 * parsing map file and search Territories information, if the Territories
	 * information matches the format, IO streaming in, otherwise printing the
	 * error information where it locates, throws exception.
	 * 
	 * @param in
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
				this.continents.add(new Continent(cname, cbonus));
			}
		}

	}

	/**
	 * parsing map file and search Continents information, if the Continents
	 * information matches the format, IO streaming in, otherwise printing the
	 * error information where it locates, throws exception.
	 * 
	 * @param in
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
							this.imageFilePath = (new File(this.mapFilePath).getParent() + "\\" + val);
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
	 * building links among all valid Territories, if one Territory matches the
	 * format and it does not exist in the Territories list, building links
	 * among them, otherwise break.
	 * 
	 * @param in
	 * @throws IOException
	 * @see buildTerritoryLinks();
	 */
	private void loadTerritories(LineNumberReader in) throws IOException {
		this.territories.clear();
		Territory ter;
		for (;;) {
			String line = in.readLine();
			if (line == null) {
				break;
			}
			if (!line.trim().equals("")) {
				ter = parseTerritoryLine(line);
				this.territories.add(ter);
			}
		}
		for (Territory t : this.territories) {
			buildTerritoryLinks(t);
		}
	}

	/**
	 * method building connections among territories, each territory has a
	 * parameters of its linked territories
	 * 
	 * @param t
	 */
	public void buildTerritoryLinks(Territory t) {
		if (findTerritory(t.getName()) != null) {
			Set<String> set = new HashSet<>();
			for (String linkName : t.getLinkNames()) {
				if (MyStringUtil.hasLength(linkName)) {
					set.add(linkName);
				}
			}
			t.setLinkNames(new ArrayList<String>(set));

			t.links = new ArrayList<Territory>();
			if (t.getLinkNames().size() > 0) {
				for (String linkName : t.getLinkNames()) {
					Territory link = findTerritory(linkName);
					t.getLinks().add(link);
				}

			}
		}
	}

	/**
	 * parsing the neighbour of the territory, spliting with ","
	 * 
	 * @param line
	 * @return return territories list
	 * @throws IOException
	 * @see getContinentX(); getContinentY();
	 * @see getCenterX(); getCenterY();
	 */
	private Territory parseTerritoryLine(String line) throws IOException {
		try {
			StringTokenizer st = new StringTokenizer(line, ",");
			Territory ter = new Territory();
			ter.name = st.nextToken().trim();
			ter.setCenter(Integer.parseInt(st.nextToken().trim()), Integer.parseInt(st.nextToken().trim()));

			if (st.hasMoreTokens()) {
				String name = st.nextToken().trim();
				ter.setContinent(findContinent(name));
				if ((ter.name == null) || (ter.name.length() < 0)) {
					throw new Exception("name not found");
				}
				if ((ter.getCenterX() == -1) || (ter.getCenterY() == -1)) {
					throw new Exception("invalid coordinates");
				}
				if (ter.getContinent() == null && !name.equals("")) {
					ter.getLinkNames().add(name);
				}
				while (st.hasMoreTokens()) {
					ter.getLinkNames().add(st.nextToken().trim());
				}
			}
			return ter;
		} catch (Exception e) {
			throw new IOException(" :Invalid territory line (" + e + "): " + line);
		}
	}

	/**
	 * updating the continent information, containing original name and new name
	 * and bonus which are expected to change, And change state of the new
	 * continent.
	 * 
	 * @param oldName
	 * @param newName
	 * @param newBonus
	 */
	public void updateContinent(String oldName, String newName, int newBonus) {
		Continent continent = findContinent(oldName);
		continent.setName(newName);
		continent.setBonus(newBonus);
		changeState();
	}

	/**
	 * saving a map, if path is valid and save the file, else throws path does
	 * not exist information.
	 * 
	 * @throws IOException
	 * @throws Exception
	 * @see validityCheck()
	 */
	public void save() throws IOException, Exception {
		if (validityCheck()) {
			if (this.mapFilePath != null) {
				save(this.mapFilePath);
			} else {
				throw new IOException("No path specified");
			}
		} else {
			throw new Exception("Cannot pass the validation!");
		}
	}

	/**
	 * saving information of the new map, parsing new map file that if
	 * continents and territories are not null, search information of the new
	 * map, printing information of new map including features of author,
	 * continents and territories.
	 * 
	 * @param out
	 * @see sortContinentsCollection()
	 * @see sortTerritoriesCollection()
	 */
	private void save(PrintWriter out) {
		sortContinentsCollection();
		sortTerritoriesCollection();

		out.println("[Map]");
		out.println("image=" + getSaveImageFilePath());
		out.println("wrap=" + (this.wrap ? "yes" : "no"));
		out.println("scroll=" + (this.scroll == null ? "" : this.scroll.toString().toLowerCase()));
		if (this.author != null) {
			out.println("author=" + this.author);
		}
		out.println("warn=" + (this.warn ? "yes" : "no"));
		out.println();
		out.println("[Continents]");
		if (this.continents != null) {
			for (Continent cont : this.continents) {
				out.println(cont.getName() + "=" + cont.getBonus());
			}
		}
		out.println();
		out.println("[Territories]");
		if (this.territories != null) {
			Continent curCont = null;
			for (Territory ter : this.territories) {
				if (curCont == null) {
					curCont = ter.getContinent();
				} else if (curCont != ter.getContinent()) {
					curCont = ter.getCont();
					out.println();
				}
				out.print(ter.name);
				out.print(',');
				out.print(ter.getCenterX());
				out.print(',');
				out.print(ter.getCenterY());
				out.print(',');
				if (ter.getContinent() != null) {
					out.print(ter.getContinent().getName());
				} else {
					out.print("");
				}
				for (String linkName : ter.linkNames) {
					out.print(',');
					out.print(linkName);
				}
				out.println();
			}
		}
	}

	/**
	 * comfirming to save the new map
	 * 
	 * @param path
	 * @throws IOException
	 * @see validityCheck()
	 */
	public void save(String path) throws IOException {
		if (validityCheck()) {
			this.mapFilePath = path;

			File f = new File(path);
			FileOutputStream fos = new FileOutputStream(f);
			PrintWriter out = new PrintWriter(fos);
			save(out);
			if (out.checkError()) {
				throw new IOException("An error occurred while attempting to save the map file");
			}
		}
	}

	/**
	 * input the author of the edited map file, change the map information.
	 * 
	 * @param author
	 */
	public final void setAuthor(String author) {
		if (!StringUtil.equal(author, this.author)) {
			this.author = author;
			changeState();
		}
	}

	/**
	 * input the Continent name, it can not be null, and change the map
	 * information
	 * 
	 * @param cont
	 * @param name
	 */
	public void setContinentName(Continent cont, String name) {
		if ((name != null) && (name.length() > 0)) {
			cont.setName(name);
			changeState();

		}
	}

	/**
	 * save the image file in a path the user wants.
	 * 
	 * @param imageFilePath
	 */
	public void setImageFilePath(String imageFilePath) {
		if (!StringUtil.equal(this.imageFilePath, imageFilePath)) {
			this.imageFilePath = imageFilePath;
			changeState();

		}
	}

	/**
	 * save the map file path in a path the user wants
	 * 
	 * @param mapFilePath
	 */
	public void setMapFilePath(String mapFilePath) {
		this.mapFilePath = mapFilePath;
	}

	/**
	 * confirming if it is scroll.
	 * 
	 * @param scroll
	 */
	public final void setScroll(ScrollOptions scroll) {
		if (this.scroll != scroll) {
			this.scroll = scroll;
			changeState();

		}
	}

	/**
	 * confirming if it is warn.
	 * 
	 * @param warn
	 */
	public final void setWarn(boolean warn) {
		if (warn != this.warn) {
			this.warn = warn;
			changeState();

		}
	}

	/**
	 * confirming if it is warp.
	 * 
	 * @param wrap
	 */
	public final void setWrap(boolean wrap) {
		if (wrap != this.wrap) {
			this.wrap = wrap;
			changeState();

		}
	}

	/**
	 * sorting continents if continents list is not null or empty
	 */
	void sortContinentsCollection() {
		if ((this.continents != null) && (!this.continents.isEmpty())) {
			Collections.sort(this.continents, this);
		}
	}

	/**
	 * sorting the territories list if it is not null or empty
	 */
	void sortTerritoriesCollection() {
		if ((this.territories != null) && (!this.territories.isEmpty())) {
			Collections.sort(this.territories, this);
		}
	}

	/**
	 * check map parameters, if a territory has links. check if a map file has a
	 * matched image file. check if a map file and its matched image file are in
	 * the same path. check the territory link information. check if the
	 * territory list is not empty and it can not reach to other territories.
	 * 
	 * @return false if also the check failed
	 * @see eachTerReachable()
	 */
	public boolean validityCheck() {
		ArrayList<String> probs = loadingCheck();

		if (probs.isEmpty()) {
			return true;
		}

		for (String string : probs) {
			LogPanel.addLog(string);
		}

		return false;
	}

	/**
	 * check map parameters, if a territory has links. check if a map file has a
	 * matched image file. check if a map file and its matched image file are in
	 * the same path. check the territory link information. check if the
	 * territory list is not empty and it can not reach to other territories.
	 * 
	 * @return false if also the check failed
	 * @see eachTerReachable()
	 */
	public ArrayList<String> loadingCheck() {
		ArrayList<String> probs = new ArrayList<>();
		if ((this.territories == null) || (this.territories.isEmpty())) {
			probs.add("Map contains no territories");
		}

		if (this.territories.size() > 0 && !eachTerReachable()) {
			probs.add("There's some teris cannot reach to every other territories!");
		}

		for (Continent c : this.continents) {
			if (!eachTerInContReachable(c)) {
				probs.add(c.getName() + " inside the continent, not each ter can reach to others!");
			}
		}

		for (String string : probs) {
			LogPanel.addLog(string);
		}

		return probs;
	}

	/**
	 * method to check if a territory is board to others in the territories
	 * list.
	 * 
	 * @return true if they are boarded, else return false.
	 */
	public boolean eachTerReachable() {
		clearReach();
		Territory head = this.territories.get(0);
		DFS(head);
		int count = 0;
		for (Territory t : this.territories) {
			if (t.hasReached) {
				count++;
			}
		}
		clearReach();
		System.out.println(count + " countries can connect to each other ");
		System.out.println("With total of " + this.territories.size() + " countries!");
		if (count == this.territories.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to check if a territory is board to others in the target
	 * continent. Also see the function eachTerReachable().
	 * 
	 * @return true if they are boarded, else return false.
	 */
	public boolean eachTerInContReachable(Continent continent) {
		clearReach();
		List<Territory> ters = new ArrayList<>();
		for (Territory t : this.territories) {
			if (t.getContinent().equals(continent)) {
				ters.add(t);
			}
		}
		int count = 0;
		if (ters.size() > 0) {
			Territory head = ters.get(0);
			DFSForCont(head, continent);
			for (Territory t : this.territories) {
				if (t.hasReached) {
					count++;
				}
			}
		}
		clearReach();
		System.out.println(count + " countries can connect to each other ");
		System.out.println("Inside " + continent.getName() + " there're " + ters.size() + " countries!");
		if (count == ters.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * delete the linked relationships among territories.
	 */
	private void clearReach() {
		for (Territory t : this.territories) {
			t.hasReached = false;
		}
	}

	/**
	 * searching all the connections relationship among territories.
	 * 
	 * @param head
	 */
	private void DFS(Territory head) {
		head.hasReached = true;
		if (head.getLinks().size() == 0) {
			return;
		}
		for (Territory neighbour : head.getLinks()) {
			if (!neighbour.hasReached) {
				DFS(neighbour);
			}
		}
	}

	/**
	 * searching all the connections relationship among target continent.
	 * 
	 * @param head
	 */
	private void DFSForCont(Territory head, Continent continent) {
		head.hasReached = true;
		if (head.getLinks().size() == 0) {
			return;
		}
		for (Territory neighbour : head.getLinks()) {
			if (neighbour.getContinent().equals(continent) && !neighbour.hasReached) {
				DFSForCont(neighbour, continent);
			}
		}
	}

}
