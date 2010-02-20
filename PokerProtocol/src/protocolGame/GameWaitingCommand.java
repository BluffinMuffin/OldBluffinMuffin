package protocolGame;

import java.util.StringTokenizer;

import protocolTools.IPokerCommand;

public class GameWaitingCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "gameWAITING";
    
    public GameWaitingCommand(StringTokenizer argsToken)
    {
    }
    
    public GameWaitingCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameWaitingCommand.COMMAND_NAME);
        return sb.toString();
    }
}
