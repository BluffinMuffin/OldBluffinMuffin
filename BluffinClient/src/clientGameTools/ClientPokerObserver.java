package clientGameTools;

import java.util.ArrayList;

import pokerLogic.PokerPlayerInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import utility.EventReceiver;

public class ClientPokerObserver extends EventReceiver<ClientPokerListener>
{
    public void betTurnEnded(ArrayList<Integer> potIndices, TypePokerRound round)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.betTurnEnded(potIndices, round);
        }
    }
    
    public void boardChanged(ArrayList<Integer> boardCardIndices)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.boardChanged(boardCardIndices);
        }
    }
    
    public void gameEnded()
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.gameEnded();
        }
    }
    
    public void gameStarted(PokerPlayerInfo oldDealer, PokerPlayerInfo oldSmallBlind, PokerPlayerInfo oldBigBlind)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.gameStarted(oldDealer, oldSmallBlind, oldBigBlind);
        }
    }
    
    public void playerCardChanged(PokerPlayerInfo player)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerCardChanged(player);
        }
    }
    
    public void playerJoined(PokerPlayerInfo player)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerJoined(player);
        }
    }
    
    public void playerLeft(PokerPlayerInfo player)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerLeft(player);
        }
    }
    
    public void playerMoneyChanged(PokerPlayerInfo player, int oldMoneyAmount)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerMoneyChanged(player, oldMoneyAmount);
        }
    }
    
    public void playerTurnBegan(PokerPlayerInfo player)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerTurnBegan(player);
        }
    }
    
    public void playerTurnEnded(PokerPlayerInfo player, TypePlayerAction action, int actionAmount)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerTurnEnded(player, action, actionAmount);
        }
    }
    
    public void potWon(PokerPlayerInfo player, int potAmountWon, int potIndex)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.potWon(player, potAmountWon, potIndex);
        }
    }
    
    public void tableClosed()
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.tableClosed();
        }
    }
    
    public void tableInfos()
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.tableInfos();
        }
    }
    
    public void waitingForPlayers()
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.waitingForPlayers();
        }
        
    }
}
