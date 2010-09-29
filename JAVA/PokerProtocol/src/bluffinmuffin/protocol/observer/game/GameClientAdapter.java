package bluffinmuffin.protocol.observer.game;

import bluffinmuffin.protocol.commands.game.BetTurnEndedCommand;
import bluffinmuffin.protocol.commands.game.BetTurnStartedCommand;
import bluffinmuffin.protocol.commands.game.GameEndedCommand;
import bluffinmuffin.protocol.commands.game.GameStartedCommand;
import bluffinmuffin.protocol.commands.game.PlayerHoleCardsChangedCommand;
import bluffinmuffin.protocol.commands.game.PlayerJoinedCommand;
import bluffinmuffin.protocol.commands.game.PlayerLeftCommand;
import bluffinmuffin.protocol.commands.game.PlayerMoneyChangedCommand;
import bluffinmuffin.protocol.commands.game.PlayerTurnBeganCommand;
import bluffinmuffin.protocol.commands.game.PlayerTurnEndedCommand;
import bluffinmuffin.protocol.commands.game.PlayerWonPotCommand;
import bluffinmuffin.protocol.commands.game.TableClosedCommand;
import bluffinmuffin.protocol.commands.game.TableInfoCommand;

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
