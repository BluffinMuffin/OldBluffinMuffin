package parser.data;

import java.util.ArrayList;
import java.util.HashMap;

public class Game extends ArrayList<Bet> {

	public String startTime = null;
	public String dealerName = null;
	public String[] table = new String[5]; // community cards

	// TODO: insert amount won by player in the playerInfo ? I approve my idea :D
	// You may delete this comment if you approve as well.
	// public HashMap<String, Double> winner = new HashMap<String, Double>();

	public HashMap<String, PlayerInfo> playerMap = new HashMap<String, PlayerInfo>();

}
