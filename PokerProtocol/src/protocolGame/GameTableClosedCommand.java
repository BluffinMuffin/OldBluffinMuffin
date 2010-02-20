package protocolGame;

import java.util.StringTokenizer;

import protocolTools.IPokerCommand;

public class GameTableClosedCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "gameTABLE_CLOSED";
    
    public GameTableClosedCommand(StringTokenizer argsToken)
    {
    }
    
    public GameTableClosedCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameTableClosedCommand.COMMAND_NAME);
        return sb.toString();
    }
}
