package bluffinmuffin.protocol.commands;

import java.util.StringTokenizer;


public class DisconnectCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return DisconnectCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "DISCONNECT";
    
    public DisconnectCommand(StringTokenizer argsToken)
    {
    }
    
    public DisconnectCommand()
    {
    }
}
