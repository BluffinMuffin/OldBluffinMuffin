package protocol.game.commands;

import java.util.StringTokenizer;

import protocol.ICommand;
import protocol.Command;

public class GameStartedCommand implements ICommand
{
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
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameStartedCommand.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        sb.append(m_noSeatD);
        sb.append(Command.DELIMITER);
        sb.append(m_noSeatSB);
        sb.append(Command.DELIMITER);
        sb.append(m_noSeatBB);
        return sb.toString();
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