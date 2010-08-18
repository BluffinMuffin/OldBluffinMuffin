package protocol.commands;

import java.util.StringTokenizer;


public class DisconnectCommand implements ICommand
{
    public static String COMMAND_NAME = "DISCONNECT";
    
    public DisconnectCommand(StringTokenizer argsToken)
    {
    }
    
    public DisconnectCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(DisconnectCommand.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        return sb.toString();
    }
}
