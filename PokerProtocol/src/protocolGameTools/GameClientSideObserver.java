package protocolGameTools;

import java.util.StringTokenizer;

import protocol.PokerCommand;
import protocol.PokerCommandObserver;
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

public class GameClientSideObserver extends PokerCommandObserver<GameClientSideListener> implements GameClientSideListener
{
    
    @Override
    public void betTurnEndedCommandReceived(GameBetTurnEndedCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.betTurnEndedCommandReceived(command);
        }
    }
    
    @Override
    public void betTurnStartedCommandReceived(GameBetTurnStartedCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.betTurnStartedCommandReceived(command);
        }
    }
    
    @Override
    public void gameEndedCommandReceived(GameEndedCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.gameEndedCommandReceived(command);
        }
    }
    
    @Override
    public void gameStartedCommandReceived(GameStartedCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.gameStartedCommandReceived(command);
        }
    }
    
    @Override
    public void holeCardsChangedCommandReceived(GameHoleCardsChangedCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.holeCardsChangedCommandReceived(command);
        }
    }
    
    @Override
    public void playerJoinedCommandReceived(GamePlayerJoinedCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerJoinedCommandReceived(command);
        }
    }
    
    @Override
    public void playerLeftCommandReceived(GamePlayerLeftCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerLeftCommandReceived(command);
        }
    }
    
    @Override
    public void playerMoneyChangedCommandReceived(GamePlayerMoneyChangedCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerMoneyChangedCommandReceived(command);
        }
    }
    
    @Override
    public void playerTurnBeganCommandReceived(GamePlayerTurnBeganCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerTurnBeganCommandReceived(command);
        }
    }
    
    @Override
    public void playerTurnEndedCommandReceived(GamePlayerTurnEndedCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerTurnEndedCommandReceived(command);
        }
    }
    
    @Override
    public void playerWonPotCommandReceived(GamePlayerWonPotCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerWonPotCommandReceived(command);
        }
    }
    
    @Override
    public void tableClosedCommandReceived(GameTableClosedCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.tableClosedCommandReceived(command);
        }
    }
    
    @Override
    public void tableInfoCommandReceived(GameTableInfoCommand command)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.tableInfoCommandReceived(command);
        }
    }
    
    @Override
    public void commandReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, PokerCommand.DELIMITER);
        final String commandName = token.nextToken();
        
        if (commandName.equals(GameBetTurnEndedCommand.COMMAND_NAME))
        {
            betTurnEndedCommandReceived(new GameBetTurnEndedCommand(token));
        }
        else if (commandName.equals(GameBetTurnStartedCommand.COMMAND_NAME))
        {
            betTurnStartedCommandReceived(new GameBetTurnStartedCommand(token));
        }
        else if (commandName.equals(GameEndedCommand.COMMAND_NAME))
        {
            gameEndedCommandReceived(new GameEndedCommand(token));
        }
        else if (commandName.equals(GameStartedCommand.COMMAND_NAME))
        {
            gameStartedCommandReceived(new GameStartedCommand(token));
        }
        else if (commandName.equals(GameHoleCardsChangedCommand.COMMAND_NAME))
        {
            holeCardsChangedCommandReceived(new GameHoleCardsChangedCommand(token));
        }
        else if (commandName.equals(GamePlayerJoinedCommand.COMMAND_NAME))
        {
            playerJoinedCommandReceived(new GamePlayerJoinedCommand(token));
        }
        else if (commandName.equals(GamePlayerLeftCommand.COMMAND_NAME))
        {
            playerLeftCommandReceived(new GamePlayerLeftCommand(token));
        }
        else if (commandName.equals(GamePlayerMoneyChangedCommand.COMMAND_NAME))
        {
            playerMoneyChangedCommandReceived(new GamePlayerMoneyChangedCommand(token));
        }
        else if (commandName.equals(GamePlayerTurnBeganCommand.COMMAND_NAME))
        {
            playerTurnBeganCommandReceived(new GamePlayerTurnBeganCommand(token));
        }
        else if (commandName.equals(GamePlayerTurnEndedCommand.COMMAND_NAME))
        {
            playerTurnEndedCommandReceived(new GamePlayerTurnEndedCommand(token));
        }
        else if (commandName.equals(GamePlayerWonPotCommand.COMMAND_NAME))
        {
            playerWonPotCommandReceived(new GamePlayerWonPotCommand(token));
        }
        else if (commandName.equals(GameTableClosedCommand.COMMAND_NAME))
        {
            tableClosedCommandReceived(new GameTableClosedCommand(token));
        }
        else if (commandName.equals(GameTableInfoCommand.COMMAND_NAME))
        {
            tableInfoCommandReceived(new GameTableInfoCommand(token));
        }
        else
        {
            super.commandReceived(line);
        }
    }
}
