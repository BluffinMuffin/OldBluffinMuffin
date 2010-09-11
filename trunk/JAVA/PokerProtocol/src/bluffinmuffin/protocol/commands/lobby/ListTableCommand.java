package bluffinmuffin.protocol.commands.lobby;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bluffinmuffin.protocol.TupleTableInfo;
import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;


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
