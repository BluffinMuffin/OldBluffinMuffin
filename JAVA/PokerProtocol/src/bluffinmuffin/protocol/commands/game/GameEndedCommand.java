package bluffinmuffin.protocol.commands.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.AbstractCommand;

public class GameEndedCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return GameEndedCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "gameENDED";
    
    public GameEndedCommand(StringTokenizer argsToken)
    {
    }
    
    public GameEndedCommand()
    {
    }
}
