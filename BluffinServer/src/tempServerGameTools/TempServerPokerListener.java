package tempServerGameTools;

import gameLogic.GameCard;

import java.util.EventListener;

import pokerLogic.PokerPlayerAction;
import pokerLogic.Pot;
import tempServerGame.TempServerPokerPlayerInfo;
import tempServerGame.TempServerTableCommunicator;

public interface TempServerPokerListener extends EventListener
{
    void bigBlindPosted(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, int value);
    
    void endBettingTurn(TempServerTableCommunicator comm);
    
    void flopDealt(TempServerTableCommunicator comm, GameCard[] cards);
    
    void gameEnded(TempServerTableCommunicator comm);
    
    void gameStarted(TempServerTableCommunicator comm);
    
    void holeCardDeal(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info);
    
    void playerEndTurn(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, PokerPlayerAction action);
    
    void playerJoinedTable(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info);
    
    void playerLeftTable(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info);
    
    void playerMoneyChanged(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info);
    
    void playerShowCard(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info);
    
    void playerTurnStarted(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info);
    
    void potWon(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, Pot pot, int value);
    
    void riverDeal(TempServerTableCommunicator comm, GameCard[] cards);
    
    void smallBlindPosted(TempServerTableCommunicator comm, TempServerPokerPlayerInfo info, int value);
    
    void tableEnded(TempServerTableCommunicator comm);
    
    void tableInfos(TempServerTableCommunicator comm);
    
    void tableStarted(TempServerTableCommunicator comm);
    
    void turnDeal(TempServerTableCommunicator comm, GameCard[] cards);
    
    void waitingForPlayers(TempServerTableCommunicator comm);
}
