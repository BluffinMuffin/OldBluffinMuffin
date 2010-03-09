package clientGameTools;

import java.util.ArrayList;
import java.util.EventListener;

import pokerLogic.OldPokerPlayerInfo;
import pokerLogic.OldTypePlayerAction;
import pokerLogic.OldTypePokerRound;

public interface ClientPokerListener extends EventListener
{
    void betTurnEnded(ArrayList<Integer> potIndices, OldTypePokerRound round);
    
    void boardChanged(ArrayList<Integer> boardCardIndices);
    
    void gameEnded();
    
    void gameStarted(OldPokerPlayerInfo oldDealer, OldPokerPlayerInfo oldSmallBlind, OldPokerPlayerInfo oldBigBlind);
    
    void playerCardChanged(OldPokerPlayerInfo player);
    
    void playerJoined(OldPokerPlayerInfo player);
    
    void playerLeft(OldPokerPlayerInfo player);
    
    void playerMoneyChanged(OldPokerPlayerInfo player, int oldMoneyAmount);
    
    void playerTurnBegan(OldPokerPlayerInfo player);
    
    void playerTurnEnded(OldPokerPlayerInfo player, OldTypePlayerAction action, int actionAmount);
    
    void potWon(OldPokerPlayerInfo player, int potAmountWon, int potIndex);
    
    void tableClosed();
    
    void tableInfos();
    
    void waitingForPlayers();
}
