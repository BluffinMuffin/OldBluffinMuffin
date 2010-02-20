package tempServerGameTools;

import gameLogic.GameCard;
import pokerLogic.PokerPlayerAction;
import pokerLogic.Pot;
import tempServerGame.TempServerPokerPlayerInfo;
import tempServerGame.TempServerTableCommunicator;

public class TempServerPokerAdapter implements TempServerPokerListener
{
    
    @Override
    public void bigBlindPosted(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, int value)
    {
        
    }
    
    @Override
    public void endBettingTurn(TempServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void flopDealt(TempServerTableCommunicator comm, GameCard[] cards)
    {
        
    }
    
    @Override
    public void gameEnded(TempServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void gameStarted(TempServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void holeCardDeal(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerEndTurn(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, PokerPlayerAction action)
    {
        
    }
    
    @Override
    public void playerJoinedTable(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerLeftTable(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerMoneyChanged(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerShowCard(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void playerTurnStarted(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        
    }
    
    @Override
    public void potWon(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, Pot pot, int value)
    {
        
    }
    
    @Override
    public void riverDeal(TempServerTableCommunicator comm, GameCard[] cards)
    {
        
    }
    
    @Override
    public void smallBlindPosted(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, int value)
    {
        
    }
    
    @Override
    public void tableEnded(TempServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void tableInfos(TempServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void tableStarted(TempServerTableCommunicator comm)
    {
        
    }
    
    @Override
    public void turnDeal(TempServerTableCommunicator comm, GameCard[] cards)
    {
        
    }
    
    @Override
    public void waitingForPlayers(TempServerTableCommunicator comm)
    {
        
    }
}
