package clientGameTools;

import java.util.ArrayList;
import java.util.EventListener;

import pokerLogic.PokerPlayerInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;

public interface ClientPokerListener extends EventListener
{
    void betTurnEnded(ArrayList<Integer> potIndices, TypePokerRound round);
    
    void boardChanged(ArrayList<Integer> boardCardIndices);
    
    void gameEnded();
    
    void gameStarted(PokerPlayerInfo oldDealer, PokerPlayerInfo oldSmallBlind, PokerPlayerInfo oldBigBlind);
    
    void playerCardChanged(PokerPlayerInfo player);
    
    void playerJoined(PokerPlayerInfo player);
    
    void playerLeft(PokerPlayerInfo player);
    
    void playerMoneyChanged(PokerPlayerInfo player, int oldMoneyAmount);
    
    void playerTurnBegan(PokerPlayerInfo player);
    
    void playerTurnEnded(PokerPlayerInfo player, TypePlayerAction action, int actionAmount);
    
    void potWon(PokerPlayerInfo player, int potAmountWon, int potIndex);
    
    void tableClosed();
    
    void tableInfos();
    
    void waitingForPlayers();
}
