package parser.data;

public class Bet {

	// public enum BettingRound {Blind, PreFlop, PreTurn, PreRiver, PreShowdown };
	// public BettingRound br;
	public enum ActionType {
		Fold, Check, Call, Raise
	};

	public enum ForcedBetType {
		SmallBlind, BigBlind, Post, Ante
	};

	public String round = null;
	public String playerName = null;
	public ActionType action = null;;
	public Double amount = null;

	public Boolean forced = false;
	public ForcedBetType forceType = null;

}