package bluffinmuffin.protocol.commands2.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.lobby.AbstractLobbyResponse;

public class GetUserResponse extends AbstractLobbyResponse<GetUserCommand>
{
    @Override
    protected String getCommandName()
    {
        return GetUserResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_GET_USER_RESPONSE";
    
    private final String m_email;
    private final String m_displayName;
    private final double m_money;
    
    public GetUserResponse(StringTokenizer argsToken)
    {
        super(new GetUserCommand(argsToken));
        m_email = argsToken.nextToken();
        m_displayName = argsToken.nextToken();
        m_money = Double.parseDouble(argsToken.nextToken());
    }
    
    public GetUserResponse(GetUserCommand command, String p_Email, String p_DisplayName, double money)
    {
        super(command);
        m_email = p_Email;
        m_displayName = p_DisplayName;
        m_money = money;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        super.encode(sb);
        append(sb, m_email);
        append(sb, m_displayName);
        append(sb, m_money);
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
