package protocol.commands.lobby;

import java.util.ArrayList;
import java.util.StringTokenizer;

import protocol.TupleTableInfo;
import protocol.commands.Command;
import protocol.commands.ICommand;
import protocol.commands.lobby.response.ListTableResponse;

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
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(ArrayList<TupleTableInfo> tables)
    {
        return new ListTableResponse(tables).encodeCommand();
    }
}
