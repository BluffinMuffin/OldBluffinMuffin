package parser.data;

import java.util.HashMap;

public class Bet {

	// public enum BettingRound {Blind, PreFlop, PreTurn, PreRiver, PreShowdown };
	// public BettingRound br;
	/*
	 * public enum ActionType {
	 * Fold, Check, Call, Raise;
	 * 
	 * public static ActionType altValueOf(String arg0) {
	 * ActionType r = null;
	 * if (arg0.equals("folds")) {
	 * r = ActionType.Fold;
	 * }
	 * return r;
	 * }
	 * };
	 */

	public enum ActionType {
		Fold, Check, Call, Raise;

		private static HashMap<String, ActionType> altValues = new HashMap<String, ActionType>() {
			{
				put("folds", ActionType.Fold);
				put("checks", ActionType.Check);
				put("calls", ActionType.Call);
				put("raises", ActionType.Raise);
			}
		};

		public static ActionType altValueOf(String arg0) {
			if (altValues.containsKey(arg0)) {
				return altValues.get(arg0);
			} else {
				return valueOf(arg0);
			}
		}
	};

	public enum RoundType {
		PreFlop, Flop, Turn, River;

		private static HashMap<String, RoundType> altValues = new HashMap<String, RoundType>() {
			{
				put("HOLE CARDS", RoundType.PreFlop);
				put("FLOP", RoundType.Flop);
				put("TURN", RoundType.Turn);
				put("RIVER", RoundType.River);
			}
		};

		public static RoundType altValueOf(String arg0) {
			if (altValues.containsKey(arg0)) {
				return altValues.get(arg0);
			} else {
				return valueOf(arg0);
			}
		}
	};

	public enum ForcedBetType {
		SmallBlind, BigBlind, Post, Ante;

		private static HashMap<String, ForcedBetType> altValues = new HashMap<String, ForcedBetType>() {
			{
				put("small blind", ForcedBetType.SmallBlind);
				put("big blind", ForcedBetType.BigBlind);
				put("small", ForcedBetType.SmallBlind);
				put("big", ForcedBetType.BigBlind);
				put("posts", ForcedBetType.Post);
			}
		};

		public static ForcedBetType altValueOf(String arg0) {
			if (altValues.containsKey(arg0.toLowerCase())) {
				return altValues.get(arg0.toLowerCase());
			} else {
				return valueOf(arg0);
			}
		}
	};

	public RoundType round = null;

	public String playerName = null;
	public ActionType action = null;;
	public Double amount = null;

	public Boolean forced = false;
	public ForcedBetType forceType = null;

}
