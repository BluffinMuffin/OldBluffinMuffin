package protocolGame;

import java.util.StringTokenizer;

import protocolLogic.IBluffinCommand;

public class GameTableClosedCommand implements IBluffinCommand
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
