package GameConsole.View;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameConsole.Core.MapLoader;
import GameConsole.Core.World;
import GameConsole.Model.Domain.Country;

/**
 * The main entrance of the game
 *
 */
public class MapDisplayer {
	private JPanel frame;
	private ArrayList<CountryButton> buttons = new ArrayList<>();
	private World world;
	private BufferedImage buttonImage;

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public MapDisplayer(JPanel map, World world) throws IOException {
		initialize(map, world);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize(JPanel map, World world) throws IOException {
		frame = new JPanel();
		frame.setBackground(Color.lightGray);
		frame.setLayout(null);
		map.add(frame);
		MapLoader mapLoader = world.getMapLoader();
		this.world = world;
		int countryNum = mapLoader.countries.size();
		buttonImage = ImageIO.read(new File(mapLoader.getImageFilePath()));
		JLabel mapLoc = new JLabel();
		mapLoc.setIcon(new ImageIcon(buttonImage));
		mapLoc.setBounds(0, 0, buttonImage.getWidth(), buttonImage.getHeight());

		frame.setBounds(100, 150, buttonImage.getWidth(), buttonImage.getHeight());

		for (int i = 0; i < countryNum; i++) {
			String buttonName = mapLoader.countries.get(i).getName();
			Country country = mapLoader.countries.get(i);
			CountryButton countryButton = new CountryButton(buttonImage, buttonName, country);
			int x = country.getXLoc();
			int y = country.getYLoc();
			countryButton.setDoubleBounds(x - 20 - 3, y - 12, 50, 20);
			frame.add(countryButton.getLabel());
			frame.add(countryButton.b);
			buttons.add(countryButton);
			country.setButton(countryButton);
		}
		frame.add(mapLoc);
	}

	/**
	 * To get the frame
	 * 
	 * @return the frame with JPanel type
	 */
	public JPanel getFrame() {
		return frame;
	}

	/**
	 * To get the button list
	 * 
	 * @return the button list with ArrayList type
	 */
	public ArrayList<CountryButton> getButtons() {
		return buttons;
	}

	/**
	 * To get the world where the players are in
	 * 
	 * @return the world where the players are in with World type
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * To set the world
	 * 
	 * @param world
	 *            the selected world that will be set as the world where the
	 *            players are in
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * Return the current button image
	 * 
	 * @return The current button image
	 */
	public BufferedImage getButtonImage() {
		return buttonImage;
	}

	/**
	 * To set the button image
	 * 
	 * @param buttonImage
	 *            The input button image
	 */
	public void setButtonImage(BufferedImage buttonImage) {
		this.buttonImage = buttonImage;
	}

}
