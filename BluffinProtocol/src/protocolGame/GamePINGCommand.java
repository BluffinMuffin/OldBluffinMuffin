package protocolGame;

import java.util.StringTokenizer;

import pokerLogic.TypePlayerAction;
import protocolLogic.IBluffinCommand;

public class GamePINGCommand implements IBluffinCommand
{
    public static String COMMAND_NAME = "gamePing";
    
    public GamePINGCommand(StringTokenizer argsToken)
    {
    }
    
    public GamePINGCommand()
    {
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GamePINGCommand.COMMAND_NAME);
        return sb.toString();
    }
    
    public String encodeResponse()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(TypePlayerAction.PONG.name());
        return sb.toString();
    }
}
