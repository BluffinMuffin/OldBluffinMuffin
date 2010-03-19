package protocol.game.commands;

import java.util.StringTokenizer;

import protocol.ICommand;

public class TableClosedCommand implements ICommand
{
    public static String COMMAND_NAME = "gameTABLE_CLOSED";
    
    public TableClosedCommand(StringTokenizer argsToken)
    {
    }
    
    public TableClosedCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(TableClosedCommand.COMMAND_NAME);
        return sb.toString();
    }
}
