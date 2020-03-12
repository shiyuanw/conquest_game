package GameConsole.Core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import GameConsole.Model.Domain.Card;
import GameConsole.Model.Domain.Continent;
import GameConsole.Model.Domain.Country;
import GameConsole.Model.Player.Player;

/**
 * This class is used to save the game if user wants to save the current session
 */
public class GameSaver {
	private GameState game;
	private WindowMain win;
	private boolean flag = true; // this flag is for test purpose

	/**
	 * Constructor for GameSaver with incoming parameters
	 * 
	 * @param game
	 *            Game state the current game
	 * @param win
	 *            Window frame of the game
	 */
	public GameSaver(GameState game, WindowMain win) {
		this.game = game;
		this.win = win;
	}

	/**
	 * Write all the information of the current game into output stream
	 * 
	 * @param out
	 *            output stream
	 */
	private void save(PrintWriter out) {
		out.println("[Map]");
		out.println("mapFilePath=" + game.getWorld().getMapLoader().getMapFilePath());
		out.println();

		out.println("[GameState]");
		out.println("firstRound=" + game.getFirstRound());
		out.println("currPlayer=" + game.getCurrPlayer().getName());
		out.println("currPhase=" + game.getCurrPhase());
		out.println("playerNum=" + game.getAllPlayers().getPlayers().size());
		if (flag == true) {
			out.println("troopRemaining=" + win.troopsLeft);
		}

		out.println();
		out.println("[Players]");
		for (int index = 0; index < game.getAllPlayers().getPlayers().size(); index++) {
			Player p = game.getAllPlayers().getPlayers().get(index);
			out.println("Player" + (index + 1));
			out.println("name=" + p.getName());
			out.println("isConquered=" + p.isConquered());
			out.println("hasMoved=" + p.isHasMoved());
			out.println("initTroop=" + p.getInitTroop());
			out.println("strategy=" + p.getStrategy().getName());

			StringBuffer sb = new StringBuffer(100);
			for (Card c : p.getOnHand()) {
				sb.append(c.getType()).append(" ");
			}
			if (sb.length() > 1)
				sb.deleteCharAt(sb.length() - 1);

			out.println("onhand=" + sb);

			sb = new StringBuffer(100);
			for (Country c : p.getCountries()) {
				sb.append(c.getName()).append(",");
			}
			if (sb.length() > 1)
				sb.deleteCharAt(sb.length() - 1);
			out.println("countries=" + sb);
			out.println();
		}

		out.println();
		out.println("[Countries]");
		Set<Country> countries = new HashSet<>();
		for (Continent c : game.getWorld().getContinents()) {
			countries.addAll(c.getCountries());
		}
		if (flag == true) {
			for (Country c : countries) {
				out.println(c.getName() + "," + c.getPlayer().getName() + "," + c.getTroopNum());
			}
		}

		out.close();
	}

	/**
	 * Save the game information into file
	 * 
	 * @param path
	 *            file path
	 * @throws IOException
	 * @see validityCheck()
	 */
	public void save(String path) throws IOException {
		File f = new File(path);
		FileOutputStream fos = new FileOutputStream(f);
		PrintWriter out = new PrintWriter(fos);
		save(out);
		fos.close();
	}

	/**
	 * Only use for test
	 * 
	 * @param _flag
	 *            Should be always true in the game
	 * 
	 */
	public void setFlagForTest(boolean _flag) {
		this.flag = _flag;
	}

}
