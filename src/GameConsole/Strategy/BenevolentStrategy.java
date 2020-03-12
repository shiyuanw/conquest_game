package GameConsole.Strategy;

import java.util.List;

import GameConsole.Model.Domain.Country;
import GameConsole.View.LogPanel;

/**
 * A concrete Strategy that implements benevolent strategy operation
 */
public class BenevolentStrategy extends OriginalStrategy implements Strategy {
	/**
	 * Constructor for BenevolentStrategy
	 */
	public BenevolentStrategy() {
		super();
		this.setName("Benevolent");
	}

	/**
	 * Method to attack.
	 */
	@Override
	public void attack() {
	}

	/**
	 * Method to reinforce.
	 */
	@Override
	public void reinforce() {
		if (getPlayer().getInitTroop() > 0) {
			if (getGameState().getFirstRound() == 1) {
				this.getPlayer().addInfantry(this.getWeakestCountry());
				this.getPlayer().setInitTroop(getPlayer().getInitTroop() - 1);
			} else {

				int num = getPlayer().getBonusAndChangeCard();
				for (int i = 0; i < num; i++) {
					this.getPlayer().addInfantry(this.getWeakestCountry());
					this.getPlayer().setInitTroop(getPlayer().getInitTroop() - 1);
				}
			}
		}
	}

	/**
	 * Method to fortify
	 */
	@Override
	public void fortify() {
		getPlayer().giveCards();
		Country country1 = getWeakestCountry();
		Country country2 = new Country();
		for (Country neighbour : country1.getBorderingCountries()) {
			if (neighbour.getPlayer() == this.getPlayer()) {
				country2 = neighbour;
				break;
			}
		}

		if (country2.getPlayer() == null)
			return;

		int minNum = country1.getTroopNum();
		int maxNum = country2.getTroopNum();

		int moveTroopNum = (maxNum - minNum) / 2;
		if (moveTroopNum > 0)
			getPlayer().moveTroops(country2, country1, moveTroopNum);

	}

	/**
	 * Method to get its weakest country
	 * 
	 * @return the country that got which is Country type
	 */
	private Country getWeakestCountry() {
		List<Country> countrys = this.getPlayer().getCountries();
		Country weakestCountry = countrys.get(0);
		for (Country country : countrys) {
			if (country.getTroopNum() < weakestCountry.getTroopNum()) {
				weakestCountry = country;
			}
		}
		return weakestCountry;
	}

}
