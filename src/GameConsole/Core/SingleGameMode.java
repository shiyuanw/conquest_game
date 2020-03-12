package GameConsole.Core;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import GameConsole.Model.Player.Player;
import GameConsole.View.LogPanel;

/**
 * This class is for single game mode
 */
public class SingleGameMode extends SwingWorker<Boolean, Boolean> {

	private GameState gameState;
	private List<Player> players;
	private WindowMain win;
	private LogPanel lp = LogPanel.getInstance();
	private final int sleepTime = 300;

	/**
	 * Constructor for SingleGameMode class
	 * 
	 * @param gs
	 *            game state with GameState type
	 */
	public SingleGameMode(GameState gs) {
		this.gameState = gs;
		this.win = gs.getWindow();
		this.players = gs.getAllPlayers().getPlayers();
	}

	/**
	 * This method is mainly to display the detail of the game on the log panel
	 * 
	 * @return true if the game is end otherwise return false
	 * @throws Exception
	 */
	@Override
	protected Boolean doInBackground() throws Exception {
		Player tempP = gameState.getCurrPlayer();
		while (true) {
			System.out.println(tempP.getName() + " inits " + tempP.getInitTroop());
			updateLabel();
			Thread.sleep(sleepTime);
			lp.addLog("=====It's " + gameState.getCurrPlayer().getName() + "'s turn.=====");
			tempP.reinforce();

			gameState.setNextPlayer();
			gameState.updateCountryLabels();
			tempP = gameState.getCurrPlayer();

			updateLabel();

			boolean isFinished = true;
			for (Player p : players) {
				if (p.getInitTroop() > 0)
					isFinished = false;
			}
			if (isFinished)
				break;
		}

		gameState.setFirstRound(2);

		while (true) {
			Player currPlayer = gameState.getCurrPlayer();
			lp.addLog("=====It's " + currPlayer.getName() + "'s turn.=====");
			if (currPlayer.getStrategy().getName().equals("Human")) {
				currPlayer.setInitTroop(currPlayer.getBonus());
			} else {
				currPlayer.setInitTroop(currPlayer.getBonusAndChangeCard());
			}
			updateLabel();
			Thread.sleep(sleepTime);
			currPlayer.reinforce();

			gameState.updateCountryLabels();
			gameState.setNextPhase();
			clearLabel();

			System.out.println("attack");
			Thread.sleep(sleepTime);
			currPlayer.attack();

			gameState.updateCountryLabels();
			if (gameState.getCurrPhase() == 1) {
				gameState.setNextPhase();
			}
			clearLabel();

			if (gameState.getCurrPlayer().checkWinGame()) {
				win.initializeEndGame();
				return true;
			}

			System.out.println("fortify");
			Thread.sleep(sleepTime);
			currPlayer.fortify();

			gameState.updateCountryLabels();
			if (gameState.getCurrPhase() == 2) {
				gameState.setNextPhase();
			}
			clearLabel();

			List<Player> removeList = new ArrayList<>();
			for (Player p : players) {
				if (p.getCountries().size() == 0) {
					removeList.add(p);
				}
				System.out.println(p.getName() + ":" + p.getCountries().size());
			}
			this.players.removeAll(removeList);

			lp.addLog("\r\n" + "Conquest Number:");
			for (Player p : players) {
				lp.addLog(p.getName() + " : " + p.getCountries().size() + " countries!");

			}

			gameState.setNextPlayer();
		}

	}

	/**
	 * Method to set the labels clean
	 */
	public void clearLabel() {
		gameState.setCountry1(null);
		gameState.setCountry2(null);
		gameState.setCurrClick(null);
	}

	/**
	 * Method to update the labels
	 */
	public void updateLabel() {
		gameState.updateCountryLabels();
		win.country1.setText((String) null);
		win.country2.setText((String) null);
		win.troopsLeft = gameState.getCurrPlayer().getInitTroop();
		win.numberOfTroops.setText("" + win.troopsLeft);

	}

}
