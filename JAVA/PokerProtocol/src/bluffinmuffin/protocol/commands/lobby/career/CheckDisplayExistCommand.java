package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class CheckDisplayExistCommand implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_CHECK_DISPLAY_EXIST";
    
    private final String m_displayName;
    
    public CheckDisplayExistCommand(StringTokenizer argsToken)
    {
        m_displayName = argsToken.nextToken();
    }
    
    public CheckDisplayExistCommand(String p_DisplayName)
    {
        m_displayName = p_DisplayName;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(CheckDisplayExistCommand.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_displayName);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(Boolean exist)
    {
        return new CheckDisplayExistResponse(m_displayName, exist).encodeCommand();
    }
    
    public String getDisplayName()
    {
        return m_displayName;
    }
}
