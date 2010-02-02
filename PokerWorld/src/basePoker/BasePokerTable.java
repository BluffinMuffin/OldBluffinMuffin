package basePoker;

import java.util.ArrayList;
import java.util.TreeMap;

public class BasePokerTable {

    public int m_nbSeats = 9;
    
    public int m_nbPlayers;
    public int m_nbPlayingPlayers;
    public int m_nbRemainingPlayers;

    public int m_totalPotAmount = 0;
    
    public int m_noSeatDealer = -1;
    public int m_noSeatSmallBlind = -1;
    public int m_noSeatBigBlind = -1;
    
    public BasePokerPlayer m_currentPlayer = null;
    public BasePokerPlayer m_dealer = null;
    public BasePokerPlayer m_smallBlind = null;
    public BasePokerPlayer m_bigBlind = null;
    
    public int m_smallBlindAmount;
    public int m_bigBlindAmount;
    public BasePokerPlayer m_localPlayer = null;
    public ArrayList<Card> m_boardCards = new ArrayList<Card>();
    

    public TypePokerRound m_gameState = TypePokerRound.BEGINNING;
    public String m_name;
    
    public TreeMap<Integer, BasePokerPlayer> m_players = new TreeMap<Integer, BasePokerPlayer>();
    public ArrayList<Integer> m_pots = new ArrayList<Integer>();
    
    
    
    public int m_currentBet;
}
