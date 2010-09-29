package bluffinmuffin.protocol.commands2.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.AbstractCommand;

public class PlayerPlayMoneyCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return PlayerPlayMoneyCommand.COMMAND_NAME;
    }
    
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
    public void encode(StringBuilder sb)
    {
        append(sb, m_played);
    }
    
    public int getPlayed()
    {
        return m_played;
    }
}
