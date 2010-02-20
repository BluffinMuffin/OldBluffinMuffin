package protocolLobby;

import java.util.StringTokenizer;

import protocolTools.IPokerCommand;
import utility.Constants;

public class LobbyConnectCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "lobbyCONNECT";
    
    private final String m_playerName;
    
    public LobbyConnectCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
    }
    
    public LobbyConnectCommand(String name)
    {
        m_playerName = name;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyConnectCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_playerName);
        sb.append(Constants.DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(Boolean success)
    {
        return success.toString();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
}
