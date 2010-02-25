package parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.PostgresConnector;

public class FullTiltParser extends AbsParser {

	private static final FullTiltParser INSTANCE = new FullTiltParser();

	private PostgresConnector dbConnector;

	private Connection bob;

	private String splitter = "\r\n\r\n\r\n";

	public static FullTiltParser getInstance() {
		return INSTANCE;

	}

	@Override
	void understand(String fileContent) {

		dbConnector = new PostgresConnector("srv-prj-05.dmi.usherb.ca", "BluffinWifEnum", "postgres", "27053");

		System.out.println("I am a FullTilt");

		String[] gameLogs = null;
		String[] rounds = null;

		int nbPlayers = 0;
		HashMap<String, Integer> playerMap = new HashMap<String, Integer>();

		gameLogs = fileContent.split(splitter);

		for (String log : gameLogs) {

			rounds = log.split("\r\n\\*\\*\\*.");
			nbPlayers = 0;

			for (int x = 0; x < rounds.length; x++) {
				if (rounds[x].length() > 0) {

					if (x == 0) { // Si on est dans la section info générales de la game
						// Get player info
						Pattern myregex = Pattern.compile("Seat \\d: ([\\w+\\s]+)[\\x28]\\$?(.+)[\\x29]");

						Matcher matcher = myregex.matcher(rounds[x]);

						while (matcher.find()) {
							nbPlayers++;
							if (playerMap.isEmpty()) {

								System.out.println("Player: " + matcher.group(1));
								System.out.println("Monies: " + matcher.group(2));

								// Obtenir les id Player des joueurs et ajouter comme value du hashmap
								playerMap.put(matcher.group(1), getPlayerID(matcher.group(1)));

							} else {
								// Fait un check si les player ont changé
								// But: éviter de refaire des query dans la bd à chaque game du même log
							}

						}
						// System.out.println("yous got " + nbPlayers + " ppl sitting down");

						//
						myregex = Pattern.compile("is sitting out");

						while (matcher.find()) {
							nbPlayers--;
						}
						// System.out.println("But some are sitting out for a total of "
						// + nbPlayers );

						// Insert into GameSet (nbPlayers) values nbPlayers

						String firstLine = (rounds[x].split("\r\n"))[0];

						// Pattern p = Pattern.compile("$(\\d+)\\/$(\\d+)", Pattern.CASE_INSENSITIVE);
						Pattern p = Pattern.compile("\\$(\\d+)\\/\\$(\\d+)", Pattern.CASE_INSENSITIVE);

						Matcher RegexMatcher = p.matcher(firstLine);

						// BB and SB values
						if (RegexMatcher.find()) {
							System.out.println("SmallBlind : $" + RegexMatcher.group(1));
							System.out.println("BigBlind : $" + RegexMatcher.group(2));
						}

						// idDealer
						// Full Tilt Poker Game #4718288226: Table Amistad (6 max) - $1/$2 - No Limit Hold'em - 0:53:10 ET - 2008/01/03
						// FullTiltPoker Game #7736018171: Table Corwood Green - $0.05/$0.10 - No Limit Hold'em - 2:23:01 ET - 2008/08/21
						// FullTiltPoker Game #6776317222: $5 + $0.25 Heads Up Sit & Go (51507932), Table 1 - 10/20 - No Limit Hold'em - 21:46:06 ET - 2008/06/10

						/*
						 * group(1) : Domain
						 * group(2) : idGame
						 * group(3) : unimportant tournament data
						 * group(4): tableName
						 * group(5): max players on table optional
						 * group(6): small blind
						 * group(7): big blind
						 * group(8): bettype group(7): startTime - keep as timestamp or go time and date seperate?
						 * group(9): time
						 * group(10): timezone
						 * group(11): date
						 */

						// String tableInfo = "(\\w+\\s)+Game.#(\\d+):(..+[\\x28](\\d+)[\\x29],)?.Table.(\\w+\\s)+([\\x28](\\d) max[\\x29])? ?- ";
						String tableInfo = "([\\w+\\s]+)Game.#(\\d+):(..+,)?.Table.(\\w+\\s)+([\\x28]\\d max[\\x29])? ?- ";
						String bettype = "([\\w+\\s]+)Hold'em - ";
						String timestmp = "([0-9]{0,2}:[0-9]{0,2}:[0-9]{0,2}) (\\w+) - ([0-9]{0,4}\\/[0-9]{0,2}\\/[0-9]{0,2})";

						String sbbb = "\\$?(\\d+[.]?[0-9]?{2})\\/\\$?(\\d+[.]?[0-9]?{2}).-.";

						p = Pattern.compile(tableInfo + sbbb + bettype + timestmp, Pattern.CASE_INSENSITIVE);
						RegexMatcher = p.matcher(firstLine);
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
							// System.out.println("10: " + RegexMatcher.group(12));

						}

						System.exit(-1);

						/*
						 * Dealing round Dealer id BB and SB betting round
						 */

					} else if (x == rounds.length - 1) {
						// Game Summary

					} else {
						// is typical betting round
						// First word is bettingRoundType

					}

				}
			}
		}

	}

	private Integer getPlayerID(String playerName) {
		ResultSet rs = dbConnector.query("SELECT idPlayer from Player WHERE playerName = " + playerName);
		try {
			if (rs.wasNull()) {

				rs = dbConnector.query("INSERT INTO Player (playerName, idDomain) VALUES (" + playerName + ", " + "2");

				rs = dbConnector.query("SELECT idPlayer FROM Player where playerName = " + playerName);

				System.out.println("The attributed id for " + playerName + " was " + rs.getInt(1));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	}

}
