package protocolLobby;

import java.util.StringTokenizer;

import protocolTools.IPokerCommand;
import utility.Constants;

public class LobbyDisconnectCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "lobbyDISCONNECT";
    
    public LobbyDisconnectCommand(StringTokenizer argsToken)
    {
    }
    
    public LobbyDisconnectCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyDisconnectCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        return sb.toString();
    }
}
