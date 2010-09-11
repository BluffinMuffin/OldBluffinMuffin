package bluffinmuffin.protocol.commands.lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import bluffinmuffin.protocol.TupleTableInfo;
import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;


public class ListTableResponse implements ICommand
{
    public static String COMMAND_NAME = "lobbyLIST_TABLES_RESPONSE";
    private final List<TupleTableInfo> m_tables;
    
    public ListTableResponse(StringTokenizer argsToken)
    {
        m_tables = new ArrayList<TupleTableInfo>();
        final int count = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < count; ++i)
        {
            m_tables.add(new TupleTableInfo(argsToken));
        }
    }
    
    public ListTableResponse(List<TupleTableInfo> tables)
    {
        m_tables = tables;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(ListTableResponse.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_tables.size());
        sb.append(Command.L_DELIMITER);
        for (final TupleTableInfo info : m_tables)
        {
            sb.append(info.toString(Command.L_DELIMITER));
        }
        return sb.toString();
    }
    
    public List<TupleTableInfo> getTables()
    {
        return m_tables;
    }
}
