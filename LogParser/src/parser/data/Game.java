package parser.data;

import java.util.ArrayList;
import java.util.HashMap;

public class Game extends ArrayList<Bet> {

	// public Integer idGame = null;; // Game identifier
	public String startTime = null;; // Game start time
	public String dealerName = null;; // The player who has the button
	public String[] table = new String[5]; // Array of 5 elements representing the community cards
	public HashMap<String, String[]> playerCards = new HashMap<String, String[]>();

}
