package poker.game;

import poker.HandEvaluator;
import game.Card;
import game.CardSet;

public class PlayerInfo
{
    private int m_currentSafeMoneyAmount;
    private int m_currentBetMoneyAmount;
    private int m_currentTablePosition;
    private int m_initialMoneyAmount;
    
    private final CardSet m_currentHand = new CardSet(2);
    private String m_playerName;
    private boolean m_playing;
    private boolean m_allIn;
    private boolean m_showingCards;
    
    public PlayerInfo()
    {
        m_playerName = "Anonymous Player";
        m_currentTablePosition = -1;
        m_currentSafeMoneyAmount = 0;
        m_initialMoneyAmount = 0;
    }
    
    public PlayerInfo(String p_name)
    {
        this();
        m_playerName = p_name;
    }
    
    public PlayerInfo(String p_name, int p_money)
    {
        this(p_name);
        m_currentSafeMoneyAmount = p_money;
        m_initialMoneyAmount = p_money;
    }
    
    public PlayerInfo(int p_noSeat, String p_name, int p_money)
    {
        this(p_name, p_money);
        m_currentTablePosition = p_noSeat;
    }
    
    public PlayerInfo(int p_noSeat)
    {
        this();
        m_currentTablePosition = p_noSeat;
    }
    
    public void setHand(Card card1, Card card2)
    {
        m_currentHand.clear();
        m_currentHand.add(card1);
        m_currentHand.add(card2);
    }
    
    public void setHand(CardSet set)
    {
        m_currentHand.clear();
        while (!set.isEmpty())
        {
            final Card gc = set.pop();
            m_currentHand.add(gc);
        }
    }
    
    public int getCurrentSafeMoneyAmount()
    {
        return m_currentSafeMoneyAmount;
    }
    
    public int getCurrentTotalMoneyAmount()
    {
        return getCurrentBetMoneyAmount() + getCurrentSafeMoneyAmount();
    }
    
    public int getCurrentTablePosition()
    {
        return m_currentTablePosition;
    }
    
    public long handValue(CardSet p_board)
    {
        final CardSet hand = new CardSet(p_board);
        hand.addAll(m_currentHand);
        return HandEvaluator.hand7Eval(HandEvaluator.encode(hand));
    }
    
    public Card[] getCurrentHand(boolean canSeeCards)
    {
        final Card[] holeCards = new Card[2];
        m_currentHand.toArray(holeCards);
        if (holeCards[0] != null && holeCards[1] == null && holeCards[0].getId() < 0)
        {
            holeCards[1] = holeCards[0];
        }
        for (int j = 0; j < 2; ++j)
        {
            if (holeCards[j] == null)
            {
                holeCards[j] = Card.NO_CARD;
            }
            else if (!m_playing && !m_allIn)
            {
                holeCards[j] = Card.NO_CARD;
            }
            else if (!canSeeCards && !m_showingCards)
            {
                holeCards[j] = Card.HIDDEN_CARD;
            }
        }
        
        return holeCards;
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public boolean isPlaying()
    {
        return m_playing;
    }
    
    public boolean isAllIn()
    {
        return m_allIn;
    }
    
    public boolean canPlay()
    {
        return m_currentTablePosition >= 0 && m_currentSafeMoneyAmount > 0;
    }
    
    public void setAllIn()
    {
        m_allIn = true;
        m_playing = false;
    }
    
    public void setFolded()
    {
        m_allIn = false;
        m_playing = false;
    }
    
    public void setPlaying()
    {
        m_showingCards = false;
        m_allIn = true;
        m_playing = true;
    }
    
    public void setCurrentTablePosition(int currentTablePosition)
    {
        m_currentTablePosition = currentTablePosition;
    }
    
    public boolean canBet(int money)
    {
        return (money <= m_currentSafeMoneyAmount);
    }
    
    public boolean tryBet(int money)
    {
        if (!canBet(money))
        {
            return false;
        }
        m_currentSafeMoneyAmount -= money;
        m_currentBetMoneyAmount += money;
        return true;
    }
    
    public int getCurrentBetMoneyAmount()
    {
        return m_currentBetMoneyAmount;
    }
    
    public void setCurrentBetMoneyAmount(int currentBetMoneyAmount)
    {
        m_currentBetMoneyAmount = currentBetMoneyAmount;
    }
    
    public void setShowingCards(boolean showingCards)
    {
        m_showingCards = showingCards;
    }
    
    public boolean isShowingCards()
    {
        return m_showingCards;
    }
    
    public void setCurrentSafeMoneyAmount(int currentSafeMoneyAmount)
    {
        m_currentSafeMoneyAmount = currentSafeMoneyAmount;
    }
    
    public void incCurrentSafeMoneyAmount(int inc)
    {
        m_currentSafeMoneyAmount += inc;
    }
    
    public int getInitialMoneyAmount()
    {
        return m_initialMoneyAmount;
    }
}
