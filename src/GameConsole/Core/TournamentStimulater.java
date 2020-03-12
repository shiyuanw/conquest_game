package GameConsole.Core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;
import GameConsole.Strategy.AggressiveStrategy;
import GameConsole.Strategy.BenevolentStrategy;
import GameConsole.Strategy.RandomStrategy;
import GameConsole.View.LogPanel;

/**
 * This class is for tournament game mode
 */
public class TournamentStimulater {
	private GameState gs;
	private int turn;
	private List<Player> players;

	/**
	 * Constructor of the TournamentStimulater class with coming parameters
	 * @param gs the game state with GameState type
	 * @param players the list of players in the game
	 * @param turn the current turn with int type
	 * @param flag1 if the game start with a UI
	 * @param flag2 if the gamestate needs to be initilized
	 */
	public TournamentStimulater(GameState gs, List<Player> players, int turn, boolean flag1, boolean flag2) {
		this.gs = gs;
		gs.getAllPlayers().setPlayers(players);
		gs.setCurrPlayer(players.get(0));
		this.players = players;
		this.turn = turn;
		if(flag2){
			gs.gameStart(flag1);
		}
	}

	/**
	 * This method is mainly to display the details of the game on the console
	 * @return "draw" String after the turns
	 */
	public String execute() {
		for(Player p: gs.getAllPlayers().getPlayers()){
			p.setInitTroop(p.getBonusAndChangeCard());
		}
		Player tempP = gs.getCurrPlayer();
		while(true){
			tempP.reinforce();
			gs.setNextPlayer();
			tempP = gs.getCurrPlayer();
			boolean isFinished = true;
			for (Player p : players) {
				if (p.getInitTroop() > 0)
					isFinished = false;
			}
			if (isFinished)
				break;
		}

		gs.setFirstRound(2);

		while (gs.getFirstRound() <= turn) {
			Player currPlayer = gs.getCurrPlayer();
			currPlayer.setInitTroop(currPlayer.getBonusAndChangeCard());
			currPlayer.reinforce();
			currPlayer.attack();
			currPlayer.fortify();

			List<Player> removeList = new ArrayList<>();
			for (Player p : players) {
				if (p.getCountries().size() == 0) {
					removeList.add(p);
				}
			}
			this.players.removeAll(removeList);


			if (players.size() == 1) {
				return players.get(0).getStrategy().getName();
			}

			if (currPlayer == players.get(players.size() - 1)) {
				gs.setCurrPlayer(players.get(0));
				gs.setFirstRound(gs.getFirstRound() + 1);
			} else {
				gs.setCurrPlayer(players.get(players.indexOf(currPlayer) + 1));
			}

		}

		return "draw";
	}


}
