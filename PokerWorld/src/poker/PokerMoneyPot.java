package poker;

import java.util.ArrayList;
import java.util.List;

public class PokerMoneyPot
{
    private final int m_id;
    private int m_amount;
    private final List<PokerPlayerInfo> m_attachedPlayers = new ArrayList<PokerPlayerInfo>();
    
    public PokerMoneyPot(int id)
    {
        m_id = id;
    }
    
    public PokerMoneyPot(int id, int amount)
    {
        m_id = id;
        m_amount = amount;
    }
    
    public void setAmount(int amount)
    {
        m_amount = amount;
    }
    
    public int getAmount()
    {
        return m_amount;
    }
    
    public int getId()
    {
        return m_id;
    }
    
    public List<PokerPlayerInfo> getAttachedPlayers()
    {
        return m_attachedPlayers;
    }
    
    // ////////////////////////////////////
    
    public boolean attachPlayer(PokerPlayerInfo p)
    {
        return m_attachedPlayers.add(p);
    }
    
    public boolean detachPlayer(PokerPlayerInfo p)
    {
        return m_attachedPlayers.remove(p);
    }
    
    public void detachAll()
    {
        m_attachedPlayers.clear();
    }
    
    public void addAmount(int amount)
    {
        m_amount += amount;
    }
}
