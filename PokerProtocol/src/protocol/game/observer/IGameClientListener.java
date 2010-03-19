package protocol.game.observer;

import protocol.game.commands.BetTurnEndedCommand;
import protocol.game.commands.BetTurnStartedCommand;
import protocol.game.commands.GameEndedCommand;
import protocol.game.commands.PlayerHoleCardsChangedCommand;
import protocol.game.commands.PlayerJoinedCommand;
import protocol.game.commands.PlayerLeftCommand;
import protocol.game.commands.PlayerMoneyChangedCommand;
import protocol.game.commands.PlayerTurnBeganCommand;
import protocol.game.commands.PlayerTurnEndedCommand;
import protocol.game.commands.PlayerWonPotCommand;
import protocol.game.commands.GameStartedCommand;
import protocol.game.commands.TableClosedCommand;
import protocol.game.commands.TableInfoCommand;
import protocol.observer.ICommandListener;

public interface IGameClientListener extends ICommandListener
{
    
    void betTurnEndedCommandReceived(BetTurnEndedCommand command);
    
    void betTurnStartedCommandReceived(BetTurnStartedCommand command);
    
    void gameEndedCommandReceived(GameEndedCommand command);
    
    void gameStartedCommandReceived(GameStartedCommand command);
    
    void holeCardsChangedCommandReceived(PlayerHoleCardsChangedCommand command);
    
    void playerJoinedCommandReceived(PlayerJoinedCommand command);
    
    void playerLeftCommandReceived(PlayerLeftCommand command);
    
    void playerMoneyChangedCommandReceived(PlayerMoneyChangedCommand command);
    
    void playerTurnBeganCommandReceived(PlayerTurnBeganCommand command);
    
    void playerTurnEndedCommandReceived(PlayerTurnEndedCommand command);
    
    void playerWonPotCommandReceived(PlayerWonPotCommand command);
    
    void tableClosedCommandReceived(TableClosedCommand command);
    
    void tableInfoCommandReceived(TableInfoCommand command);
}
