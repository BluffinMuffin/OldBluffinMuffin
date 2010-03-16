package protocolGameCommands;

import java.util.StringTokenizer;

import protocol.IPokerCommand;
import protocol.PokerCommand;

public class GamePlayerTurnBeganCommand implements IPokerCommand
{
    private final int m_playerPos;
    public static String COMMAND_NAME = "gamePLAYER_TURN_BEGAN";
    
    public GamePlayerTurnBeganCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
    }
    
    public GamePlayerTurnBeganCommand(int pos)
    {
        m_playerPos = pos;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GamePlayerTurnBeganCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerPos);
        return sb.toString();
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
}
