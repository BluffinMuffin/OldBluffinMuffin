package parser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.data.Bet;
import parser.data.Game;
import parser.data.GameSet;
import db.PokerLogsDBManager;

public class FullTiltParser extends AbsParser {

	private static final FullTiltParser INSTANCE = new FullTiltParser();

	public static FullTiltParser getInstance() {
		return INSTANCE;

	}

	private PokerLogsDBManager DBManager;

	private HashMap<String, Integer> playerMap = new HashMap<String, Integer>();
	private GameSet gameSet = new GameSet();

	// To be initialised at the start of each individual game
	private Game game = null;

	@Override
	void understand(String fileContent) {

		DBManager = new PokerLogsDBManager();

		String[] gameLogs = null;
		String[] rounds = null;

		gameLogs = fileContent.split("\r\n\r\n\r\n");

		for (String log : gameLogs) {

			rounds = log.split("\r\n\\*\\*\\*.");

			game = new Game(); // Instance a new game

			for (int x = 0; x < rounds.length; x++) {

				if (rounds[x].length() > 0) {
					if (x == 0) { // Si on est dans la section info générales de la game

						setPlayers(rounds[x]);
						readHeader((rounds[x].split("\r\n"))[0]);

						setDealerID(rounds[x]);
						setBlindBettingRound(rounds[x]); // Big blind Broken

					} else if (x == rounds.length - 1) {
						// Game Summary

						// Déterminer le gagnant et le montant amassé
						// Remplir la table avec le "Board : "

						// System.exit(-1);

					} else {
						// is typical betting round
						insertBettingActions(rounds[x]);
					}

				}
			}
		}

	}

	private void insertBettingActions(String bettingInfo) {

		String[] actions = (bettingInfo.split("\r\n"));
		String round = getBettingRoundType(actions[0]);

		// TODO: Replace by megaMatcher of DOOOMM!!
		/*
		 * Pattern fold = Pattern.compile("([\\w+\\s]+) folds");
		 * Pattern raise = Pattern.compile("([\\w+\\s]+)raises to \\$?(\\d+[.]?[0-9]?{2})");
		 * Pattern call = Pattern.compile("([\\w+\\s]+) calls \\$?(\\d+[.]?[0-9]?{2})");
		 * Pattern check = Pattern.compile("");
		 * Pattern voluntaryBet = Pattern.compile("([\\w+\\s]+)adds \\$?(\\d+[.]?[0-9]?{2})");
		 */

		Pattern cardsDealt = Pattern.compile("Dealt to ([\\w+\\s]+)\\x5B(..) (..)\\x5D");
		Pattern megaPattern = Pattern.compile("([\\w+\\s]+)(folds|raises|calls|adds|checks) ?(to )?\\$?(\\d+)?");

		// Can wait until summary when we have Board [.. .. .. .. ..]
		// Pattern tableCards = Pattern.compile("([\\w+\\s]+)\\*\\*\\* \\x5B(..) ?(..)? ?(..)?\\x5D");
		// Matcher tableMatcher = tableCards.matcher(actions[0]);
		//
		// if (!round.equals("HOLE CARDS")) {
		// // FLOP / TURN /RIVER
		// // Get the turned cards and add to game.table
		// if (tableMatcher.find()) {
		// if (tableMatcher.group(1).equals("FLOP")) {
		// game.table[0] = tableMatcher.group(2);
		// game.table[1] = tableMatcher.group(3);
		// game.table[2] = tableMatcher.group(4);
		//
		// } else if (tableMatcher.group(1).equals("TURN")) {
		// game.table[3] = tableMatcher.group(2);
		//
		// } else if (tableMatcher.group(1).equals("RIVER")) {
		// game.table[4] = tableMatcher.group(2);
		// }
		//
		// }
		//
		// }

		for (int x = 1; x < actions.length; x++) {
			Matcher cardsDealtMatcher = cardsDealt.matcher(actions[x]);
			Matcher megaMatcher = megaPattern.matcher(actions[x]);

			if (cardsDealtMatcher.find()) {
				// Traiter la ligne
				game.playerCards.put(cardsDealtMatcher.group(1), new String[] { cardsDealtMatcher.group(2), cardsDealtMatcher.group(3) });
			}

			if (megaMatcher.find()) {
				if (megaMatcher.group(2).equals("raises")) {
					Bet b = new Bet();
					b.round = round;
					b.playerName = megaMatcher.group(1);
					b.amount = Double.valueOf(megaMatcher.group(4));
					b.action = Bet.ActionType.Raise;
					game.add(b);

				} else if (megaMatcher.group(2).equals("calls")) {
					Bet b = new Bet();
					b.round = round;
					b.playerName = megaMatcher.group(1);
					b.amount = Double.valueOf(megaMatcher.group(4));
					b.action = Bet.ActionType.Call;
					game.add(b);

				} else if (megaMatcher.group(2).equals("folds")) {
					Bet b = new Bet();
					b.round = round;
					b.playerName = megaMatcher.group(1);
					b.action = Bet.ActionType.Fold;
					game.add(b);

					// } else if (megaMatcher.group(2).equals("adds")) { // Not too sure about this one
					// Bet b = new Bet();
					// b.round = round;
					// b.forced = true;
					// b.forceType = Bet.ForcedBetType.Post;
					// b.playerName = megaMatcher.group(1);
					// game.add(b);
					//
				} else if (megaMatcher.group(2).equals("checks")) {
					Bet b = new Bet();
					b.round = round;
					b.playerName = megaMatcher.group(1);
					b.action = Bet.ActionType.Check;
					game.add(b);

				}

			}

		}

	}

	private void setDealerID(String info) {

		/*
		 * TODO: Decide how to correspond seat # to player name
		 */
		// ResultSet rs = dbConnector.query("INSERT INTO Game (idDealer) values ("+ );

		Pattern p = Pattern.compile("The button is in seat #(\\d)");
		Matcher RegexMatcher = p.matcher(info);

		if (RegexMatcher.find()) {

			game.dealerName = gameSet.seats[Integer.valueOf(RegexMatcher.group(1)) - 1];
			System.out.println("Seat # " + RegexMatcher.group(1) + " is the dealer (" + game.dealerName + ")");
		}

	}

	private void readHeader(String header) {

		// group(1) : Domain
		// group(2) : idGame
		// group(3) : unimportant tournament data
		// group(4): tableName
		// group(5): max players on table optional
		// group(6): small blind
		// group(7): big blind
		// group(8): Bet Type
		// group(9): PokerType
		// group(10): time
		// group(11): timezone
		// group(12): date

		String tableInfo = "([\\w+\\s]+)Game.#(\\d+):(..+,)?.Table.(\\w+\\s)+([\\x28]\\d max[\\x29])? ?- ";
		String bettype = "([\\w+\\s]+)(Hold'em|Omaha) - ";
		String timestmp = "([0-9]{0,2}:[0-9]{0,2}:[0-9]{0,2}) (\\w+) - ([0-9]{0,4}\\/[0-9]{0,2}\\/[0-9]{0,2})";

		String sbbb = "\\$?(\\d+[.]?[0-9]?{2})\\/\\$?(\\d+[.]?[0-9]?{2}).-.";

		Pattern p = Pattern.compile(tableInfo + sbbb + bettype + timestmp, Pattern.CASE_INSENSITIVE);
		Matcher RegexMatcher = p.matcher(header);

		if (RegexMatcher.find()) {
			System.out.println("FILEHEADER: " + RegexMatcher.group(0));
			System.out.println("1: " + RegexMatcher.group(1));
			System.out.println("2: " + RegexMatcher.group(2));
			System.out.println("3: " + RegexMatcher.group(3));
			System.out.println("4: " + RegexMatcher.group(4));
			System.out.println("5: " + RegexMatcher.group(5));

			System.out.println("6: " + RegexMatcher.group(6));
			gameSet.smallBlindValue = Double.valueOf(RegexMatcher.group(6));

			System.out.println("7: " + RegexMatcher.group(7));
			gameSet.bigBlindValue = Double.valueOf(RegexMatcher.group(6));

			System.out.println("8: " + RegexMatcher.group(8));
			System.out.println("9: " + RegexMatcher.group(9));
			System.out.println("10: " + RegexMatcher.group(10));
			System.out.println("11: " + RegexMatcher.group(11));
			System.out.println("11: " + RegexMatcher.group(12));

		}

	}

	private void setPlayers(String seatInfo) {

		Integer nbPlayers = 0;

		Pattern myregex = Pattern.compile("Seat \\d: ([\\w+\\s]+)[\\x28]\\$?(.+)[\\x29]");

		Matcher matcher = myregex.matcher(seatInfo);

		while (matcher.find()) {
			nbPlayers++;

			System.out.println("Player: " + matcher.group(1));
			System.out.println("Monies: " + matcher.group(2));

			// Obtenir les id Player des joueurs et ajouter comme value du hashmap
			String playerName = matcher.group(1).trim();

			playerMap.put(playerName, DBManager.getPlayerID(playerName));
			gameSet.seats[nbPlayers - 1] = playerName;

		}

	}

	private void setBlindBettingRound(String info) {

		/*
		 * TODO: Get corresponding ID's from playerMap once bd is up and running
		 */

		Pattern p = Pattern.compile("\r\n([\\w+\\s]+)posts the small blind of \\$?(\\d+[.]?[0-9]?{2})");
		Matcher RegexMatcher = p.matcher(info);

		if (RegexMatcher.find()) {
			System.out.println("id smallBlind: " + RegexMatcher.group(1));
			Bet b = new Bet();
			b.forced = true;
			b.forceType = Bet.ForcedBetType.Blind;
			b.playerName = RegexMatcher.group(1);
			b.amount = Double.valueOf(RegexMatcher.group(2));
			game.add(b);

		}

		Pattern m = Pattern.compile("\r\n([\\w+\\s]+)posts the big blind of \\$?(\\d+[.]?[0-9]?{2})");
		Matcher RegexMatcher2 = m.matcher(info);

		if (RegexMatcher2.find()) {
			System.out.println("id bigBlind: " + RegexMatcher2.group(1));

		}

		// INSERT INTO bettingRounds(idGame, idGameSet, idPlayer, round, seq, amountRaised) VALUES (idGame, idGameset, idSmallBlind, 'BLIND', 1, 0)
		// INSERT INTO bettingRounds(idGame, idGameSet, idPlayer, round, seq, amountRaised) VALUES (idGame, idGameset, idBigBlind, 'BLIND', 1, 0)

	}

	private String getBettingRoundType(String firstLine) {
		String roundType = null;

		Pattern p = Pattern.compile("([\\w+\\s]+)\\*\\*\\*");
		Matcher RegexMatcher = p.matcher(firstLine);

		if (RegexMatcher.find()) {
			System.out.println("We are in round " + RegexMatcher.group(1));
			roundType = RegexMatcher.group(1);

		}

		return roundType;

	}

}
