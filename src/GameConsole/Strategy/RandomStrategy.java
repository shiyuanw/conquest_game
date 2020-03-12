package GameConsole.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import GameConsole.Core.GameLoader;
import GameConsole.Core.GameState;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;

/**
 * A concrete Strategy that implements random strategy operation
 */
public class RandomStrategy extends OriginalStrategy implements Strategy {

	/**
	 * Constructor for RandomStrategy
	 */
	public RandomStrategy() {
		super();
		this.setName("Random");
	}

	/**
	 * Method to attack.
	 */
	@Override
	public void attack() {
		int randomAttackTime = (int) (Math.random() * 150);
		int erroTry = 1000;
		for (;;) {
			if (!getPlayer().checkIfCanAttack() || randomAttackTime == 0 || erroTry == 0)
				return;
			Country country1 = getCanAttackRandomCountry();
			Country country2 = new Country();
			for (Country neighbour : country1.getBorderingCountries()) {
				if (neighbour.getPlayer() != this.getPlayer()) {
					country2 = neighbour;
					break;
				}
			}

			if (country1.getTroopNum() <= 1 || country2.getPlayer() == null) {
				erroTry--;
				continue;
			}

			int decision1 = country1.getTroopNum() >= 3 ? 3 : country1.getTroopNum();
			int decision2 = 1;

			try {
				Map<String, Object> result = getGameState().getCurrPlayer().originalAttack(country1, country2,
						decision1, decision2);

				boolean flag = (boolean) result.get("result");
				System.out.println("!!!onetimeattack");
				if (flag) {
					int moveNum = getRandTroops(country1.getTroopNum());
					country2.addInfrantry(moveNum);
					country1.removeTroops(moveNum);
				}
				randomAttackTime--;
			} catch (Exception e) {

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
				this.getPlayer().addInfantry(this.getRandCountry());
				this.getPlayer().setInitTroop(getPlayer().getInitTroop() - 1);
			} else {

				int num = getPlayer().getBonusAndChangeCard();
				for (int i = 0; i < num; i++) {
					if (getCanAttackRandomCountry() != null)
						this.getPlayer().addInfantry(this.getCanAttackRandomCountry());
					else
						this.getPlayer().addInfantry(this.getRandCountry());
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
		int erroTry = 200;
		for (;;) {
			if (erroTry == 0)
				return;
			Country country1 = getRandCountry();
			Country country2 = new Country();
			for (Country neighbour : country1.getBorderingCountries()) {
				if (neighbour.getPlayer() == this.getPlayer() && neighbour.getTroopNum() >= 2) {
					country2 = neighbour;
					break;
				}
			}
			if (country2.getPlayer() == null) {
				erroTry--;
				continue;
			}
			int moveTroopNum = getRandTroops(country2.getTroopNum());
			getPlayer().moveTroops(country2, country1, moveTroopNum);
			return;

		}
	}

	/**
	 * Method to get country in random mode
	 * 
	 * @return the country that got which is Country type
	 */
	private Country getRandCountry() {
		List<Country> countrys = this.getPlayer().getCountries();
		int rand = (int) (Math.random() * countrys.size());
		return countrys.get(rand);
	}

	/**
	 * Method to get country which can be attacked in random mode
	 * 
	 * @return the country that got which is Country type
	 */
	private Country getCanAttackRandomCountry() {
		List<Country> countrys = this.getPlayer().getCountries();
		List<Country> canAttack = new ArrayList<>();
		for (Country c : countrys) {
			if (c.getTroopNum() <= 1)
				continue;
			for (Country neighbour : c.getBorderingCountries()) {
				if (neighbour.getPlayer() != this.getPlayer()) {
					canAttack.add(c);
				}
			}
		}
		if (canAttack.size() == 0)
			return null;
		int rand = (int) (Math.random() * canAttack.size());
		return canAttack.get(rand);
	}

	/**
	 * Method to get the number of troop in randomly
	 * 
	 * @param num
	 *            the total number of troop the country owns
	 * @return the random number of the troop which is no more than the total
	 *         number of troop the country owns
	 */
	private int getRandTroops(int num) {
		if (num == 1)
			return 0;
		else
			return ((int) (Math.random() * (num - 1))) + 1;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		GameLoader gl = new GameLoader(null, "C:\\Users\\Liang\\Documents\\13.txt");
		GameState gs = gl.getGameState();
		Player p1 = gs.getAllPlayers().getPlayers().get(0);
		RandomStrategy s1 = new RandomStrategy();
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
