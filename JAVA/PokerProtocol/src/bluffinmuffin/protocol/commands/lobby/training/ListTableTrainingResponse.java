package bluffinmuffin.protocol.commands.lobby.training;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import bluffinmuffin.protocol.TupleTableInfoTraining;
import bluffinmuffin.protocol.commands.lobby.AbstractLobbyCommand;
import bluffinmuffin.protocol.commands.lobby.AbstractLobbyResponse;
import bluffinmuffin.protocol.commands.lobby.ListTableCommand;

public class ListTableTrainingResponse extends AbstractLobbyResponse<ListTableCommand>
{
    @Override
    protected String getCommandName()
    {
        return ListTableTrainingResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyTRAINING_LIST_TABLES_RESPONSE";
    private final List<TupleTableInfoTraining> m_tables;
    
    public ListTableTrainingResponse(StringTokenizer argsToken)
    {
        super(new ListTableCommand(argsToken));
        m_tables = new ArrayList<TupleTableInfoTraining>();
        final int count = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < count; ++i)
        {
            m_tables.add(new TupleTableInfoTraining(argsToken));
        }
    }
    
    public ListTableTrainingResponse(ListTableCommand command, List<TupleTableInfoTraining> tables)
    {
        super(command);
        m_tables = tables;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        super.encode(sb);
        append(sb, m_tables.size());
        for (final TupleTableInfoTraining info : m_tables)
        {
            append(sb, info.toString("" + AbstractLobbyCommand.Delimitter));
        }
    }
    
    public List<TupleTableInfoTraining> getTables()
    {
        return m_tables;
    }
}
