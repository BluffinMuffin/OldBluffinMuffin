package protocolLobbyCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import protocol.IPokerCommand;
import protocol.PokerCommand;
import protocolLobby.SummaryTableInfo;

public class LobbyListTableResponseCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "lobbyLIST_TABLES_RESPONSE";
    private final List<SummaryTableInfo> m_tables;
    
    public LobbyListTableResponseCommand(StringTokenizer argsToken)
    {
        m_tables = new ArrayList<SummaryTableInfo>();
        final int count = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < count; ++i)
        {
            m_tables.add(new SummaryTableInfo(argsToken));
        }
    }
    
    public LobbyListTableResponseCommand(List<SummaryTableInfo> tables)
    {
        m_tables = tables;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyListTableResponseCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_tables.size());
        sb.append(PokerCommand.DELIMITER);
        for (final SummaryTableInfo info : m_tables)
        {
            sb.append(info.toString(PokerCommand.DELIMITER));
        }
        return sb.toString();
    }
    
    public List<SummaryTableInfo> getTables()
    {
        return m_tables;
    }
}
