package newPokerLogic;

import gameLogic.GameCard;

public abstract class PokerPlayerInfo
{
    private int m_currentSafeMoneyAmount;
    private int m_currentBetMoneyAmount;
    private int m_currentTablePosition;
    
    private final GameCard[] m_currentHand = new GameCard[2];
    private String m_playerName;
    private boolean m_playing;
    private boolean m_stillThereForShowDown;
    
    public PokerPlayerInfo()
    {
        m_playerName = "Anonymous Player";
        m_currentTablePosition = -1;
        m_currentSafeMoneyAmount = 0;
        setHand(GameCard.NO_CARD, GameCard.NO_CARD);
    }
    
    public PokerPlayerInfo(String p_name)
    {
        this();
        m_playerName = p_name;
    }
    
    public PokerPlayerInfo(String p_name, int p_money)
    {
        this(p_name);
        m_currentSafeMoneyAmount = p_money;
    }
    
    public PokerPlayerInfo(int p_noSeat, String p_name, int p_money)
    {
        this(p_name, p_money);
        m_currentTablePosition = p_noSeat;
    }
    
    public PokerPlayerInfo(int p_noSeat)
    {
        this();
        m_currentTablePosition = p_noSeat;
    }
    
    public void setHand(GameCard card1, GameCard card2)
    {
        m_currentHand[0] = card1;
        m_currentHand[1] = card2;
    }
    
    public int getCurrentSafeMoneyAmount()
    {
        return m_currentSafeMoneyAmount;
    }
    
    public int getCurrentTablePosition()
    {
        return m_currentTablePosition;
    }
    
    public GameCard[] getCurrentHand()
    {
        return m_currentHand;
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public boolean isPlaying()
    {
        return m_playing;
    }
    
    public boolean isStillThereForShowDown()
    {
        return m_stillThereForShowDown;
    }
    
    public boolean canPlay()
    {
        return m_currentTablePosition >= 0 && m_currentSafeMoneyAmount > 0;
    }
    
    public void setAllIn()
    {
        m_stillThereForShowDown = true;
        m_playing = false;
    }
    
    public void setFolded()
    {
        m_stillThereForShowDown = false;
        m_playing = false;
    }
    
    public void setPlaying()
    {
        m_stillThereForShowDown = true;
        m_playing = true;
    }
    
    public void setCurrentTablePosition(int currentTablePosition)
    {
        m_currentTablePosition = currentTablePosition;
    }
    
    public boolean bet(int money)
    {
        if (money > m_currentSafeMoneyAmount)
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
}
