package GameConsole.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import GameConsole.Core.GameLoader;
import GameConsole.Core.GameState;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;
import GameConsole.View.LogPanel;

/**
 * A concrete Strategy that implements Aggressive strategy operation
 */
public class AggressiveStrategy extends OriginalStrategy implements Strategy {
	private LogPanel lp = LogPanel.getInstance();
	/**
	 * Constructor for AggressiveStrategy
	 */
	public AggressiveStrategy() {
		super();
		this.setName("Aggressive");
	}

	/**
	 * Method to attack.
	 */
	@Override
	public void attack() {
		for (;;) {
			if (!getPlayer().checkIfCanAttack()) {
				return;
			}
			Country strongestC = this.getStrongestContry();
			int decision1 = strongestC.getTroopNum() >= 3 ? 3 : strongestC.getTroopNum();
			int decision2 = 1;
			Country country1 = strongestC;
			Country country2 = new Country();
			for (Country neighbour : strongestC.getBorderingCountries()) {
				if (neighbour.getPlayer() != this.getPlayer()) {
					country2 = neighbour;
					break;
				}
			}
			if (country2.getPlayer() != null && country2.getPlayer() != getPlayer()) {
				try {
					Map<String, Object> result = getGameState().getCurrPlayer().originalAttack(country1, country2,
							decision1, decision2);
					boolean flag = (boolean) result.get("result");
					if (flag) {
						int moveNum = strongestC.getTroopNum() - 1;
						country2.addInfrantry(moveNum);
						country1.removeTroops(moveNum);
						lp.addLog(getPlayer().getName() + " moves " + moveNum + " troops from " + country1.getName() + " to " + country2.getName() + "!");
					}
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Method to reinforce.
	 */
	@Override
	public void reinforce() {
		if (getPlayer().getInitTroop() > 0) {
			if (getGameState().getFirstRound() == 1) {
				this.getPlayer().addInfantry(this.getStrongestContry());
				this.getPlayer().setInitTroop(getPlayer().getInitTroop() - 1);
			} else {
				int num = getPlayer().getBonusAndChangeCard();
				for (int i = 0; i < num; i++) {
					this.getPlayer().addInfantry(this.getStrongestContry());
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
		Country strongestC = getStrongestContry();
		if (strongestC != null) {
			for (Country neighbour : strongestC.getBorderingCountries()) {
				if (neighbour.getPlayer() == this.getPlayer() && neighbour.getTroopNum() > 1) {
					getPlayer().moveTroops(neighbour, strongestC, neighbour.getTroopNum() - 1);
					return;
				}
			}
		}
	}

	/**
	 * Method to get its strongest country
	 * 
	 * @return the country that got which is Country type
	 */
	private Country getStrongestContry() {
		List<Country> countrys = this.getPlayer().getCountries();
		List<Country> canAttackCountries = new ArrayList<>();
		for (Country c : countrys) {
			boolean flag = false;
			for (Country neighbour : c.getBorderingCountries()) {
				if (neighbour.getPlayer() != this.getPlayer())
					flag = true;
			}
			if (flag) {
				canAttackCountries.add(c);
			}
		}

		if (canAttackCountries.isEmpty())
			return null;
		Country strongestCountry = canAttackCountries.get(0);
		for (Country country : canAttackCountries) {
			if (country.getTroopNum() > strongestCountry.getTroopNum()) {
				strongestCountry = country;
			}
		}
		return strongestCountry;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		GameLoader gl = new GameLoader(null, "C:\\Users\\Liang\\Documents\\13.txt");
		GameState gs = gl.getGameState();
		Player p1 = gs.getAllPlayers().getPlayers().get(0);
		AggressiveStrategy s1 = new AggressiveStrategy();
		s1.setGameState(gs);
		s1.setPlayer(p1);

		p1.setStrategy(s1);

		for (Country c : p1.getCountries()) {
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}
		System.out.println("======");

		p1.reinforce();

		for (Country c : p1.getCountries()) {
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}
		System.out.println("======");

		p1.fortify();
		for (Country c : p1.getCountries()) {
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}
		System.out.println("======");

		p1.attack();

		for (Country c : p1.getCountries()) {
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}
		System.out.println("======");

		p1.reinforce();

		for (Country c : p1.getCountries()) {
			System.out.println(c.getName() + ": " + c.getTroopNum());
		}
		System.out.println("======");
	}

}
