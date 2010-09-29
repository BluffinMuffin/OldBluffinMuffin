package bluffinmuffin.protocol.commands2.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.lobby.AbstractLobbyResponse;

public class CreateUserResponse extends AbstractLobbyResponse<CreateUserCommand>
{
    @Override
    protected String getCommandName()
    {
        return CreateUserResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_CREATE_USER_RESPONSE";
    private final boolean m_isOK;
    
    public CreateUserResponse(StringTokenizer argsToken)
    {
        super(new CreateUserCommand(argsToken));
        m_isOK = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public CreateUserResponse(CreateUserCommand command, boolean ok)
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
