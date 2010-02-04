package protocolLobby;

import java.util.StringTokenizer;

import protocolLogic.IBluffinCommand;
import utility.Constants;

public class LobbyDisconnectCommand implements IBluffinCommand
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
