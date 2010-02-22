package newPokerLogic;

import gameLogic.GameCard;
import gameLogic.GameCardSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PokerTableInfo
{// TODO: Gestion TypePokerGame
    private final int m_nbMaxSeats;
    private int m_nbUsedSeats;
    private final PokerPlayerInfo[] m_currentPlayers;
    
    private final int m_smallBlindAmount;
    private final int m_bigBlindAmount;
    
    private final GameCardSet m_currentBoardCards = new GameCardSet(5);
    
    private final String m_tableName;
    private final Stack<Integer> m_RemainingSeats = new Stack<Integer>();
    
    private final Stack<PokerMoneyPot> m_pots = new Stack<PokerMoneyPot>();
    private int m_totalPotAmount;
    
    private int m_currentDealerNoSeat;
    private int m_currentSmallBlindNoSeat;
    private int m_currentBigBlindNoSeat;
    private int m_currentPlayerNoSeat;
    
    private final Map<PokerPlayerInfo, Integer> m_blindNeeded = new HashMap<PokerPlayerInfo, Integer>();
    private int m_totalBlindNeeded;
    
    private int m_nbPlayed;
    private int m_nbPlaying;
    private int m_currentHigherBet;
    
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
        m_currentDealerNoSeat = -1;
        m_currentSmallBlindNoSeat = -1;
        m_currentBigBlindNoSeat = -1;
        
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
            System.err.println("Too bad: m_RemainingSeats.size() == 0");
            return false;
        }
        
        if (containsPlayer(p))
        {
            System.err.println("Too bad: containsPlayer(p)");
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
    
    public Stack<PokerMoneyPot> getPots()
    {
        return m_pots;
    }
    
    public void initPots()
    {
        setTotalPotAmount(0);
        m_pots.clear();
        m_pots.push(new PokerMoneyPot(0));
    }
    
    public void placeButtons()
    {
        m_currentDealerNoSeat = nextPlayingPlayer(m_currentDealerNoSeat).getCurrentTablePosition();
        m_currentSmallBlindNoSeat = nextPlayingPlayer(m_currentDealerNoSeat).getCurrentTablePosition();
        m_currentBigBlindNoSeat = nextPlayingPlayer(m_currentSmallBlindNoSeat).getCurrentTablePosition();
        m_blindNeeded.clear();
        m_blindNeeded.put(getPlayer(m_currentSmallBlindNoSeat), getSmallBlindAmount());
        m_blindNeeded.put(getPlayer(m_currentBigBlindNoSeat), getBigBlindAmount());
    }
    
    public int blindNeeded(PokerPlayerInfo p)
    {
        if (m_blindNeeded.containsKey(p))
        {
            return m_blindNeeded.get(p);
        }
        return 0;
    }
    
    public void setTotalPotAmount(int totalPotAmount)
    {
        m_totalPotAmount = totalPotAmount;
    }
    
    public int getTotalPotAmount()
    {
        return m_totalPotAmount;
    }
    
    public void setCurrentDealerNoSeat(int currentDealerNoSeat)
    {
        m_currentDealerNoSeat = currentDealerNoSeat;
    }
    
    public int getCurrentDealerNoSeat()
    {
        return m_currentDealerNoSeat;
    }
    
    public void setCurrentSmallBlindNoSeat(int currentSmallBlindNoSeat)
    {
        m_currentSmallBlindNoSeat = currentSmallBlindNoSeat;
    }
    
    public int getCurrentSmallBlindNoSeat()
    {
        return m_currentSmallBlindNoSeat;
    }
    
    public void setCurrentBigBlindNoSeat(int currentBigBlindNoSeat)
    {
        m_currentBigBlindNoSeat = currentBigBlindNoSeat;
    }
    
    public int getCurrentBigBlindNoSeat()
    {
        return m_currentBigBlindNoSeat;
    }
    
    public void setCurrentPlayerNoSeat(int currentPlayerNoSeat)
    {
        m_currentPlayerNoSeat = currentPlayerNoSeat;
    }
    
    public int getCurrentPlayerNoSeat()
    {
        return m_currentPlayerNoSeat;
    }
    
    public void setBlindNeeded(PokerPlayerInfo p, int needed)
    {
        m_blindNeeded.put(p, needed);
    }
    
    public void setTotalBlindNeeded(int totalBlindNeeded)
    {
        m_totalBlindNeeded = totalBlindNeeded;
    }
    
    public int getTotalBlindNeeded()
    {
        return m_totalBlindNeeded;
    }
    
    public void setNbPlayed(int nbPlayed)
    {
        m_nbPlayed = nbPlayed;
    }
    
    public void incNbPlayed()
    {
        m_nbPlayed++;
    }
    
    public void decNbPlayed()
    {
        m_nbPlayed--;
    }
    
    public int getNbPlayed()
    {
        return m_nbPlayed;
    }
    
    public void setNbPlaying(int nbPlaying)
    {
        m_nbPlaying = nbPlaying;
    }
    
    public void incNbPlaying()
    {
        m_nbPlaying++;
    }
    
    public void decNbPlaying()
    {
        m_nbPlaying--;
    }
    
    public int getNbPlaying()
    {
        return m_nbPlaying;
    }
    
    public void setCurrentHigherBet(int currentHigherBet)
    {
        m_currentHigherBet = currentHigherBet;
    }
    
    public int getCurrentHigherBet()
    {
        return m_currentHigherBet;
    }
    
    public boolean containsPlayer(String name)
    {
        for (final PokerPlayerInfo p : getPlayers())
        {
            if (p.getPlayerName().equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }
}
