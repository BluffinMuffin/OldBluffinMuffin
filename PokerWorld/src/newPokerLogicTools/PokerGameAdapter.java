package newPokerLogicTools;

import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;

public class PokerGameAdapter implements PokerGameListener
{
    
    @Override
    public void bigBlindPosted(PokerPlayerInfo p, int bbAmount)
    {
    }
    
    @Override
    public void blindsNeeded(PokerPlayerInfo sb, PokerPlayerInfo bb, int sbValue, int bbValue)
    {
    }
    
    @Override
    public void playerCalled(PokerPlayerInfo p, int playedAmount, int totalCallValue)
    {
    }
    
    @Override
    public void playerFolded(PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerJoined(PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerLeaved(PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerRaised(PokerPlayerInfo p, int playedAmount, int totalRaiseValue)
    {
    }
    
    @Override
    public void smallBlindPosted(PokerPlayerInfo p, int sbAmount)
    {
    }
    
    @Override
    public void actionNeeded(PokerPlayerInfo p, int amountToCall, int maxRaise)
    {
    }
    
    @Override
    public void boardCardsChanged(PokerTableInfo t)
    {
    }
    
    @Override
    public void end()
    {
    }
    
    @Override
    public void playerMoneyChanged(PokerPlayerInfo p)
    {
    }
    
}
