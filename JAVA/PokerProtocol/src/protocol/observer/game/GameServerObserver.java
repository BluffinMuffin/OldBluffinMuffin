package protocol.observer.game;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.DisconnectCommand;
import protocol.commands.game.PlayerPlayMoneyCommand;
import protocol.observer.CommandObserver;

public class GameServerObserver extends CommandObserver<IGameServerListener> implements IGameServerListener
{
    public void playMoneyCommandReceived(PlayerPlayMoneyCommand command)
    {
        for (final IGameServerListener listener : getSubscribers())
        {
            listener.playMoneyCommandReceived(command);
        }
    }
    
    @Override
    public void disconnectCommandReceived(DisconnectCommand command)
    {
        for (final IGameServerListener listener : getSubscribers())
        {
            listener.disconnectCommandReceived(command);
        }
    }
    
    @Override
    public void commandReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, Command.DELIMITER);
        final String commandName = token.nextToken();
        
        if (commandName.equals(PlayerPlayMoneyCommand.COMMAND_NAME))
        {
            playMoneyCommandReceived(new PlayerPlayMoneyCommand(token));
        }
        else if (commandName.equals(DisconnectCommand.COMMAND_NAME))
        {
            disconnectCommandReceived(new DisconnectCommand(token));
        }
        else
        {
            System.out.println("WTF##################: GameServerObserver: should not happen!!! ");
            super.commandReceived(line);
        }
    }
}
