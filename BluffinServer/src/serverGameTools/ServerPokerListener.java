package serverGameTools;

import gameLogic.GameCard;

import java.util.EventListener;

import pokerLogic.OldPokerPlayerAction;
import pokerLogic.OldPot;
import serverGame.ServerPokerPlayerInfo;
import serverGame.ServerTableCommunicator;

public interface ServerPokerListener extends EventListener
{
    void bigBlindPosted(ServerTableCommunicator comm, ServerPokerPlayerInfo info, int value);
    
    void endBettingTurn(ServerTableCommunicator comm);
    
    void flopDealt(ServerTableCommunicator comm, GameCard[] cards);
    
    void gameEnded(ServerTableCommunicator comm);
    
    void gameStarted(ServerTableCommunicator comm);
    
    void holeCardDeal(ServerTableCommunicator comm, ServerPokerPlayerInfo info);
    
    void playerEndTurn(ServerTableCommunicator comm, ServerPokerPlayerInfo info, OldPokerPlayerAction action);
    
    void playerJoinedTable(ServerTableCommunicator comm, ServerPokerPlayerInfo info);
    
    void playerLeftTable(ServerTableCommunicator comm, ServerPokerPlayerInfo info);
    
    void playerMoneyChanged(ServerTableCommunicator comm, ServerPokerPlayerInfo info);
    
    void playerShowCard(ServerTableCommunicator comm, ServerPokerPlayerInfo info);
    
    void playerTurnStarted(ServerTableCommunicator comm, ServerPokerPlayerInfo info);
    
    void potWon(ServerTableCommunicator comm, ServerPokerPlayerInfo info, OldPot pot, int value);
    
    void riverDeal(ServerTableCommunicator comm, GameCard[] cards);
    
    void smallBlindPosted(ServerTableCommunicator comm, ServerPokerPlayerInfo info, int value);
    
    void tableEnded(ServerTableCommunicator comm);
    
    void tableInfos(ServerTableCommunicator comm);
    
    void tableStarted(ServerTableCommunicator comm);
    
    void turnDeal(ServerTableCommunicator comm, GameCard[] cards);
    
    void waitingForPlayers(ServerTableCommunicator comm);
}
