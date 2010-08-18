package protocol.observer.game;

import protocol.commands.game.BetTurnEndedCommand;
import protocol.commands.game.BetTurnStartedCommand;
import protocol.commands.game.GameEndedCommand;
import protocol.commands.game.GameStartedCommand;
import protocol.commands.game.PlayerHoleCardsChangedCommand;
import protocol.commands.game.PlayerJoinedCommand;
import protocol.commands.game.PlayerLeftCommand;
import protocol.commands.game.PlayerMoneyChangedCommand;
import protocol.commands.game.PlayerTurnBeganCommand;
import protocol.commands.game.PlayerTurnEndedCommand;
import protocol.commands.game.PlayerWonPotCommand;
import protocol.commands.game.TableClosedCommand;
import protocol.commands.game.TableInfoCommand;
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
