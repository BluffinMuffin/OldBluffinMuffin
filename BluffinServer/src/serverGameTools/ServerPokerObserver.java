package serverGameTools;

import newPokerLogic.GameCard;
import pokerLogic.PokerPlayerAction;
import pokerLogic.Pot;
import serverGame.ServerPokerPlayerInfo;
import serverGame.ServerTableCommunicator;
import utility.EventReceiver;

public class ServerPokerObserver extends EventReceiver<ServerPokerListener>
{
    public void bigBlindPosted(ServerTableCommunicator comm, ServerPokerPlayerInfo info, int value)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.bigBlindPosted(comm, info, value);
        }
    }
    
    public void endBettingTurn(ServerTableCommunicator comm)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.endBettingTurn(comm);
        }
    }
    
    public void flopDealt(ServerTableCommunicator comm, GameCard[] cards)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.flopDealt(comm, cards);
        }
    }
    
    public void gameEnded(ServerTableCommunicator comm)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.gameEnded(comm);
        }
    }
    
    public void gameStarted(ServerTableCommunicator comm)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.gameStarted(comm);
        }
    }
    
    public void holeCardDeal(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.holeCardDeal(comm, info);
        }
    }
    
    public void playerEndTurn(ServerTableCommunicator comm, ServerPokerPlayerInfo info, PokerPlayerAction action)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.playerEndTurn(comm, info, action);
        }
    }
    
    public void playerJoinedTable(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.playerJoinedTable(comm, info);
        }
    }
    
    public void playerLeftTable(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.playerLeftTable(comm, info);
        }
    }
    
    public void playerMoneyChanged(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.playerMoneyChanged(comm, info);
        }
    }
    
    public void playerShowCard(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.playerShowCard(comm, info);
        }
    }
    
    public void playerTurnStarted(ServerTableCommunicator comm, ServerPokerPlayerInfo info)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.playerTurnStarted(comm, info);
        }
    }
    
    public void potWon(ServerTableCommunicator comm, ServerPokerPlayerInfo info, Pot pot, int value)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.potWon(comm, info, pot, value);
        }
    }
    
    public void riverDeal(ServerTableCommunicator comm, GameCard[] cards)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.riverDeal(comm, cards);
        }
    }
    
    public void smallBlindPosted(ServerTableCommunicator comm, ServerPokerPlayerInfo info, int value)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.smallBlindPosted(comm, info, value);
        }
    }
    
    public void tableEnded(ServerTableCommunicator comm)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.tableEnded(comm);
        }
    }
    
    public void tableInfos(ServerTableCommunicator comm)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.tableInfos(comm);
        }
    }
    
    public void tableStarted(ServerTableCommunicator comm)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.tableStarted(comm);
        }
    }
    
    public void turnDeal(ServerTableCommunicator comm, GameCard[] cards)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.turnDeal(comm, cards);
        }
    }
    
    public void waitingForPlayers(ServerTableCommunicator comm)
    {
        for (final ServerPokerListener listener : getSubscribers())
        {
            listener.waitingForPlayers(comm);
        }
    }
}
