package protocolGame;

import java.util.StringTokenizer;

import pokerLogic.OldTypePlayerAction;
import protocolTools.IPokerCommand;

public class GamePINGCommand implements IPokerCommand
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
        sb.append(OldTypePlayerAction.PONG.name());
        return sb.toString();
    }
}
