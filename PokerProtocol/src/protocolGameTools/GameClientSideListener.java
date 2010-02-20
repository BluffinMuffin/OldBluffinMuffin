package protocolGameTools;

import protocolGame.GameAskActionCommand;
import protocolGame.GameBetTurnEndedCommand;
import protocolGame.GameBoardChangedCommand;
import protocolGame.GameEndedCommand;
import protocolGame.GameHoleCardsChangedCommand;
import protocolGame.GamePINGCommand;
import protocolGame.GamePlayerJoinedCommand;
import protocolGame.GamePlayerLeftCommand;
import protocolGame.GamePlayerMoneyChangedCommand;
import protocolGame.GamePlayerTurnBeganCommand;
import protocolGame.GamePlayerTurnEndedCommand;
import protocolGame.GamePlayerWonPotCommand;
import protocolGame.GameStartedCommand;
import protocolGame.GameTableClosedCommand;
import protocolGame.GameTableInfoCommand;
import protocolGame.GameWaitingCommand;
import protocolTools.PokerCommandListener;

public interface GameClientSideListener extends PokerCommandListener
{
    void askActionCommandReceived(GameAskActionCommand command);
    
    void betTurnEndedCommandReceived(GameBetTurnEndedCommand command);
    
    void boardChangedCommandReceived(GameBoardChangedCommand command);
    
    void gameEndedCommandReceived(GameEndedCommand command);
    
    void gameStartedCommandReceived(GameStartedCommand command);
    
    void holeCardsChangedCommandReceived(GameHoleCardsChangedCommand command);
    
    void pingCommandReceived(GamePINGCommand command);
    
    void playerJoinedCommandReceived(GamePlayerJoinedCommand command);
    
    void playerLeftCommandReceived(GamePlayerLeftCommand command);
    
    void playerMoneyChangedCommandReceived(GamePlayerMoneyChangedCommand command);
    
    void playerTurnBeganCommandReceived(GamePlayerTurnBeganCommand command);
    
    void playerTurnEndedCommandReceived(GamePlayerTurnEndedCommand command);
    
    void playerWonPotCommandReceived(GamePlayerWonPotCommand command);
    
    void tableClosedCommandReceived(GameTableClosedCommand command);
    
    void tableInfoCommandReceived(GameTableInfoCommand command);
    
    void waitingCommandReceived(GameWaitingCommand command);
}
