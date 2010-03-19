package protocol.lobby.commands.response;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import protocol.ICommand;
import protocol.Command;
import protocol.lobby.SummaryTableInfo;

public class ListTableResponse implements ICommand
{
    public static String COMMAND_NAME = "lobbyLIST_TABLES_RESPONSE";
    private final List<SummaryTableInfo> m_tables;
    
    public ListTableResponse(StringTokenizer argsToken)
    {
        m_tables = new ArrayList<SummaryTableInfo>();
        final int count = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < count; ++i)
        {
            m_tables.add(new SummaryTableInfo(argsToken));
        }
    }
    
    public ListTableResponse(List<SummaryTableInfo> tables)
    {
        m_tables = tables;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(ListTableResponse.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        sb.append(m_tables.size());
        sb.append(Command.DELIMITER);
        for (final SummaryTableInfo info : m_tables)
        {
            sb.append(info.toString(Command.DELIMITER));
        }
        return sb.toString();
    }
    
    public List<SummaryTableInfo> getTables()
    {
        return m_tables;
    }
}
