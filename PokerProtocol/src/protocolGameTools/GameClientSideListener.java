package protocolGameTools;

import protocolGame.GameBetTurnEndedCommand;
import protocolGame.GameBetTurnStartedCommand;
import protocolGame.GameEndedCommand;
import protocolGame.GameHoleCardsChangedCommand;
import protocolGame.GamePlayerJoinedCommand;
import protocolGame.GamePlayerLeftCommand;
import protocolGame.GamePlayerMoneyChangedCommand;
import protocolGame.GamePlayerTurnBeganCommand;
import protocolGame.GamePlayerTurnEndedCommand;
import protocolGame.GamePlayerWonPotCommand;
import protocolGame.GameStartedCommand;
import protocolGame.GameTableClosedCommand;
import protocolGame.GameTableInfoCommand;
import protocolTools.PokerCommandListener;

public interface GameClientSideListener extends PokerCommandListener
{
    
    void betTurnEndedCommandReceived(GameBetTurnEndedCommand command);
    
    void betTurnStartedCommandReceived(GameBetTurnStartedCommand command);
    
    void gameEndedCommandReceived(GameEndedCommand command);
    
    void gameStartedCommandReceived(GameStartedCommand command);
    
    void holeCardsChangedCommandReceived(GameHoleCardsChangedCommand command);
    
    void playerJoinedCommandReceived(GamePlayerJoinedCommand command);
    
    void playerLeftCommandReceived(GamePlayerLeftCommand command);
    
    void playerMoneyChangedCommandReceived(GamePlayerMoneyChangedCommand command);
    
    void playerTurnBeganCommandReceived(GamePlayerTurnBeganCommand command);
    
    void playerTurnEndedCommandReceived(GamePlayerTurnEndedCommand command);
    
    void playerWonPotCommandReceived(GamePlayerWonPotCommand command);
    
    void tableClosedCommandReceived(GameTableClosedCommand command);
    
    void tableInfoCommandReceived(GameTableInfoCommand command);
}
