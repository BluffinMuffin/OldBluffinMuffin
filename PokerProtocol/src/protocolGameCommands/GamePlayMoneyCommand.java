package protocolGameCommands;

import java.util.StringTokenizer;

import protocol.IPokerCommand;
import protocol.PokerCommand;

public class GamePlayMoneyCommand implements IPokerCommand
{
    
    private final int m_played;
    public static String COMMAND_NAME = "gamePLAY_MONEY";
    
    public GamePlayMoneyCommand(StringTokenizer argsToken)
    {
        m_played = Integer.parseInt(argsToken.nextToken());
    }
    
    public GamePlayMoneyCommand(int played)
    {
        m_played = played;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GamePlayMoneyCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_played);
        return sb.toString();
    }
    
    public int getPlayed()
    {
        return m_played;
    }
}
