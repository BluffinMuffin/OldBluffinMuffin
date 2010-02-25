package pokerClientGameTools;

import java.util.ArrayList;

import pokerLogic.OldPokerPlayerInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import utility.EventObserver;

public class ClientPokerObserver extends EventObserver<ClientPokerListener>
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
    
    public void gameStarted(OldPokerPlayerInfo oldDealer, OldPokerPlayerInfo oldSmallBlind, OldPokerPlayerInfo oldBigBlind)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.gameStarted(oldDealer, oldSmallBlind, oldBigBlind);
        }
    }
    
    public void playerCardChanged(OldPokerPlayerInfo player)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerCardChanged(player);
        }
    }
    
    public void playerJoined(OldPokerPlayerInfo player)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerJoined(player);
        }
    }
    
    public void playerLeft(OldPokerPlayerInfo player)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerLeft(player);
        }
    }
    
    public void playerMoneyChanged(OldPokerPlayerInfo player, int oldMoneyAmount)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerMoneyChanged(player, oldMoneyAmount);
        }
    }
    
    public void playerTurnBegan(OldPokerPlayerInfo player)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerTurnBegan(player);
        }
    }
    
    public void playerTurnEnded(OldPokerPlayerInfo player, TypePlayerAction action, int actionAmount)
    {
        for (final ClientPokerListener listener : getSubscribers())
        {
            listener.playerTurnEnded(player, action, actionAmount);
        }
    }
    
    public void potWon(OldPokerPlayerInfo player, int potAmountWon, int potIndex)
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
