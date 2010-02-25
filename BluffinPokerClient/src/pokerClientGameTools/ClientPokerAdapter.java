package pokerClientGameTools;

import java.util.ArrayList;

import pokerLogic.OldPokerPlayerInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;

public abstract class ClientPokerAdapter implements ClientPokerListener
{
    
    @Override
    public void betTurnEnded(ArrayList<Integer> potIndices, TypePokerRound round)
    {
        
    }
    
    @Override
    public void boardChanged(ArrayList<Integer> boardCardIndices)
    {
        
    }
    
    @Override
    public void gameEnded()
    {
        
    }
    
    @Override
    public void gameStarted(OldPokerPlayerInfo oldDealer, OldPokerPlayerInfo oldSmallBlind, OldPokerPlayerInfo oldBigBlind)
    {
        
    }
    
    @Override
    public void playerCardChanged(OldPokerPlayerInfo player)
    {
        
    }
    
    @Override
    public void playerJoined(OldPokerPlayerInfo player)
    {
        
    }
    
    @Override
    public void playerLeft(OldPokerPlayerInfo player)
    {
        
    }
    
    @Override
    public void playerMoneyChanged(OldPokerPlayerInfo player, int oldMoneyAmount)
    {
        
    }
    
    @Override
    public void playerTurnBegan(OldPokerPlayerInfo player)
    {
        
    }
    
    @Override
    public void playerTurnEnded(OldPokerPlayerInfo player, TypePlayerAction action, int actionAmount)
    {
        
    }
    
    @Override
    public void potWon(OldPokerPlayerInfo player, int potAmountWon, int potIndex)
    {
        
    }
    
    @Override
    public void tableClosed()
    {
        
    }
    
    @Override
    public void tableInfos()
    {
        
    }
    
    @Override
    public void waitingForPlayers()
    {
        
    }
}
