package parser;

import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.data.Bet;
import parser.data.Game;
import parser.data.GameSet;
import parser.data.PlayerInfo;
import parser.data.GameSet.GameType;

public class FullTiltParser extends AbsParser {

	public FullTiltParser(String fileContent) {
		super(fileContent);
	}

	protected void parse(String fileContent) {
		String[] gameList = fileContent.split("[\r\n]{4,}");
		String[] rounds = null;
		Game game = null;
		int fuckedup = 0;

		if (readHeader(gameList[0].split("\r\n")[0])) { // If header is valid
			for (String g : gameList) {
				rounds = g.split("\r\n\\*\\*\\*.");
				game = new Game();
				boolean goodGame = false;

				if (!rounds[0].split("\r\n")[0].endsWith("(partial)")) {

					for (int x = 0; x < rounds.length; x++) {
						if (!rounds[x].equals("\r\n")) {
							// System.out.println(rounds[x].split("\r\n")[0]);
							// Game general info
							if (x == 0) {
								if (!setPlayers(rounds[x], game)) {
									// goodGame = false;
									break;
								}

								if (!setDealerID(rounds[x], game)) {
									// System.out.println("Exitting du to no dealer");
									// System.out.println(rounds[x]);
									fuckedup++;
									// System.out.println(fuckedup);

									// goodGame = false;
									break;
								}
								setBlindBettingRound(rounds[x], "big", game);
								setBlindBettingRound(rounds[x], "small", game);

							} else if (x == rounds.length - 1) {
								// Game Summary
								setWinner(rounds[x], game);
								setBoard(rounds[x], game);
								goodGame = true;

							} else {
								// typical betting round
								setBettingActions(rounds[x], game);
							}
						}
					}

					if (goodGame) {
						gameSet.add(game);
					}
				}

			}
		}
	}

	private void setWinner(String summary, Game game) {
		Pattern winner = Pattern.compile("Seat \\d: ([\\w+\\-\\s]+?)(?:\\x28(?:small blind|big blind|button)\\x29 )?(?:showed|mucked)? ?(?:\\x5B(..) (..)\\x5D )?(?:and won |collected )?(?:\\x28\\$([\\d{1,3},]*\\d+(?:\\.\\d{1,2})?)\\x29)?(:? with|-|, mucked)");
		Matcher winMatcher = winner.matcher(summary);

		while (winMatcher.find()) {

			if (winMatcher.group(4) != null) {
				// Warning, ugly code

				PlayerInfo tmpNfo = game.playerMap.get(winMatcher.group(1).trim());
				tmpNfo.amountWon = Double.valueOf(winMatcher.group(4).replaceAll(",", ""));
				game.playerMap.put(winMatcher.group(1).trim(), tmpNfo);
			}

			if (winMatcher.group(2) != null && winMatcher.group(3) != null) {
				// TODO: UGLY!! Find a better way to set the cards in playerInfo
				// TODO2: probably time to make a function since same code written twice (also in setBoard)
				PlayerInfo tmpNfo = game.playerMap.get(winMatcher.group(1).trim());
				tmpNfo.cards = new String[] { winMatcher.group(2), winMatcher.group(3) };
				game.playerMap.put(winMatcher.group(1).trim(), tmpNfo);
			}

		}
		// System.exit(-1);

	}

	private void setBoard(String summary, Game game) {

		Pattern board = Pattern.compile("Board: \\[(..) (..) (..) ?(..)? ?(..)?\\]");
		Matcher tableMatcher = board.matcher(summary);

		if (tableMatcher.find()) {

			game.table[0] = tableMatcher.group(1);
			game.table[1] = tableMatcher.group(2);
			game.table[2] = tableMatcher.group(3);
			game.table[3] = tableMatcher.group(4);
			game.table[4] = tableMatcher.group(5);

		}

	}

	private void setBettingActions(String bettingInfo, Game game) {
		String[] actions = (bettingInfo.split("\r\n"));
		String round = getBettingRoundType(actions[0]);
		Double minBid = gameSet.bigBlindValue;

		if (!round.equals("SHOW DOWN")) {
			Pattern cardsDealt = Pattern.compile("Dealt to ([\\w+\\-\\s]+)\\[(..) (..)\\]");
			Pattern megaPattern = Pattern.compile("^([\\w+\\-\\s]+) (folds|raises|calls|checks) ?(to )?\\$?([\\d{1,3},]*\\d+(?:\\.\\d{1,2})?)?");

			for (int x = 1; x < actions.length; x++) {
				Matcher cardsDealtMatcher = cardsDealt.matcher(actions[x]);
				Matcher megaMatcher = megaPattern.matcher(actions[x]);

				if (cardsDealtMatcher.find()) {

					// TODO: UGLY!! Find a better way to set the cards in playerInfo
					PlayerInfo tmpNfo = game.playerMap.get(cardsDealtMatcher.group(1).trim());
					tmpNfo.cards = new String[] { cardsDealtMatcher.group(2), cardsDealtMatcher.group(3) };
					game.playerMap.put(cardsDealtMatcher.group(1).trim(), tmpNfo);
				}

				if (megaMatcher.find()) {

					Bet b = new Bet();
					b.round = Bet.RoundType.altValueOf(round);
					b.playerName = megaMatcher.group(1).trim();
					b.action = Bet.ActionType.altValueOf(megaMatcher.group(2).trim());

					switch (b.action) {
					case Raise:
						b.amount = Double.valueOf(megaMatcher.group(4).replaceAll(",", "")) - minBid;
						minBid = Double.valueOf(megaMatcher.group(4).replaceAll(",", ""));
						break;

					case Call:
						b.amount = Double.valueOf(megaMatcher.group(4).replaceAll(",", ""));
						break;

					default:
						// System.out.println(b.action.toString());
						break;
					}

					game.add(b);
				}
			}
		}

	}

	private boolean setDealerID(String info, Game game) {

		Pattern p = Pattern.compile("The button is in seat #(\\d)");
		Matcher RegexMatcher = p.matcher(info);
		boolean existingDealer = false;

		if (RegexMatcher.find()) {
			for (Entry<String, PlayerInfo> e : game.playerMap.entrySet()) {
				if (Integer.valueOf(RegexMatcher.group(1)).equals(e.getValue().seatNo)) {
					game.dealerName = e.getKey();
					existingDealer = true;
				}
			}
		}
		return existingDealer;
	}

	private boolean readHeader(String header) {

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

		String TIname = "(.+)";
		String TIgameNumber = "Game\\s#(\\d+):";
		String TItblName = "(..+,)?\\sTable\\s" + TIname;
		String TItblType = "(\\(.+\\))?";
		String tableInfo = TIname + TIgameNumber + TItblName + TItblType + "\\s*-\\s*";

		String bettype = "\\$?(?:[\\d{1,3},]*\\d+(?:\\.\\d{1,2})?)?(.+)(Hold'em|Omaha hi|Ohama H/L) - ";
		String timestmp = "([0-9]{0,2}:[0-9]{0,2}:[0-9]{0,2}) (\\w+) - ([0-9]{0,4}\\/[0-9]{0,2}\\/[0-9]{0,2})";

		String sbbb = "\\$([\\d{1,3},]*\\d+(?:\\.\\d{1,2})?)\\/\\$([\\d{1,3},]*\\d+(?:\\.\\d{1,2})?).-.";

		Pattern p = Pattern.compile(tableInfo + sbbb + bettype + timestmp, Pattern.CASE_INSENSITIVE);
		Matcher RegexMatcher = p.matcher(header);

		if (RegexMatcher.find()) {

			gameSet.smallBlindValue = Double.valueOf(RegexMatcher.group(6).replaceAll(",", ""));
			gameSet.bigBlindValue = Double.valueOf(RegexMatcher.group(7).replaceAll(",", ""));
			gameSet.betType = GameSet.BetType.altValueOf(RegexMatcher.group(8).trim());
			gameSet.pokerType = GameSet.PokerType.altValueOf(RegexMatcher.group(9));
			gameSet.gameType = GameType.Ring;

			return true;

		} else {
			System.out.println("(Tourney) Didn't parse : " + header);
			return false;
		}

	}

	private boolean setPlayers(String seatInfo, Game game) {

		Pattern myregex = Pattern.compile("Seat (\\d): ([\\w+\\-\\s]+)\\(\\$([\\d{1,3},]*\\d+(?:\\.\\d{1,2})?)\\)");
		Matcher matcher = myregex.matcher(seatInfo);
		boolean sittingPlayers = false; // Because some logs are shitty and have no players

		while (matcher.find()) {
			PlayerInfo nfo = new PlayerInfo();
			nfo.seatNo = Integer.valueOf(matcher.group(1));
			nfo.chips = Double.valueOf(matcher.group(3).replaceAll(",", ""));
			String playerName = matcher.group(2).trim();
			game.playerMap.put(playerName, nfo);
			sittingPlayers = true;

		}

		return sittingPlayers;

	}

	private void setBlindBettingRound(String info, String blindType, Game game) {

		// Find the blinds
		String rexName = "(.+)";
		String rexStaticString = "posts the " + blindType + " blind of\\s+";
		String rexMoney = "\\$([\\d{1,3},]*\\d+(?:\\.\\d{1,2})?)";

		Pattern p = Pattern.compile(rexName + rexStaticString + rexMoney);
		Matcher RegexMatcher = p.matcher(info);

		while (RegexMatcher.find()) {
			// System.out.println("id " + blindType + "Blind($" + RegexMatcher.group(2) + "): " + RegexMatcher.group(1));
			Bet b = new Bet();
			b.forced = true;
			b.forceType = Bet.ForcedBetType.altValueOf(blindType);
			b.playerName = RegexMatcher.group(1);
			b.amount = Double.valueOf(RegexMatcher.group(2).replaceAll(",", ""));
			game.add(b);
		}
	}

	private String getBettingRoundType(String firstLine) {
		String roundType = null;

		Pattern p = Pattern.compile("([\\w+\\-\\s]+)\\*\\*\\*");
		Matcher RegexMatcher = p.matcher(firstLine);

		if (RegexMatcher.find()) {
			roundType = RegexMatcher.group(1);
		}
		return roundType.trim();
	}

	private void PrintPlayerMap(Game game) {
		System.out.println("Printing hashmap ...");
		for (Entry<String, PlayerInfo> e : game.playerMap.entrySet()) {
			// players.put(e.getKey(), dba.getPlayerID(e.getKey()));

			System.out.println("Name: " + e.getKey());
			System.out.println("Seat: " + e.getValue().seatNo);

		}

	}

}
