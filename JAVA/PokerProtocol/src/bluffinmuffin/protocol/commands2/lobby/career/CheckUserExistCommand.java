package bluffinmuffin.protocol.commands2.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.lobby.AbstractLobbyCommand;

public class CheckUserExistCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return CheckUserExistCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_CHECK_USER_EXIST";
    
    private final String m_username;
    
    public CheckUserExistCommand(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
    }
    
    public CheckUserExistCommand(String p_Username)
    {
        m_username = p_Username;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_username);
    }
    
    public String encodeResponse(Boolean exist)
    {
        return new CheckUserExistResponse(this, exist).encode();
    }
    
    public String getUsername()
    {
        return m_username;
    }
}
