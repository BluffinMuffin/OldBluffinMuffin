package bluffinmuffin.protocol.commands.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.AbstractCommand;

public class PlayerLeftCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return PlayerLeftCommand.COMMAND_NAME;
    }
    
    private final int m_playerPos;
    
    public static String COMMAND_NAME = "gamePLAYER_LEFT";
    
    public PlayerLeftCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
    }
    
    public PlayerLeftCommand(int pos)
    {
        m_playerPos = pos;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_playerPos);
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
}
