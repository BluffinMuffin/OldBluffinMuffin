package newPokerLogic;

import gameLogic.GameCard;
import gameLogic.GameCardSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PokerTableInfo
{
    private final int m_nbMaxSeats;
    private int m_nbUsedSeats;
    private final PokerPlayerInfo[] m_currentPlayers;
    
    private final int m_smallBlindAmount;
    private final int m_bigBlindAmount;
    
    private final GameCardSet m_currentBoardCards = new GameCardSet(5);
    
    private final String m_tableName;
    private final Stack<Integer> m_RemainingSeats = new Stack<Integer>();
    
    public PokerTableInfo()
    {
        this(10);
    }
    
    public PokerTableInfo(int nbSeats)
    {
        this("Anonymous Table", 10, nbSeats);
    }
    
    public PokerTableInfo(String pName, int pBigBlind, int nbSeats)
    {
        m_nbMaxSeats = nbSeats;
        m_nbUsedSeats = 0;
        m_currentPlayers = new PokerPlayerInfo[m_nbMaxSeats];
        m_tableName = pName;
        m_bigBlindAmount = pBigBlind;
        m_smallBlindAmount = pBigBlind / 2;
        
        for (int i = 1; i <= m_nbMaxSeats; ++i)
        {
            m_RemainingSeats.push(m_nbMaxSeats - i);
        }
    }
    
    public void setBoardCards(GameCard c1, GameCard c2, GameCard c3, GameCard c4, GameCard c5)
    {
        m_currentBoardCards.clear();
        addBoardCard(c1);
        addBoardCard(c2);
        addBoardCard(c3);
        addBoardCard(c4);
        addBoardCard(c5);
    }
    
    public void setBoardCards(GameCardSet set)
    {
        m_currentBoardCards.clear();
        addBoardCards(set);
    }
    
    public void addBoardCards(GameCardSet set)
    {
        while (!set.isEmpty())
        {
            addBoardCard(set.pop());
        }
    }
    
    public void addBoardCard(GameCard c)
    {
        m_currentBoardCards.add(c);
    }
    
    public int getNbUsedSeats()
    {
        return m_nbUsedSeats;
    }
    
    public void setNbUsedSeats(int nbUsedSeats)
    {
        m_nbUsedSeats = nbUsedSeats;
    }
    
    public int getNbMaxSeats()
    {
        return m_nbMaxSeats;
    }
    
    public PokerPlayerInfo[] getCurrentPlayers()
    {
        return m_currentPlayers;
    }
    
    public int getSmallBlindAmount()
    {
        return m_smallBlindAmount;
    }
    
    public int getBigBlindAmount()
    {
        return m_bigBlindAmount;
    }
    
    public GameCardSet getCurrentBoardCards()
    {
        return m_currentBoardCards;
    }
    
    public String getTableName()
    {
        return m_tableName;
    }
    
    public boolean joinTable(PokerPlayerInfo p, int seat)
    {
        if (m_RemainingSeats.size() == 0)
        {
            return false;
        }
        
        if (containsPlayer(p))
        {
            return false;
        }
        
        if (!m_RemainingSeats.contains(seat))
        {
            return false;
        }
        m_RemainingSeats.remove(seat);
        p.setFolded();
        p.setCurrentTablePosition(seat);
        m_currentPlayers[seat] = p;
        return true;
    }
    
    public boolean joinTable(PokerPlayerInfo p)
    {
        if (m_RemainingSeats.size() == 0)
        {
            return false;
        }
        
        if (containsPlayer(p))
        {
            return false;
        }
        
        final int seat = m_RemainingSeats.pop();
        p.setFolded();
        p.setCurrentTablePosition(seat);
        m_currentPlayers[seat] = p;
        return true;
    }
    
    public boolean leaveTable(PokerPlayerInfo p)
    {
        
        if (!containsPlayer(p))
        {
            return false;
        }
        
        final int seat = p.getCurrentTablePosition();
        p.setFolded();
        p.setCurrentTablePosition(-1);
        m_currentPlayers[seat] = null;
        
        return true;
    }
    
    public List<PokerPlayerInfo> getPlayers()
    {
        final List<PokerPlayerInfo> list = new ArrayList<PokerPlayerInfo>();
        for (int i = 0; i < m_nbMaxSeats; ++i)
        {
            if (m_currentPlayers[i] != null)
            {
                list.add(m_currentPlayers[i]);
            }
        }
        return list;
    }
    
    public List<PokerPlayerInfo> getPlayingPlayers()
    {
        final List<PokerPlayerInfo> list = new ArrayList<PokerPlayerInfo>();
        for (int i = 0; i < m_nbMaxSeats; ++i)
        {
            if (m_currentPlayers[i] != null && m_currentPlayers[i].isPlaying())
            {
                list.add(m_currentPlayers[i]);
            }
        }
        return list;
    }
    
    private boolean containsPlayer(PokerPlayerInfo p)
    {
        return getPlayers().contains(p);
    }
    
    public int getAndSetNbPlayingPlayers()
    {
        return 0;
    }
    
    private PokerPlayerInfo nextPlayer(int seat, List<PokerPlayerInfo> players)
    {
        return players.get((seat + 1) % players.size());
    }
    
    public PokerPlayerInfo nextPlayer(int seat)
    {
        return nextPlayer(seat, getPlayers());
    }
    
    public PokerPlayerInfo nextPlayingPlayer(int seat)
    {
        return nextPlayer(seat, getPlayingPlayers());
    }
    
    public PokerPlayerInfo getPlayer(int seat)
    {
        return m_currentPlayers[seat];
    }
}
