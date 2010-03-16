import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import parser.AbsParser;
import parser.FullTiltParser;
import parser.PokerStarsParser;
import parser.data.Bet;
import parser.data.Game;
import parser.data.GameSet;
import parser.data.PlayerInfo;
import db.PokerLogsDBManager;

public class mainLogImporter {

	static PokerLogsDBManager dba = new PokerLogsDBManager();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long t0 = System.currentTimeMillis();

		// ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/Poker Archive/Poker Archive/FullTit/0811200703020313_FT20070721 Satellite to $500K Guarantee (22617550), Table 2 - 15-30 - No Limit Hold'em.txt", ".txt");
		// ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/Poker Archive/Poker Archive/FullTit/012720091317191_FT20090127 Rose (heads up) - $0.25-$0.50 - Pot Limit Omaha Hi.txt", ".txt");
		// ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/Poker Archive/Poker Archive/FullTit/010720080159392_FT20080107 Everest (6 max) - $0.50-$1 - Pot Limit Hold'em - Logged In As louick45.txt", ".txt");
		// ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/Poker Archive/Poker Archive/FullTit/052020082025158_FT20080520 Eminence (6 max) - $0.50-$1 - Pot Limit Hold'em.txt", ".txt");

		System.out.print("Building file list ... ");
		ArrayList<String> logPaths = PathBuilder.getlogPaths("C:/Users/BUNNI/Desktop/Poker Archive/Poker Archive/FullTit", ".txt");
		System.out.println("Done (" + logPaths.size() + " files in " + Utils.msToStringTime(System.currentTimeMillis() - t0) + ")");

		String fileContent = null;
		/*
		 * Integer nbGames = 0;
		 * Integer nbPokerStarGames = 0;
		 * Integer nbFullTiltGames = 0;
		 * Integer wtf = 0;
		 */
		int i = 0;
		for (String s : logPaths) {
			if ((++i % 200) == 0) {
				System.out.println("####################### " + i + "/" + logPaths.size() + " #######################");
			}

			// Read the log file
			try {
				fileContent = Utils.readFileAsString(s);
				/*
				 * String[] gameLogs = null;
				 * 
				 * gameLogs = fileContent.split("\r\n\r\n\r\n");
				 * 
				 * for (String log : gameLogs) { nbGames++; if (fileContent.startsWith("PokerStars")) { nbPokerStarGames++; } else if (fileContent.startsWith("Full")) { nbFullTiltGames++; } else { wtf++; System.out.println("Path: " + s); }
				 * 
				 * }
				 */
			} catch (IOException e) {
				System.out.println("Can't read file at: " + s);
			}

			// Read the header & spawn parser accordingly
			AbsParser logParser = null;
			String domain = null;
			if (fileContent.startsWith("PokerStars")) {
				logParser = new PokerStarsParser(fileContent);
			} else if (fileContent.startsWith("Full Tilt Poker") || fileContent.startsWith("FullTiltPoker")) {
				// System.out.println(s);
				logParser = new FullTiltParser(fileContent);
				domain = "Full Tilt";

			} else {
				System.out.println("Unknown log type at" + s);
			}

			// Insert the parsed data into the database
			if (logParser.getGameSet().size() != 0) {
				createInserts(logParser.getGameSet(), domain, s.substring(s.lastIndexOf("\\")));
			}

		}
		System.out.println("####################### " + i + "/" + logPaths.size() + " #######################");

		// System.out.println("You has " + nbGames + " games in your poker archive");
		// System.out.println(nbPokerStarGames + " of then are from pokerStars");
		// System.out.println(nbFullTiltGames + " of then are from Full Tilt modafucka");
		// System.out.println(wtf + " of then are i don't know the fuck where");
		// System.out.println(nbGames - (nbPokerStarGames + nbFullTiltGames + wtf) + "games are unaccounted for");

		dba.doCommit();
		System.out.println("\n\nNbGameSet : " + logPaths.size());
		System.out.println("Processed in " + Utils.msToStringTime(System.currentTimeMillis() - t0));

	}

	private static void createInserts(GameSet gameSet, String domain, String path) {
		HashMap<String, Integer> players = new HashMap<String, Integer>();
		Integer gameSetID = null;

		gameSetID = dba.addGameSet(gameSet.bigBlindValue, gameSet.smallBlindValue, gameSet.betType.name(), gameSet.gameType.name(), gameSet.pokerType.name(), path);

		for (Game game : gameSet) {
			int seq = 0;

			for (Entry<String, PlayerInfo> e : game.playerMap.entrySet()) {
				players.put(e.getKey(), dba.getPlayerID(e.getKey()));
			}

			Integer gameID = null;
			try {
				gameID = dba.addGame(gameSetID, players.get(game.dealerName.trim()), game.table[0], game.table[1], game.table[2], game.table[3], game.table[4]);
			} catch (Exception e) {
				System.out.println(game.dealerName);
				System.exit(0);
			}

			for (Entry<String, PlayerInfo> e : game.playerMap.entrySet()) {
				if (e.getValue().amountWon != 0) {
					dba.addWinner(gameID, gameSetID, players.get(e.getKey()), e.getValue().amountWon);
				}
				dba.addSeats(gameID, gameSetID, players.get(e.getKey()), e.getValue().seatNo, true, e.getValue().chips);
				if (e.getValue().cards[0] != null) {
					dba.addDealtCards(gameID, gameSetID, players.get(e.getKey()), e.getValue().cards[0], e.getValue().cards[1]);
				}
			}

			for (Bet bet : game) {
				if (!bet.forced) {
					dba.addBet(gameID, gameSetID, players.get(bet.playerName.trim()), bet.round.name(), ++seq, bet.action.name(), bet.amount);
				} else {
					dba.addForcedBets(gameID, gameSetID, players.get(bet.playerName.trim()), ++seq, bet.forceType.name(), bet.amount);
				}

			}

		}

	}
}
