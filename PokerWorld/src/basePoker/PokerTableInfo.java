package basePoker;

import java.util.ArrayList;
import java.util.TreeMap;

public class PokerTableInfo
{
    
    public int m_nbSeats = 9;
    
    public int m_nbPlayers;
    public int m_nbPlayingPlayers;
    public int m_nbRemainingPlayers;
    
    public int m_totalPotAmount = 0;
    
    public int m_noSeatDealer = -1;
    public int m_noSeatSmallBlind = -1;
    public int m_noSeatBigBlind = -1;
    
    public PokerPlayerInfo m_currentPlayer = null;
    public PokerPlayerInfo m_dealer = null;
    public PokerPlayerInfo m_smallBlind = null;
    public PokerPlayerInfo m_bigBlind = null;
    public TypePokerGame m_gameType;
    
    public int m_smallBlindAmount;
    public int m_bigBlindAmount;
    public PokerPlayerInfo m_localPlayer = null;
    public ArrayList<Card> m_boardCards = new ArrayList<Card>();
    
    public TypePokerRound m_gameState = TypePokerRound.BEGINNING;
    public String m_name;
    
    public TreeMap<Integer, PokerPlayerInfo> m_players = new TreeMap<Integer, PokerPlayerInfo>();
    // public PokerPlayerInfo[] m_players;
    
    public ArrayList<Integer> m_pots = new ArrayList<Integer>();
    
    public int m_currentBet;
}
