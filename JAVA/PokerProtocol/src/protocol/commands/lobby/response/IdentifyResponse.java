package protocol.commands.lobby.response;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.ICommand;

public class IdentifyResponse implements ICommand
{
    public static String COMMAND_NAME = "lobbyIDENTIFY_RESPONSE";
    
    private final String m_playerName;
    private final boolean m_isOK;
    
    public IdentifyResponse(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
        m_isOK = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public IdentifyResponse(String name, boolean ok)
    {
        m_playerName = name;
        m_isOK = ok;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(IdentifyResponse.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_playerName);
        sb.append(Command.L_DELIMITER);
        sb.append(m_isOK);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public boolean isOK()
    {
        return m_isOK;
    }
}
