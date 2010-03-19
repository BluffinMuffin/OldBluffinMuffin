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

public class GameClientAdapter implements IGameClientListener
{
    
    @Override
    public void betTurnEndedCommandReceived(BetTurnEndedCommand command)
    {
    }
    
    @Override
    public void betTurnStartedCommandReceived(BetTurnStartedCommand command)
    {
    }
    
    @Override
    public void gameEndedCommandReceived(GameEndedCommand command)
    {
    }
    
    @Override
    public void gameStartedCommandReceived(GameStartedCommand command)
    {
    }
    
    @Override
    public void holeCardsChangedCommandReceived(PlayerHoleCardsChangedCommand command)
    {
    }
    
    @Override
    public void playerJoinedCommandReceived(PlayerJoinedCommand command)
    {
    }
    
    @Override
    public void playerLeftCommandReceived(PlayerLeftCommand command)
    {
    }
    
    @Override
    public void playerMoneyChangedCommandReceived(PlayerMoneyChangedCommand command)
    {
    }
    
    @Override
    public void playerTurnBeganCommandReceived(PlayerTurnBeganCommand command)
    {
    }
    
    @Override
    public void playerTurnEndedCommandReceived(PlayerTurnEndedCommand command)
    {
    }
    
    @Override
    public void playerWonPotCommandReceived(PlayerWonPotCommand command)
    {
    }
    
    @Override
    public void tableClosedCommandReceived(TableClosedCommand command)
    {
    }
    
    @Override
    public void tableInfoCommandReceived(TableInfoCommand command)
    {
    }
    
    @Override
    public void commandReceived(String command)
    {
    }
}
