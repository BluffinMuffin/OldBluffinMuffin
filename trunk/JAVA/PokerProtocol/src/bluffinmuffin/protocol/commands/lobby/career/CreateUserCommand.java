package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.lobby.AbstractLobbyCommand;

public class CreateUserCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return CreateUserCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_CREATE_USER";
    
    private final String m_username;
    private final String m_password;
    private final String m_email;
    private final String m_displayName;
    
    public CreateUserCommand(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
        m_password = argsToken.nextToken();
        m_email = argsToken.nextToken();
        m_displayName = argsToken.nextToken();
    }
    
    public CreateUserCommand(String p_Username, String p_Password, String p_Email, String p_DisplayName)
    {
        m_username = p_Username;
        m_password = p_Password;
        m_email = p_Email;
        m_displayName = p_DisplayName;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_username);
        append(sb, m_password);
        append(sb, m_email);
        append(sb, m_displayName);
    }
    
    public String encodeResponse(Boolean success)
    {
        return new CreateUserResponse(this, success).encode();
    }
    
    public String getUsername()
    {
        return m_username;
    }
    
    public String getPassword()
    {
        return m_password;
    }
    
    public String getEmail()
    {
        return m_email;
    }
    
    public String getDisplayName()
    {
        return m_displayName;
    }
}
