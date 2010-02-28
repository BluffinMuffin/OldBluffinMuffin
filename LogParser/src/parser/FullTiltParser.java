package parser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.PokerLogsDBManager;

public class FullTiltParser extends AbsParser {

	private static final FullTiltParser INSTANCE = new FullTiltParser();

	private PokerLogsDBManager DBManager;

	private HashMap<String, Integer> playerMap = new HashMap<String, Integer>();

	public static FullTiltParser getInstance() {
		return INSTANCE;

	}

	@Override
	void understand(String fileContent) {

		DBManager = new PokerLogsDBManager();

		String[] gameLogs = null;
		String[] rounds = null;

		gameLogs = fileContent.split("\r\n\r\n\r\n");

		for (String log : gameLogs) {

			rounds = log.split("\r\n\\*\\*\\*.");

			for (int x = 0; x < rounds.length; x++) {

				if (rounds[x].length() > 0) {
					if (x == 0) { // Si on est dans la section info générales de la game

						readHeader((rounds[x].split("\r\n"))[0]);
						setPlayers(rounds[x]);
						setDealerID(rounds[x]);
						setBlindBettingRound(rounds[x]); // Broken

					} else if (x == rounds.length - 1) {
						// Game Summary

					} else {
						// is typical betting round
						// First word is bettingRoundType
						insertBettingActions(rounds[x]);
						getBettingRoundType((rounds[x].split("\r\n"))[0]);
						System.exit(-1);

					}

				}
			}
		}

	}

	private void insertBettingActions(String bettingInfo) {
		String[] actions = (bettingInfo.split("\r\n"));
		String type = getBettingRoundType(actions[0]);

		Pattern cardsDealt = Pattern.compile("Dealt to ([\\w+\\s]+)\\x5B(..) (..)[\\x5B\\x5D]");
		Pattern fold = Pattern.compile("([\\w+\\s]+) folds");
		Pattern raise = Pattern.compile("([\\w+\\s]+)raises to \\$?(\\d+[.]?[0-9]?{2})");

		for (int x = 1; x < actions.length; x++) {

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
			System.out.println("Seat # " + RegexMatcher.group(1) + " is the dealer");
			// Save idDealer for when all game info is acquired
		}

	}

	private void readHeader(String header) {

		/*
		 * group(1) : Domain group(2) : idGame group(3) : unimportant tournament data group(4): tableName group(5): max players on table optional group(6): small blind group(7): big blind group(8): bettype group(7): startTime - keep as timestamp or go time and date seperate? group(9): time group(10): timezone group(11): date
		 */

		String tableInfo = "([\\w+\\s]+)Game.#(\\d+):(..+,)?.Table.(\\w+\\s)+([\\x28]\\d max[\\x29])? ?- ";
		String bettype = "([\\w+\\s]+)Hold'em - ";
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
			System.out.println("7: " + RegexMatcher.group(7));
			System.out.println("8: " + RegexMatcher.group(8));
			System.out.println("9: " + RegexMatcher.group(9));
			System.out.println("10: " + RegexMatcher.group(10));
			System.out.println("11: " + RegexMatcher.group(11));

		}

	}

	private void setPlayers(String seatInfo) {

		Integer nbPlayers = 0;

		Pattern myregex = Pattern.compile("Seat \\d: ([\\w+\\s]+)[\\x28]\\$?(.+)[\\x29]");

		Matcher matcher = myregex.matcher(seatInfo);

		while (matcher.find()) {
			nbPlayers++;
			if (playerMap.isEmpty()) {

				System.out.println("Player: " + matcher.group(1));
				System.out.println("Monies: " + matcher.group(2));

				// Obtenir les id Player des joueurs et ajouter comme value du hashmap
				playerMap.put(matcher.group(1), DBManager.getPlayerID(matcher.group(1).trim()));

			} else {
				// Fait un check si les player ont changé
				// But: éviter de refaire des query dans la bd à chaque game du même log
			}

		}

	}

	private void setBlindBettingRound(String info) {

		/*
		 * TODO: Get corresponding ID's from playerMap once bd is up and running
		 */

		Pattern p = Pattern.compile("([\\w+\\s]+)posts the small blind of .+");
		Matcher RegexMatcher = p.matcher(info);

		if (RegexMatcher.find()) {
			System.out.println("id smallBlind: " + RegexMatcher.group(0));
		}

		p = Pattern.compile("([\\w+\\s]+)posts the big blind of .+");

		if (RegexMatcher.find()) {
			System.out.println("id bigBlind: " + RegexMatcher.group(1));

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

		}

		return null;

	}

}
