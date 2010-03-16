package pokerGameTools;

import pokerGameLogic.PokerMoneyPot;
import pokerGameLogic.PokerPlayerInfo;
import pokerGameLogic.TypePokerGameAction;
import pokerGameLogic.TypePokerGameRound;

public class PokerGameAdapter implements PokerGameListener
{
    
    @Override
    public void gameBlindsNeeded()
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
    public void playerActionNeeded(PokerPlayerInfo p)
    {
    }
    
    @Override
    public void gameBettingRoundStarted()
    {
    }
    
    @Override
    public void everythingEnded()
    {
    }
    
    @Override
    public void playerMoneyChanged(PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerActionTaken(PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount)
    {
    }
    
    @Override
    public void gameEnded()
    {
    }
    
    @Override
    public void playerWonPot(PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount)
    {
    }
    
    @Override
    public void playerHoleCardsChanged(PokerPlayerInfo p)
    {
    }
    
    @Override
    public void gameBettingRoundEnded(TypePokerGameRound r)
    {
    }
    
    @Override
    public void gameGenerallyUpdated()
    {
    }
    
}