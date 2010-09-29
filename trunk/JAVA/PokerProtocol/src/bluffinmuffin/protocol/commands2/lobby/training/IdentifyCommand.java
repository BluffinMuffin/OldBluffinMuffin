package bluffinmuffin.protocol.commands2.lobby.training;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.lobby.AbstractLobbyCommand;

public class IdentifyCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return IdentifyCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyTRAINING_IDENTIFY";
    
    private final String m_playerName;
    
    public IdentifyCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
    }
    
    public IdentifyCommand(String name)
    {
        m_playerName = name;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_playerName);
    }
    
    public String encodeResponse(Boolean success)
    {
        return new IdentifyResponse(this, success).encode();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
}
