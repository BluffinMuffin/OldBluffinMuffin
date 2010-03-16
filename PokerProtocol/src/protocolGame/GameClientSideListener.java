package protocolGame;

import protocol.PokerCommandListener;
import protocolGameCommands.GameBetTurnEndedCommand;
import protocolGameCommands.GameBetTurnStartedCommand;
import protocolGameCommands.GameEndedCommand;
import protocolGameCommands.GameHoleCardsChangedCommand;
import protocolGameCommands.GamePlayerJoinedCommand;
import protocolGameCommands.GamePlayerLeftCommand;
import protocolGameCommands.GamePlayerMoneyChangedCommand;
import protocolGameCommands.GamePlayerTurnBeganCommand;
import protocolGameCommands.GamePlayerTurnEndedCommand;
import protocolGameCommands.GamePlayerWonPotCommand;
import protocolGameCommands.GameStartedCommand;
import protocolGameCommands.GameTableClosedCommand;
import protocolGameCommands.GameTableInfoCommand;

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
