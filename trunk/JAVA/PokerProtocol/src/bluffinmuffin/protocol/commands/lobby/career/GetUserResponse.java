package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class GetUserResponse implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_GET_USER_RESPONSE";
    
    private final String m_username;
    private final String m_email;
    private final String m_displayName;
    private final double m_money;
    
    public GetUserResponse(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
        m_email = argsToken.nextToken();
        m_displayName = argsToken.nextToken();
        m_money = Double.parseDouble(argsToken.nextToken());
    }
    
    public GetUserResponse(String p_Username, String p_Email, String p_DisplayName, double money)
    {
        m_username = p_Username;
        m_email = p_Email;
        m_displayName = p_DisplayName;
        m_money = money;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GetUserResponse.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_username);
        sb.append(Command.L_DELIMITER);
        sb.append(m_email);
        sb.append(Command.L_DELIMITER);
        sb.append(m_displayName);
        sb.append(Command.L_DELIMITER);
        sb.append(m_money);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String getUsername()
    {
        return m_username;
    }
    
    public String getEmail()
    {
        return m_email;
    }
    
    public String getDisplayName()
    {
        return m_displayName;
    }
    
    public double getMoney()
    {
        return m_money;
    }
}
