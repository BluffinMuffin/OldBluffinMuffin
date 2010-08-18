package protocol.commands.game;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.ICommand;

public class PlayerPlayMoneyCommand implements ICommand
{
    
    private final int m_played;
    public static String COMMAND_NAME = "gamePLAY_MONEY";
    
    public PlayerPlayMoneyCommand(StringTokenizer argsToken)
    {
        m_played = Integer.parseInt(argsToken.nextToken());
    }
    
    public PlayerPlayMoneyCommand(int played)
    {
        m_played = played;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(PlayerPlayMoneyCommand.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        sb.append(m_played);
        return sb.toString();
    }
    
    public int getPlayed()
    {
        return m_played;
    }
}
