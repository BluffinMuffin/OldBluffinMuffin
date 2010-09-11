package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class AuthenticateUserCommand implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_AUTHENTICATE_USER";
    
    private final String m_username;
    private final String m_password;
    
    public AuthenticateUserCommand(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
        m_password = argsToken.nextToken();
    }
    
    public AuthenticateUserCommand(String p_Username, String p_Password)
    {
        m_username = p_Username;
        m_password = p_Password;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(AuthenticateUserCommand.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_username);
        sb.append(Command.L_DELIMITER);
        sb.append(m_password);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(Boolean success)
    {
        return new AuthenticateUserResponse(m_username, m_password, success).encodeCommand();
    }
    
    public String getUsername()
    {
        return m_username;
    }
    
    public String getPassword()
    {
        return m_password;
    }
}
