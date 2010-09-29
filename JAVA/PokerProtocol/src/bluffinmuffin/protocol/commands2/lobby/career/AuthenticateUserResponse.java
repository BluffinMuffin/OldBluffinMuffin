package bluffinmuffin.protocol.commands2.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.lobby.AbstractLobbyResponse;

public class AuthenticateUserResponse extends AbstractLobbyResponse<AuthenticateUserCommand>
{
    @Override
    protected String getCommandName()
    {
        return AuthenticateUserResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_AUTHENTICATE_USER_RESPONSE";
    
    private final boolean m_isOK;
    
    public AuthenticateUserResponse(StringTokenizer argsToken)
    {
        super(new AuthenticateUserCommand(argsToken));
        m_isOK = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public AuthenticateUserResponse(AuthenticateUserCommand command, boolean ok)
    {
        super(command);
        m_isOK = ok;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        super.encode(sb);
        append(sb, m_isOK);
    }
    
    public boolean isOK()
    {
        return m_isOK;
    }
}
