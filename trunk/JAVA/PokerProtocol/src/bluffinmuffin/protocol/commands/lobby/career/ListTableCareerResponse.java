package bluffinmuffin.protocol.commands.lobby.career;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import bluffinmuffin.protocol.TupleTableInfoCareer;
import bluffinmuffin.protocol.commands.lobby.AbstractLobbyCommand;
import bluffinmuffin.protocol.commands.lobby.AbstractLobbyResponse;
import bluffinmuffin.protocol.commands.lobby.ListTableCommand;

public class ListTableCareerResponse extends AbstractLobbyResponse<ListTableCommand>
{
    @Override
    protected String getCommandName()
    {
        return ListTableCareerResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_LIST_TABLES_RESPONSE";
    private final List<TupleTableInfoCareer> m_tables;
    
    public ListTableCareerResponse(StringTokenizer argsToken)
    {
        super(new ListTableCommand(argsToken));
        m_tables = new ArrayList<TupleTableInfoCareer>();
        final int count = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < count; ++i)
        {
            m_tables.add(new TupleTableInfoCareer(argsToken));
        }
    }
    
    public ListTableCareerResponse(ListTableCommand command, List<TupleTableInfoCareer> tables)
    {
        super(command);
        m_tables = tables;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        super.encode(sb);
        append(sb, m_tables.size());
        for (final TupleTableInfoCareer info : m_tables)
        {
            append(sb, info.toString("" + AbstractLobbyCommand.Delimitter));
        }
    }
    
    public List<TupleTableInfoCareer> getTables()
    {
        return m_tables;
    }
}
