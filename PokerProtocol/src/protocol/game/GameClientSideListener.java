package protocol.game;

import protocol.PokerCommandListener;
import protocol.game.commands.GameBetTurnEndedCommand;
import protocol.game.commands.GameBetTurnStartedCommand;
import protocol.game.commands.GameEndedCommand;
import protocol.game.commands.GameHoleCardsChangedCommand;
import protocol.game.commands.GamePlayerJoinedCommand;
import protocol.game.commands.GamePlayerLeftCommand;
import protocol.game.commands.GamePlayerMoneyChangedCommand;
import protocol.game.commands.GamePlayerTurnBeganCommand;
import protocol.game.commands.GamePlayerTurnEndedCommand;
import protocol.game.commands.GamePlayerWonPotCommand;
import protocol.game.commands.GameStartedCommand;
import protocol.game.commands.GameTableClosedCommand;
import protocol.game.commands.GameTableInfoCommand;

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
