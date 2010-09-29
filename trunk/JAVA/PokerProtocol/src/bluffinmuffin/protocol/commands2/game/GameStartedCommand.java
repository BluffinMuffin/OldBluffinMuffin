package bluffinmuffin.protocol.commands2.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.AbstractCommand;

public class GameStartedCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return GameStartedCommand.COMMAND_NAME;
    }
    
    private final int m_noSeatD;
    private final int m_noSeatSB;
    private final int m_noSeatBB;
    
    public static String COMMAND_NAME = "gameSTARTED";
    
    public GameStartedCommand(StringTokenizer argsToken)
    {
        m_noSeatD = Integer.parseInt(argsToken.nextToken());
        m_noSeatSB = Integer.parseInt(argsToken.nextToken());
        m_noSeatBB = Integer.parseInt(argsToken.nextToken());
    }
    
    public GameStartedCommand(int noSeatD, int noSeatSB, int noSeatBB)
    {
        m_noSeatD = noSeatD;
        m_noSeatSB = noSeatSB;
        m_noSeatBB = noSeatBB;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_noSeatD);
        append(sb, m_noSeatSB);
        append(sb, m_noSeatBB);
    }
    
    public int GetNoSeatD()
    {
        return m_noSeatD;
    }
    
    public int GetNoSeatSB()
    {
        return m_noSeatSB;
    }
    
    public int GetNoSeatBB()
    {
        return m_noSeatBB;
    }
}
