package protocol.game.commands;

import java.util.StringTokenizer;

import protocol.IPokerCommand;

public class GameDisconnectCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "gameDisconnect";
    
    public GameDisconnectCommand(StringTokenizer argsToken)
    {
    }
    
    public GameDisconnectCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameDisconnectCommand.COMMAND_NAME);
        return sb.toString();
    }
}
