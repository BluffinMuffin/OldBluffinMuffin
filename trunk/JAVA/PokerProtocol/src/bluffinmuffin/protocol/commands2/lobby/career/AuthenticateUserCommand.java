package bluffinmuffin.protocol.commands2.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.lobby.AbstractLobbyCommand;

public class AuthenticateUserCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return AuthenticateUserCommand.COMMAND_NAME;
    }
    
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
    public void encode(StringBuilder sb)
    {
        append(sb, m_username);
        append(sb, m_password);
    }
    
    public String encodeResponse(Boolean success)
    {
        return new AuthenticateUserResponse(this, success).encode();
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
