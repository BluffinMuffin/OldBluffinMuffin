package bluffinmuffin.protocol.commands2.lobby.training;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.lobby.AbstractLobbyResponse;

public class CreateTrainingTableResponse extends AbstractLobbyResponse<CreateTrainingTableCommand>
{
    @Override
    protected String getCommandName()
    {
        return CreateTrainingTableResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyTRAINING_CREATE_TABLE_RESPONSE";
    
    private final int m_ResponsePort;
    
    public CreateTrainingTableResponse(StringTokenizer argsToken)
    {
        super(new CreateTrainingTableCommand(argsToken));
        m_ResponsePort = Integer.parseInt(argsToken.nextToken());
    }
    
    public CreateTrainingTableResponse(CreateTrainingTableCommand command, int responsePort)
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
