package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class CheckUserExistResponse implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_CHECK_USER_EXIST_RESPONSE";
    
    private final String m_username;
    private final boolean m_isExist;
    
    public CheckUserExistResponse(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
        m_isExist = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public CheckUserExistResponse(String p_Username, boolean exist)
    {
        m_username = p_Username;
        m_isExist = exist;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(CheckUserExistResponse.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_username);
        sb.append(Command.L_DELIMITER);
        sb.append(m_isExist);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String getUsername()
    {
        return m_username;
    }
    
    public boolean isExist()
    {
        return m_isExist;
    }
}
