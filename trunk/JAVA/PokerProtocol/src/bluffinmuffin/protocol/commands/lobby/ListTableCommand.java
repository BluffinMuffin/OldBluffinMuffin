package bluffinmuffin.protocol.commands.lobby;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bluffinmuffin.protocol.TupleTableInfoCareer;
import bluffinmuffin.protocol.TupleTableInfoTraining;
import bluffinmuffin.protocol.commands.lobby.career.ListTableCareerResponse;
import bluffinmuffin.protocol.commands.lobby.training.ListTableTrainingResponse;

public class ListTableCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return ListTableCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyLIST_TABLES";
    private final boolean m_training;
    
    public ListTableCommand(StringTokenizer argsToken)
    {
        m_training = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public ListTableCommand(boolean training)
    {
        m_training = training;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_training);
    }
    
    public String encodeCareerResponse(ArrayList<TupleTableInfoCareer> tables)
    {
        return new ListTableCareerResponse(this, tables).encode();
    }
    
    public String encodeTrainingResponse(ArrayList<TupleTableInfoTraining> tables)
    {
        return new ListTableTrainingResponse(this, tables).encode();
    }
    
    public boolean getTraining()
    {
        return m_training;
    }
}
