package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class CheckUserExistCommand implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_CHECK_USER_EXIST";
    
    private final String m_username;
    
    public CheckUserExistCommand(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
    }
    
    public CheckUserExistCommand(String p_Username)
    {
        m_username = p_Username;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(CheckUserExistCommand.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_username);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(Boolean exist)
    {
        return new CheckUserExistResponse(m_username, exist).encodeCommand();
    }
    
    public String getUsername()
    {
        return m_username;
    }
}
