package bluffinmuffin.protocol.commands.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.ICommand;


public class GameEndedCommand implements ICommand
{
    public static String COMMAND_NAME = "gameENDED";
    
    public GameEndedCommand(StringTokenizer argsToken)
    {
    }
    
    public GameEndedCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameEndedCommand.COMMAND_NAME);
        return sb.toString();
    }
}
