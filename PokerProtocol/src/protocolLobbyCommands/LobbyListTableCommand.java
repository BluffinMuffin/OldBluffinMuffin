package protocolLobbyCommands;

import java.util.ArrayList;
import java.util.StringTokenizer;

import protocol.IPokerCommand;
import protocol.PokerCommand;
import protocolLobby.SummaryTableInfo;

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
        sb.append(PokerCommand.DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(ArrayList<SummaryTableInfo> tables)
    {
        return new LobbyListTableResponseCommand(tables).encodeCommand();
    }
}
