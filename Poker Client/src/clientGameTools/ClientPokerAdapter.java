package clientGameTools;

import java.util.ArrayList;

import pokerLogic.PokerPlayerInfo;
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
    public void gameStarted(PokerPlayerInfo oldDealer, PokerPlayerInfo oldSmallBlind, PokerPlayerInfo oldBigBlind)
    {
        
    }
    
    @Override
    public void playerCardChanged(PokerPlayerInfo player)
    {
        
    }
    
    @Override
    public void playerJoined(PokerPlayerInfo player)
    {
        
    }
    
    @Override
    public void playerLeft(PokerPlayerInfo player)
    {
        
    }
    
    @Override
    public void playerMoneyChanged(PokerPlayerInfo player, int oldMoneyAmount)
    {
        
    }
    
    @Override
    public void playerTurnBegan(PokerPlayerInfo player)
    {
        
    }
    
    @Override
    public void playerTurnEnded(PokerPlayerInfo player, TypePlayerAction action, int actionAmount)
    {
        
    }
    
    @Override
    public void potWon(PokerPlayerInfo player, int potAmountWon, int potIndex)
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
