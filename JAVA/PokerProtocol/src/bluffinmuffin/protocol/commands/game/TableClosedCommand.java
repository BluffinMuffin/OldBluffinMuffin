package bluffinmuffin.protocol.commands.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.AbstractCommand;

public class TableClosedCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return TableClosedCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "gameTABLE_CLOSED";
    
    public TableClosedCommand(StringTokenizer argsToken)
    {
    }
    
    public TableClosedCommand()
    {
    }
}
