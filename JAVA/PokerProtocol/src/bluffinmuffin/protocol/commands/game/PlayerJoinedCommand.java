package bluffinmuffin.protocol.commands.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.AbstractCommand;

public class PlayerJoinedCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return PlayerJoinedCommand.COMMAND_NAME;
    }
    
    private final int m_playerPos;
    private final String m_playerName;
    private final int m_playerMoney;
    
    public static String COMMAND_NAME = "gamePLAYER_JOINED";
    
    public PlayerJoinedCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        m_playerName = argsToken.nextToken();
        m_playerMoney = Integer.parseInt(argsToken.nextToken());
    }
    
    public PlayerJoinedCommand(int pos, String name, int money)
    {
        m_playerPos = pos;
        m_playerName = name;
        m_playerMoney = money;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_playerPos);
        append(sb, m_playerName);
        append(sb, m_playerMoney);
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public int getPlayerMoney()
    {
        return m_playerMoney;
    }
}
