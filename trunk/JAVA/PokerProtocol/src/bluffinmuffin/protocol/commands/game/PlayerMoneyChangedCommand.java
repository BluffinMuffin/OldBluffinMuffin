package bluffinmuffin.protocol.commands.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.AbstractCommand;

public class PlayerMoneyChangedCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return PlayerMoneyChangedCommand.COMMAND_NAME;
    }
    
    private final int m_playerPos;
    private final int m_playerMoney;
    
    public static String COMMAND_NAME = "gamePLAYER_MONEY_CHANGED";
    
    public PlayerMoneyChangedCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        m_playerMoney = Integer.parseInt(argsToken.nextToken());
    }
    
    public PlayerMoneyChangedCommand(int pos, int money)
    {
        m_playerPos = pos;
        m_playerMoney = money;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_playerPos);
        append(sb, m_playerMoney);
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
    
    public int getPlayerMoney()
    {
        return m_playerMoney;
    }
}
