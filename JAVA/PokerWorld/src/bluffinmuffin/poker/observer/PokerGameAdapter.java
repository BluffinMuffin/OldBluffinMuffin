package bluffinmuffin.poker.observer;

import bluffinmuffin.poker.entities.PotInfo;
import bluffinmuffin.poker.entities.PlayerInfo;
import bluffinmuffin.poker.entities.type.PlayerActionType;
import bluffinmuffin.poker.entities.type.GameRoundType;

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
    public void playerActionTaken(PlayerInfo p, PlayerActionType reason, int playedAmount)
    {
    }
    
    @Override
    public void gameEnded()
    {
    }
    
    @Override
    public void playerWonPot(PlayerInfo p, PotInfo pot, int wonAmount)
    {
    }
    
    @Override
    public void playerHoleCardsChanged(PlayerInfo p)
    {
    }
    
    @Override
    public void gameBettingRoundEnded(GameRoundType r)
    {
    }
    
    @Override
    public void gameGenerallyUpdated()
    {
    }
    
}
