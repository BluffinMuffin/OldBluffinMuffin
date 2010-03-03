package parser.data;

import java.util.ArrayList;

public class GameSet extends ArrayList<Game> {

	public Integer nbPlayers = null;
	public Double smallBlindValue = null;
	public Double bigBlindValue = null;
	public String gameType = null; // Ring, Tournament
	public String[] seats = new String[9];
	public boolean realMoney = false;
}
