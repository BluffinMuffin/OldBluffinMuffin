package protocol.game;

import java.util.StringTokenizer;

import protocol.PokerCommand;
import protocol.PokerCommandObserver;
import protocol.game.commands.GamePlayMoneyCommand;

public class GameServerSideObserver extends PokerCommandObserver<GameServerSideListener> implements GameServerSideListener
{
    public void playMoneyCommandReceived(GamePlayMoneyCommand command)
    {
        for (final GameServerSideListener listener : getSubscribers())
        {
            listener.playMoneyCommandReceived(command);
        }
    }
    
    @Override
    public void disconnectCommandReceived(GamePlayMoneyCommand command)
    {
        for (final GameServerSideListener listener : getSubscribers())
        {
            listener.disconnectCommandReceived(command);
        }
    }
    
    @Override
    public void commandReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, PokerCommand.DELIMITER);
        final String commandName = token.nextToken();
        
        if (commandName.equals(GamePlayMoneyCommand.COMMAND_NAME))
        {
            playMoneyCommandReceived(new GamePlayMoneyCommand(token));
        }
        else
        {
            super.commandReceived(line);
        }
    }
}
