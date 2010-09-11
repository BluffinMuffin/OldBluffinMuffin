package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class AuthenticateUserResponse implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_AUTHENTICATE_USER_RESPONSE";
    
    private final String m_username;
    private final String m_password;
    private final boolean m_isOK;
    
    public AuthenticateUserResponse(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
        m_password = argsToken.nextToken();
        m_isOK = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public AuthenticateUserResponse(String p_Username, String p_Password, boolean ok)
    {
        m_username = p_Username;
        m_password = p_Password;
        m_isOK = ok;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(AuthenticateUserResponse.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_username);
        sb.append(Command.L_DELIMITER);
        sb.append(m_password);
        sb.append(Command.L_DELIMITER);
        sb.append(m_isOK);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String getUsername()
    {
        return m_username;
    }
    
    public String getPassword()
    {
        return m_password;
    }
    
    public boolean isOK()
    {
        return m_isOK;
    }
}
