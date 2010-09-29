package bluffinmuffin.protocol.commands.game;

import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.PlayerActionType;
import bluffinmuffin.protocol.commands.AbstractCommand;

public class PlayerTurnEndedCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return PlayerTurnEndedCommand.COMMAND_NAME;
    }
    
    private final int m_playerPos;
    private final int m_playerBet;
    private final int m_playerMoney;
    private final int m_totalPot;
    private final PlayerActionType m_actionType;
    private final int m_actionAmount;
    private final boolean m_isPlaying;
    
    public static String COMMAND_NAME = "gamePLAYER_TURN_ENDED";
    
    public PlayerTurnEndedCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        m_playerBet = Integer.parseInt(argsToken.nextToken());
        m_playerMoney = Integer.parseInt(argsToken.nextToken());
        m_totalPot = Integer.parseInt(argsToken.nextToken());
        m_actionType = PlayerActionType.values()[Integer.parseInt(argsToken.nextToken())];
        m_actionAmount = Integer.parseInt(argsToken.nextToken());
        m_isPlaying = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public PlayerTurnEndedCommand(int pos, int bet, int money, int totalPot, PlayerActionType actionType, int actionAmount, boolean isPlaying)
    {
        m_playerPos = pos;
        m_playerBet = bet;
        m_playerMoney = money;
        m_totalPot = totalPot;
        m_actionType = actionType;
        m_actionAmount = actionAmount;
        m_isPlaying = isPlaying;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_playerPos);
        append(sb, m_playerBet);
        append(sb, m_playerMoney);
        append(sb, m_totalPot);
        append(sb, m_actionType.ordinal());
        append(sb, m_actionAmount);
        append(sb, m_isPlaying);
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
    
    public int getPlayerBet()
    {
        return m_playerBet;
    }
    
    public int getPlayerMoney()
    {
        return m_playerMoney;
    }
    
    public int getTotalPot()
    {
        return m_totalPot;
    }
    
    public PlayerActionType getActionType()
    {
        return m_actionType;
    }
    
    public int getActionAmount()
    {
        return m_actionAmount;
    }
    
    public boolean isPlaying()
    {
        return m_isPlaying;
    }
}
