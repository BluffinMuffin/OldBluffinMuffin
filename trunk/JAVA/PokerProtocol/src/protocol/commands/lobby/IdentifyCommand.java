package protocol.commands.lobby;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.ICommand;
import protocol.commands.lobby.response.IdentifyResponse;

public class IdentifyCommand implements ICommand
{
    public static String COMMAND_NAME = "lobbyIDENTIFY";
    
    private final String m_playerName;
    
    public IdentifyCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
    }
    
    public IdentifyCommand(String name)
    {
        m_playerName = name;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(IdentifyCommand.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_playerName);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(Boolean success)
    {
        return new IdentifyResponse(m_playerName, success).encodeCommand();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
}
