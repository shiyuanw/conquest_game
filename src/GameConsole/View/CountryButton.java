package GameConsole.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;

/**
 * This class is attach a button to each country
 */
public class CountryButton {
	public String name;
	public JButton b;
	public Country country;
	private JLabel label;

	/**
	 * Constructor method with incoming parameters
	 * 
	 * @param i
	 *            the image with BufferedImage type
	 * @param name
	 *            the button name with String type
	 * @param c
	 *            the country will be attached with the button with Country type
	 */
	public CountryButton(BufferedImage i, String name, Country c) {
		this.country = c;
		c.setButton(this);
		this.name = name;

		this.label = new JLabel(1 + "");
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.label.setForeground(Color.RED);
		this.label.setFont(new Font("Dialog", Font.PLAIN, 20));

		this.b = new JButton() {
			private static final long serialVersionUID = 1L;
		};
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
	}

	/**
	 * To set the bounds of the button and label
	 * 
	 * @param x
	 *            x coordinate location with int type
	 * @param y
	 *            y coordinate location with int type
	 * @param w
	 *            the width with int type
	 * @param h
	 *            the height with int type
	 */
	public void setDoubleBounds(int x, int y, int w, int h) {
		this.b.setBounds(x, y, w, h);
		this.label.setBounds(x, y, w, h);
	}

	/**
	 * Reset the values of bound
	 * 
	 * @param x
	 *            x coordinate location with int type
	 * @param y
	 *            y coordinate location with int type
	 * @param w
	 *            the width with int type
	 * @param h
	 *            the height with int type
	 */
	public void fixLabelBounds(int x, int y, int w, int h) {
		this.label.setBounds(x, y, w, h);
	}

	/**
	 * To get the label
	 * 
	 * @return the label with JLabel type
	 */
	public JLabel getLabel() {
		return this.label;
	}

	/**
	 * Method to update the troop size on the label
	 * 
	 * @param p
	 *            the player who owns the corresponding country of the button
	 *            with Player type
	 */
	public void updateLabel(Player p) {
		this.label.setText("" + this.country.getTroopNum());
		this.label.setForeground(p.getColor());
	}

}