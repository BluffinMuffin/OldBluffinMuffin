package tempServerGameTools;

import gameLogic.GameCard;
import pokerLogic.PokerPlayerAction;
import pokerLogic.Pot;
import tempServerGame.TempServerPokerPlayerInfo;
import tempServerGame.TempServerTableCommunicator;
import utility.EventObserver;

public class TempServerPokerObserver extends EventObserver<TempServerPokerListener> implements TempServerPokerListener
{
    public void bigBlindPosted(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, int value)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.bigBlindPosted(comm, info, value);
        }
    }
    
    public void endBettingTurn(TempServerTableCommunicator comm)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.endBettingTurn(comm);
        }
    }
    
    public void flopDealt(TempServerTableCommunicator comm, GameCard[] cards)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.flopDealt(comm, cards);
        }
    }
    
    public void gameEnded(TempServerTableCommunicator comm)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.gameEnded(comm);
        }
    }
    
    public void gameStarted(TempServerTableCommunicator comm)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.gameStarted(comm);
        }
    }
    
    public void holeCardDeal(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.holeCardDeal(comm, info);
        }
    }
    
    public void playerEndTurn(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, PokerPlayerAction action)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.playerEndTurn(comm, info, action);
        }
    }
    
    public void playerJoinedTable(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.playerJoinedTable(comm, info);
        }
    }
    
    public void playerLeftTable(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.playerLeftTable(comm, info);
        }
    }
    
    public void playerMoneyChanged(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.playerMoneyChanged(comm, info);
        }
    }
    
    public void playerShowCard(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.playerShowCard(comm, info);
        }
    }
    
    public void playerTurnStarted(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.playerTurnStarted(comm, info);
        }
    }
    
    public void potWon(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, Pot pot, int value)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.potWon(comm, info, pot, value);
        }
    }
    
    public void riverDeal(TempServerTableCommunicator comm, GameCard[] cards)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.riverDeal(comm, cards);
        }
    }
    
    public void smallBlindPosted(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, int value)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.smallBlindPosted(comm, info, value);
        }
    }
    
    public void tableEnded(TempServerTableCommunicator comm)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.tableEnded(comm);
        }
    }
    
    public void tableInfos(TempServerTableCommunicator comm)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.tableInfos(comm);
        }
    }
    
    public void tableStarted(TempServerTableCommunicator comm)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.tableStarted(comm);
        }
    }
    
    public void turnDeal(TempServerTableCommunicator comm, GameCard[] cards)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.turnDeal(comm, cards);
        }
    }
    
    public void waitingForPlayers(TempServerTableCommunicator comm)
    {
        for (final TempServerPokerListener listener : getSubscribers())
        {
            listener.waitingForPlayers(comm);
        }
    }
}
