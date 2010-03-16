package protocolGame;

import java.util.StringTokenizer;

import protocolTools.IPokerCommand;
import protocolTools.PokerCommand;

public class GamePlayerLeftCommand implements IPokerCommand
{
    private final int m_playerPos;
    public static String COMMAND_NAME = "gamePLAYER_LEFT";
    
    public GamePlayerLeftCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
    }
    
    public GamePlayerLeftCommand(int pos)
    {
        m_playerPos = pos;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GamePlayerLeftCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerPos);
        return sb.toString();
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
}
