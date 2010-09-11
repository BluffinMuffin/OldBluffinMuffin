package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class CheckDisplayExistResponse implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_CHECK_DISPLAY_EXIST_RESPONSE";
    
    private final String m_displayName;
    private final boolean m_isExist;
    
    public CheckDisplayExistResponse(StringTokenizer argsToken)
    {
        m_displayName = argsToken.nextToken();
        m_isExist = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public CheckDisplayExistResponse(String name, boolean exist)
    {
        m_displayName = name;
        m_isExist = exist;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(CheckDisplayExistResponse.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_displayName);
        sb.append(Command.L_DELIMITER);
        sb.append(m_isExist);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String getDisplayName()
    {
        return m_displayName;
    }
    
    public boolean isExist()
    {
        return m_isExist;
    }
}
