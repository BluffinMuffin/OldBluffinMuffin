package parser.data;

import java.util.ArrayList;
import java.util.HashMap;

public class GameSet extends ArrayList<Game> {

	public enum BetType {
		NoLimit, PotLimit, CapNoLimit;

		private static HashMap<String, BetType> altValues = new HashMap<String, BetType>() {
			{
				put("no limit", BetType.NoLimit);
				put("pot limit", BetType.PotLimit);
				put("cap no limit", BetType.CapNoLimit);

			}
		};

		public static BetType altValueOf(String arg0) {
			if (altValues.containsKey(arg0.toLowerCase())) {
				return altValues.get(arg0.toLowerCase());
			} else {
				return valueOf(arg0);
			}
		}
	};

	public enum PokerType {
		Holdem, OmahaHI, OmahaNL;

		private static HashMap<String, PokerType> altValues = new HashMap<String, PokerType>() {
			{
				put("hold'em", PokerType.Holdem);
				put("omaha hi", PokerType.OmahaHI);
				put("omaha N/L", PokerType.OmahaNL);

			}
		};

		public static PokerType altValueOf(String arg0) {
			if (altValues.containsKey(arg0.toLowerCase())) {
				return altValues.get(arg0.toLowerCase());
			} else {
				return valueOf(arg0);
			}
		}
	};

	public enum GameType {
		Ring, Tournament;

		private static HashMap<String, GameType> altValues = new HashMap<String, GameType>() {
			{
				put("ring", GameType.Ring);
				put("tourney", GameType.Tournament);

			}
		};

		public static GameType altValueOf(String arg0) {
			if (altValues.containsKey(arg0)) {
				return altValues.get(arg0);
			} else {
				return valueOf(arg0);
			}
		}
	};

	public GameType gameType = null;
	public PokerType pokerType = null;
	public BetType betType = null;

	public boolean realMoney = false;
	public String tableName = null;
	public Integer nbPlayers = null;
	public Double smallBlindValue = null;
	public Double bigBlindValue = null;

}
