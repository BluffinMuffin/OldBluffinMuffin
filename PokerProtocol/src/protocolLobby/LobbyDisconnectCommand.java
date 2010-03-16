package protocolLobby;

import java.util.StringTokenizer;

import protocolTools.IPokerCommand;
import protocolTools.PokerCommand;

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
        sb.append(PokerCommand.DELIMITER);
        return sb.toString();
    }
}
