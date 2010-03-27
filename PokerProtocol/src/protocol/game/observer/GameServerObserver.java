package protocol.game.observer;

import java.util.StringTokenizer;

import protocol.Command;
import protocol.commands.DisconnectCommand;
import protocol.game.commands.PlayerPlayMoneyCommand;
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
    public void disconnectCommandReceived()
    {
        for (final IGameServerListener listener : getSubscribers())
        {
            listener.disconnectCommandReceived();
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
        	disconnectCommandReceived();
        }
        else
        {
        	System.out.println("WTF##################: GameServerObserver: should not happen!!! ");
            super.commandReceived(line);
        }
    }
}
