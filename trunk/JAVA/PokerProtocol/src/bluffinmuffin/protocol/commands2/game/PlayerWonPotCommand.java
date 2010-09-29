package bluffinmuffin.protocol.commands2.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands2.AbstractCommand;

public class PlayerWonPotCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return PlayerWonPotCommand.COMMAND_NAME;
    }
    
    private final int m_playerPos;
    private final int m_potID;
    private final int m_shared;
    private final int m_playerMoney;
    
    public static String COMMAND_NAME = "gamePLAYER_WON_POT";
    
    public PlayerWonPotCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        m_potID = Integer.parseInt(argsToken.nextToken());
        m_shared = Integer.parseInt(argsToken.nextToken());
        m_playerMoney = Integer.parseInt(argsToken.nextToken());
    }
    
    public PlayerWonPotCommand(int pos, int potID, int shared, int money)
    {
        m_playerPos = pos;
        m_potID = potID;
        m_shared = shared;
        m_playerMoney = money;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_playerPos);
        append(sb, m_potID);
        append(sb, m_shared);
        append(sb, m_playerMoney);
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
    
    public int getPotID()
    {
        return m_potID;
    }
    
    public int getShared()
    {
        return m_shared;
    }
    
    public int getPlayerMoney()
    {
        return m_playerMoney;
    }
}
