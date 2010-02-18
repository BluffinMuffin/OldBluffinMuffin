package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullTiltParser extends AbsParser {

	private static final FullTiltParser INSTANCE = new FullTiltParser();
	private String splitter = "\r\n\r\n\r\n";

	public static FullTiltParser getInstance() {
		return INSTANCE;

	}

	@Override
	void understand(String fileContent) {

		System.out.println("I am a FullTilt");

		String[] gameLogs = null;
		String[] rounds = null;

		int nbPlayers = 0;

		gameLogs = fileContent.split(splitter);

		for (String log : gameLogs) {

			rounds = log.split("\r\n\\u002A\\u002A\\u002A.");

			for (int x = 0; x < rounds.length; x++) { // Do a for each? Need to increment a car anyways

				if (x == 0) {
					// First line is header
					/*
					 * ValBB, ValSB
					 */

					// Count player seats
					Pattern myregex = Pattern.compile("Seat.\\d:");

					Matcher matcher = myregex.matcher(rounds[x]); // Woudn't have to do this with a for each

					while (matcher.find()) {
						nbPlayers++;
					}
					System.out.println("yous got " + nbPlayers + " ppl sitting down");

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
