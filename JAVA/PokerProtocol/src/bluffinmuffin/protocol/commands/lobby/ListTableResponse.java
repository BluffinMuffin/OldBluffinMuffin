package bluffinmuffin.protocol.commands.lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import bluffinmuffin.protocol.TupleTableInfo;

public class ListTableResponse extends AbstractLobbyResponse<ListTableCommand>
{
    @Override
    protected String getCommandName()
    {
        return ListTableResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyLIST_TABLES_RESPONSE";
    private final List<TupleTableInfo> m_tables;
    
    public ListTableResponse(StringTokenizer argsToken)
    {
        super(new ListTableCommand(argsToken));
        m_tables = new ArrayList<TupleTableInfo>();
        final int count = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < count; ++i)
        {
            m_tables.add(new TupleTableInfo(argsToken));
        }
    }
    
    public ListTableResponse(ListTableCommand command, List<TupleTableInfo> tables)
    {
        super(command);
        m_tables = tables;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        super.encode(sb);
        append(sb, m_tables.size());
        for (final TupleTableInfo info : m_tables)
        {
            append(sb, info.toString("" + AbstractLobbyCommand.Delimitter));
        }
    }
    
    public List<TupleTableInfo> getTables()
    {
        return m_tables;
    }
}
