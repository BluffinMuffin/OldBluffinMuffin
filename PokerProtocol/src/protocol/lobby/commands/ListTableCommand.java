package protocol.lobby.commands;

import java.util.ArrayList;
import java.util.StringTokenizer;

import protocol.ICommand;
import protocol.Command;
import protocol.lobby.SummaryTableInfo;
import protocol.lobby.commands.response.ListTableResponse;

public class ListTableCommand implements ICommand
{
    public static String COMMAND_NAME = "lobbyLIST_TABLES";
    
    public ListTableCommand(StringTokenizer argsToken)
    {
    }
    
    public ListTableCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(ListTableCommand.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(ArrayList<SummaryTableInfo> tables)
    {
        return new ListTableResponse(tables).encodeCommand();
    }
}
