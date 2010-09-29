package bluffinmuffin.protocol.commands2.lobby;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bluffinmuffin.protocol.TupleTableInfo;

public class ListTableCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return ListTableCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyLIST_TABLES";
    
    public ListTableCommand(StringTokenizer argsToken)
    {
    }
    
    public ListTableCommand()
    {
    }
    
    public String encodeResponse(ArrayList<TupleTableInfo> tables)
    {
        return new ListTableResponse(this, tables).encode();
    }
}
