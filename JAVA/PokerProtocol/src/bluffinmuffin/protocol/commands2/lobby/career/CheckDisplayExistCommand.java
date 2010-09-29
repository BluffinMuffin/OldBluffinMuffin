package bluffinmuffin.protocol.commands2.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.lobby.AbstractLobbyCommand;

public class CheckDisplayExistCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return CheckDisplayExistCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_CHECK_DISPLAY_EXIST";
    
    private final String m_displayName;
    
    public CheckDisplayExistCommand(StringTokenizer argsToken)
    {
        m_displayName = argsToken.nextToken();
    }
    
    public CheckDisplayExistCommand(String p_DisplayName)
    {
        m_displayName = p_DisplayName;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_displayName);
    }
    
    public String encodeResponse(Boolean exist)
    {
        return new CheckDisplayExistResponse(this, exist).encode();
    }
    
    public String getDisplayName()
    {
        return m_displayName;
    }
}
