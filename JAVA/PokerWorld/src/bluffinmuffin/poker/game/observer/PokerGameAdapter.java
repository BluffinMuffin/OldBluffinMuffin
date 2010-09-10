package bluffinmuffin.poker.game.observer;

import bluffinmuffin.poker.game.MoneyPot;
import bluffinmuffin.poker.game.PlayerInfo;
import bluffinmuffin.poker.game.TypeAction;
import bluffinmuffin.poker.game.TypeRound;

public class PokerGameAdapter implements IPokerGameListener
{
    
    @Override
    public void gameBlindsNeeded()
    {
    }
    
    @Override
    public void playerJoined(PlayerInfo p)
    {
    }
    
    @Override
    public void playerLeaved(PlayerInfo p)
    {
    }
    
    @Override
    public void playerActionNeeded(PlayerInfo p, PlayerInfo lastPlayer)
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
    public void playerMoneyChanged(PlayerInfo p)
    {
    }
    
    @Override
    public void playerActionTaken(PlayerInfo p, TypeAction reason, int playedAmount)
    {
    }
    
    @Override
    public void gameEnded()
    {
    }
    
    @Override
    public void playerWonPot(PlayerInfo p, MoneyPot pot, int wonAmount)
    {
    }
    
    @Override
    public void playerHoleCardsChanged(PlayerInfo p)
    {
    }
    
    @Override
    public void gameBettingRoundEnded(TypeRound r)
    {
    }
    
    @Override
    public void gameGenerallyUpdated()
    {
    }
    
}
