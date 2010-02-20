package protocolLobby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import protocolLobbyTools.SummaryTableInfo;
import protocolTools.IPokerCommand;
import utility.Constants;

public class LobbyListTableCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "lobbyLIST_TABLES";
    
    public LobbyListTableCommand(StringTokenizer argsToken)
    {
    }
    
    public LobbyListTableCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyListTableCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(ArrayList<SummaryTableInfo> tables)
    {
        final StringBuilder sb = new StringBuilder();
        Collections.sort(tables);
        
        for (final SummaryTableInfo table : tables)
        {
            sb.append(table.toString(Constants.DELIMITER));
        }
        
        return sb.toString();
    }
}
