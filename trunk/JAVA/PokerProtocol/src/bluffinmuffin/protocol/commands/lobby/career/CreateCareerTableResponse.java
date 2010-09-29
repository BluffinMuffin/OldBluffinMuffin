package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.lobby.AbstractLobbyResponse;

public class CreateCareerTableResponse extends AbstractLobbyResponse<CreateCareerTableCommand>
{
    @Override
    protected String getCommandName()
    {
        return CreateCareerTableResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_CREATE_TABLE_RESPONSE";
    private final int m_ResponsePort;
    
    public CreateCareerTableResponse(StringTokenizer argsToken)
    {
        super(new CreateCareerTableCommand(argsToken));
        m_ResponsePort = Integer.parseInt(argsToken.nextToken());
    }
    
    public CreateCareerTableResponse(CreateCareerTableCommand command, int responsePort)
    {
        super(command);
        m_ResponsePort = responsePort;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        super.encode(sb);
        append(sb, m_ResponsePort);
    }
    
    public int getResponsePort()
    {
        return m_ResponsePort;
    }
    
}
