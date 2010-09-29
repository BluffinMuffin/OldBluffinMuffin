package bluffinmuffin.protocol.commands2.lobby.training;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.lobby.AbstractLobbyResponse;

public class IdentifyResponse extends AbstractLobbyResponse<IdentifyCommand>
{
    @Override
    protected String getCommandName()
    {
        return IdentifyResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyIDENTIFY_TRAINING_RESPONSE";
    
    private final boolean m_isOK;
    
    public IdentifyResponse(StringTokenizer argsToken)
    {
        super(new IdentifyCommand(argsToken));
        m_isOK = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public IdentifyResponse(IdentifyCommand command, boolean ok)
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
