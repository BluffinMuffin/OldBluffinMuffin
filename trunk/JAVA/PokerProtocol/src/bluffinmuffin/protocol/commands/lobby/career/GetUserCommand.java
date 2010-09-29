package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.lobby.AbstractLobbyCommand;

public class GetUserCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return GetUserCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_GET_USER";
    
    private final String m_username;
    
    public GetUserCommand(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
    }
    
    public GetUserCommand(String p_Username)
    {
        m_username = p_Username;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_username);
    }
    
    public String encodeResponse(String mail, String display, double money)
    {
        return new GetUserResponse(this, mail, display, money).encode();
    }
    
    public String getUsername()
    {
        return m_username;
    }
}
