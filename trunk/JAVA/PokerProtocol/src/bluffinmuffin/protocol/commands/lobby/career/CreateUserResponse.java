package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class CreateUserResponse implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_CREATE_USER_RESPONSE";
    
    private final String m_username;
    private final String m_password;
    private final String m_email;
    private final String m_displayName;
    private final boolean m_isOK;
    
    public CreateUserResponse(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
        m_password = argsToken.nextToken();
        m_email = argsToken.nextToken();
        m_displayName = argsToken.nextToken();
        m_isOK = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public CreateUserResponse(String p_Username, String p_Password, String p_Email, String p_DisplayName, boolean ok)
    {
        m_username = p_Username;
        m_password = p_Password;
        m_email = p_Email;
        m_displayName = p_DisplayName;
        m_isOK = ok;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(CreateUserResponse.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_username);
        sb.append(Command.L_DELIMITER);
        sb.append(m_password);
        sb.append(Command.L_DELIMITER);
        sb.append(m_email);
        sb.append(Command.L_DELIMITER);
        sb.append(m_displayName);
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
    
    public String getEmail()
    {
        return m_email;
    }
    
    public String getDisplayName()
    {
        return m_displayName;
    }
    
    public boolean isOK()
    {
        return m_isOK;
    }
}
