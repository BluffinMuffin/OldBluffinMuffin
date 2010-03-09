package serverGameTools;

import gameLogic.GameCard;
import pokerLogic.OldPokerPlayerAction;
import pokerLogic.OldPot;
import serverGame.ServerPokerPlayerInfo;
import serverGame.ServerTableCommunicator;

public abstract class ServerPokerAdapter implements ServerPokerListener
{
    
    @Override
    public void bigBlindPosted(ServerTableCommunicator comm, ServerPokerPlayerInfo info, int value)
    {
        
    }
    
    @Override
    public void endBettingTurn(ServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void flopDealt(ServerTableCommunicator comm, GameCard[] cards)
    {
        
    }
    
    @Override
    public void gameEnded(ServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void gameStarted(ServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void holeCardDeal(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerEndTurn(ServerTableCommunicator comm, ServerPokerPlayerInfo info, OldPokerPlayerAction action)
    {
        
    }
    
    @Override
    public void playerJoinedTable(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerLeftTable(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerMoneyChanged(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerShowCard(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerTurnStarted(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void potWon(ServerTableCommunicator comm, ServerPokerPlayerInfo info, OldPot pot, int value)
    {
        
    }
    
    @Override
    public void riverDeal(ServerTableCommunicator comm, GameCard[] cards)
    {
        
    }
    
    @Override
    public void smallBlindPosted(ServerTableCommunicator comm, ServerPokerPlayerInfo info, int value)
    {
        
    }
    
    @Override
    public void tableEnded(ServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void tableInfos(ServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void tableStarted(ServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void turnDeal(ServerTableCommunicator comm, GameCard[] cards)
    {
        
    }
    
    @Override
    public void waitingForPlayers(ServerTableCommunicator comm)
    {
        
    }
}
