package bluffinmuffin.protocol.commands.lobby.training;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class IdentifyCommand implements ICommand
{
    public static String COMMAND_NAME = "lobbyTRAINING_IDENTIFY";
    
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
