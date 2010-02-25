package newPokerLogicTools;

import newPokerLogic.PokerMoneyPot;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogic.TypePokerGameAction;
import newPokerLogic.TypePokerGameRound;

public class PokerGameAdapter implements PokerGameListener
{
    
    @Override
    public void gameBlindsNeeded(PokerTableInfo t)
    {
    }
    
    @Override
    public void playerJoined(PokerTableInfo t, PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerLeaved(PokerTableInfo t, PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerActionNeeded(PokerTableInfo t, PokerPlayerInfo p)
    {
    }
    
    @Override
    public void gameBoardCardsChanged(PokerTableInfo t)
    {
    }
    
    @Override
    public void everythingEnded(PokerTableInfo t)
    {
    }
    
    @Override
    public void playerMoneyChanged(PokerTableInfo t, PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerActionTaken(PokerTableInfo t, PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount)
    {
    }
    
    @Override
    public void gameEnded(PokerTableInfo t)
    {
    }
    
    @Override
    public void playerWonPot(PokerTableInfo t, PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount)
    {
    }
    
    @Override
    public void playerHoleCardsChanged(PokerTableInfo t, PokerPlayerInfo p)
    {
    }
    
    @Override
    public void gameBettingRoundEnded(PokerTableInfo t, TypePokerGameRound r)
    {
    }
    
    @Override
    public void gameGenerallyUpdated(PokerTableInfo t)
    {
    }
    
}
