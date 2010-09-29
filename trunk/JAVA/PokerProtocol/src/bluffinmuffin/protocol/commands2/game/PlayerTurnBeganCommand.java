package bluffinmuffin.protocol.commands2.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.AbstractCommand;

public class PlayerTurnBeganCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return PlayerTurnBeganCommand.COMMAND_NAME;
    }
    
    private final int m_playerPos;
    private final int m_lastPlayerNoSeat;
    
    public static String COMMAND_NAME = "gamePLAYER_TURN_BEGAN";
    
    public PlayerTurnBeganCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        m_lastPlayerNoSeat = Integer.parseInt(argsToken.nextToken());
    }
    
    public PlayerTurnBeganCommand(int pos, int lastNoSeat)
    {
        m_playerPos = pos;
        m_lastPlayerNoSeat = lastNoSeat;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_playerPos);
        append(sb, m_lastPlayerNoSeat);
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
    
    public int getLastPlayerNoSeat()
    {
        return m_lastPlayerNoSeat;
    }
}
