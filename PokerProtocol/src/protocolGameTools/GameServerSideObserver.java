package protocolGameTools;

import java.util.StringTokenizer;

import protocolGame.GamePlayMoneyCommand;
import protocolTools.PokerCommandObserver;
import utility.Constants;

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
        final StringTokenizer token = new StringTokenizer(line, Constants.DELIMITER);
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
