package pokerGameLogic;

import gameLogic.GameCard;
import gameLogic.GameCardSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

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
    
    private final List<PokerMoneyPot> m_pots = new ArrayList<PokerMoneyPot>();
    private int m_totalPotAmount;
    private int m_currentPotId;
    private final LinkedBlockingQueue<Integer> m_allInCaps = new LinkedBlockingQueue<Integer>();
    
    private int m_currentDealerNoSeat;
    private int m_currentSmallBlindNoSeat;
    private int m_currentBigBlindNoSeat;
    private int m_currentPlayerNoSeat;
    
    private final Map<PokerPlayerInfo, Integer> m_blindNeeded = new HashMap<PokerPlayerInfo, Integer>();
    private int m_totalBlindNeeded;
    
    private int m_nbPlayed;
    private int m_nbAllIn;
    private int m_currentHigherBet;
    
    private TypePokerGameRound m_currentGameRound;
    private TypePokerGameLimit m_betLimit;
    
    public PokerTableInfo()
    {
        this(10);
    }
    
    public PokerTableInfo(int nbSeats)
    {
        this("Anonymous Table", 10, nbSeats, TypePokerGameLimit.NO_LIMIT);
    }
    
    public PokerTableInfo(String pName, int pBigBlind, int nbSeats, TypePokerGameLimit limit)
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
        m_betLimit = limit;
        for (int i = 1; i <= m_nbMaxSeats; ++i)
        {
            m_RemainingSeats.push(m_nbMaxSeats - i);
        }
    }
    
    public void initTable()
    {
        m_currentBoardCards.clear();
        setNbPlayed(0);
        placeButtons();
        initPots();
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
    
    public boolean forceJoinTable(PokerPlayerInfo p, int seat)
    {
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
    
    public void getAndSetNbPlayingPlayers()
    {
        for (final PokerPlayerInfo p : getPlayers())
        {
            if (p.canPlay())
            {
                p.setPlaying();
            }
        }
    }
    
    public PokerPlayerInfo nextPlayer(int seat)
    {
        for (int i = 0; i < m_nbMaxSeats; ++i)
        {
            final int j = (seat + 1 + i) % m_nbMaxSeats;
            if (m_currentPlayers[j] != null)
            {
                return m_currentPlayers[j];
            }
        }
        return m_currentPlayers[seat];
    }
    
    public PokerPlayerInfo nextPlayingPlayer(int seat)
    {
        for (int i = 0; i < m_nbMaxSeats; ++i)
        {
            final int j = (seat + 1 + i) % m_nbMaxSeats;
            if (m_currentPlayers[j] != null && m_currentPlayers[j].isPlaying())
            {
                return m_currentPlayers[j];
            }
        }
        return m_currentPlayers[seat];
    }
    
    public PokerPlayerInfo getPlayer(int seat)
    {
        return m_currentPlayers[seat];
    }
    
    public List<PokerMoneyPot> getPots()
    {
        return m_pots;
    }
    
    public void initPots()
    {
        setTotalPotAmount(0);
        m_pots.clear();
        m_allInCaps.clear();
        m_pots.add(new PokerMoneyPot(0));
        m_currentPotId = 0;
        setNbAllIn(0);
    }
    
    public void placeButtons()
    {
        m_currentDealerNoSeat = nextPlayingPlayer(m_currentDealerNoSeat).getCurrentTablePosition();
        m_currentSmallBlindNoSeat = getNbPlaying() == 2 ? m_currentDealerNoSeat : nextPlayingPlayer(m_currentDealerNoSeat).getCurrentTablePosition();
        m_currentBigBlindNoSeat = nextPlayingPlayer(m_currentSmallBlindNoSeat).getCurrentTablePosition();
        m_blindNeeded.clear();
        m_blindNeeded.put(getPlayer(m_currentSmallBlindNoSeat), getSmallBlindAmount());
        m_blindNeeded.put(getPlayer(m_currentBigBlindNoSeat), getBigBlindAmount());
        m_totalBlindNeeded = getSmallBlindAmount() + getBigBlindAmount();
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
    
    public void incTotalPotAmount(int inc)
    {
        System.out.print("incTotalPotAmount from $" + m_totalPotAmount);
        m_totalPotAmount += inc;
        System.out.println(" to $" + m_totalPotAmount);
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
    
    public int getNbPlaying()
    {
        return getPlayingPlayers().size();
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
    
    public void addAllInCap(Integer cap)
    {
        final int tot = cap;
        System.out.println("Adding cap of: " + cap);
        if (!m_allInCaps.contains(tot))
        {
            try
            {
                m_allInCaps.put(tot);
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void AddBet(PokerPlayerInfo p, PokerMoneyPot pot, int bet)
    {
        p.setCurrentBetMoneyAmount(p.getCurrentBetMoneyAmount() - bet);
        pot.addAmount(bet);
        if (bet >= 0 && (p.isPlaying() || p.isAllIn()))
        {
            pot.attachPlayer(p);
        }
    }
    
    public void managePotsRoundEnd()
    {
        int currentTaken = 0;
        while (m_allInCaps.size() > 0)
        {
            final PokerMoneyPot pot = m_pots.get(m_currentPotId);
            pot.detachAll();
            final int cap = m_allInCaps.poll() - currentTaken;
            
            for (final PokerPlayerInfo p : getPlayers())
            {
                AddBet(p, pot, Math.min(p.getCurrentBetMoneyAmount(), cap));
            }
            currentTaken += cap;
            m_currentPotId++;
            m_pots.add(new PokerMoneyPot(m_currentPotId));
        }
        
        final PokerMoneyPot curPot = m_pots.get(m_currentPotId);
        curPot.detachAll();
        for (final PokerPlayerInfo p : getPlayers())
        {
            AddBet(p, curPot, p.getCurrentBetMoneyAmount());
            
        }
        m_currentHigherBet = 0;
    }
    
    public void setNbAllIn(int nbAllIn)
    {
        m_nbAllIn = nbAllIn;
    }
    
    public void incNbAllIn()
    {
        m_nbAllIn++;
    }
    
    public int getNbAllIn()
    {
        return m_nbAllIn;
    }
    
    public void cleanPotsForWinning()
    {
        for (int i = 0; i <= m_currentPotId; ++i)
        {
            final PokerMoneyPot pot = m_pots.get(i);
            long bestHand = 0;
            final List<PokerPlayerInfo> info = new ArrayList<PokerPlayerInfo>(pot.getAttachedPlayers());
            pot.detachAll();
            for (final PokerPlayerInfo p : info)
            {
                final long handValue = p.handValue(m_currentBoardCards);
                if (handValue > bestHand)
                {
                    pot.detachAll();
                    pot.attachPlayer(p);
                    bestHand = handValue;
                }
                else if (handValue == bestHand)
                {
                    pot.attachPlayer(p);
                }
            }
        }
    }
    
    public void setCurrentGameRound(TypePokerGameRound currentGameRound)
    {
        m_currentGameRound = currentGameRound;
    }
    
    public TypePokerGameRound getCurrentGameRound()
    {
        return m_currentGameRound;
    }
    
    public TypePokerGameLimit getBetLimit()
    {
        return m_betLimit;
    }
    
    public void setBetLimit(TypePokerGameLimit limit)
    {
        m_betLimit = limit;
    }
    
    public boolean canRaise(PokerPlayerInfo p)
    {
        return getCurrentHigherBet() < p.getCurrentTotalMoneyAmount();
    }
    
    public boolean canCheck(PokerPlayerInfo p)
    {
        return getCurrentHigherBet() <= p.getCurrentBetMoneyAmount();
    }
    
    public int getMinRaiseAmount(PokerPlayerInfo p)
    {
        return Math.min(getCallAmount(p) + getBigBlindAmount(), getMaxRaiseAmount(p));
    }
    
    public int getMaxRaiseAmount(PokerPlayerInfo p)
    {
        return getFreeMoneyAmount(p) + getCallAmount(p);
    }
    
    public int getCallAmount(PokerPlayerInfo p)
    {
        return getCurrentHigherBet() - p.getCurrentBetMoneyAmount();
    }
    
    public int getFreeMoneyAmount(PokerPlayerInfo p)
    {
        return p.getCurrentTotalMoneyAmount() - getCurrentHigherBet();
    }
}
