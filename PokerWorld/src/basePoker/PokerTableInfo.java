package basePoker;

import java.util.ArrayList;
import java.util.List;

public class PokerTableInfo
{
    
    public int m_nbSeats;
    
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
    
    protected PokerPlayerInfo[] m_players;
    
    public ArrayList<Integer> m_pots = new ArrayList<Integer>();
    
    public int m_currentBet;
    
    public PokerTableInfo()
    {
        this(9);
    }
    
    public PokerTableInfo(int nbSeats)
    {
        m_nbSeats = nbSeats;
        initializePlayers(nbSeats);
    }
    
    public List<PokerPlayerInfo> getPlayers()
    {
        final ArrayList<PokerPlayerInfo> players = new ArrayList<PokerPlayerInfo>();
        for (int i = 0; i < m_nbSeats; ++i)
        {
            if (m_players[i] != null)
            {
                players.add(m_players[i]);
            }
        }
        return players;
    }
    
    public List<Integer> getPlayerIds()
    {
        final ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < m_nbSeats; ++i)
        {
            if (m_players[i] != null)
            {
                ids.add(i);
            }
        }
        return ids;
    }
    
    public PokerPlayerInfo getPlayer(int i)
    {
        return m_players[i];
    }
    
    public void removePlayer(int i)
    {
        if (m_players[i] != null)
        {
            m_nbPlayers--;
        }
        m_players[i] = null;
    }
    
    public void removePlayer(PokerPlayerInfo p)
    {
        int count = 0;
        for (int i = 0; i < m_nbSeats; ++i)
        {
            if (m_players[i] == p)
            {
                m_players[i] = null;
                m_nbPlayers--;
                count++;
            }
        }
    }
    
    public int addPlayer(PokerPlayerInfo p)
    {
        for (int i = 0; i < m_nbSeats; ++i)
        {
            if (m_players[i] == null)
            {
                m_nbPlayers++;
                m_players[i] = p;
                return i;
            }
        }
        return -1;
    }
    
    public void addPlayer(int id, PokerPlayerInfo p)
    {
        if (m_players[id] == null)
        {
            m_nbPlayers++;
        }
        m_players[id] = p;
    }
    
    public void clearPlayers()
    {
        m_nbPlayers = 0;
        for (int i = 0; i < m_nbSeats; ++i)
        {
            m_players[i] = null;
        }
    }
    
    public void initializePlayers(int nbSeat)
    {
        m_nbPlayers = 0;
        m_nbSeats = nbSeat;
        m_players = new PokerPlayerInfo[nbSeat];
    }
}
