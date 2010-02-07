package protocolGameTools;

import java.util.StringTokenizer;

import protocolGame.GameSendActionCommand;
import protocolTools.BluffinCommandReceiver;
import utility.Constants;

public class GameServerSideReceiver extends BluffinCommandReceiver<GameServerSideListener>
{
    @Override
    protected void onLineReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, Constants.DELIMITER);
        final String commandName = token.nextToken();
        
        if (commandName.equals(GameSendActionCommand.COMMAND_NAME))
        {
            sendAction(new GameSendActionCommand(token));
        }
        else
        {
            super.onLineReceived(line);
        }
    }
    
    private void sendAction(GameSendActionCommand lobbyJoinTableCommand)
    {
        for (final GameServerSideListener listener : getSubscribers())
        {
            listener.sendActionCommandReceived(lobbyJoinTableCommand);
        }
    }
}
