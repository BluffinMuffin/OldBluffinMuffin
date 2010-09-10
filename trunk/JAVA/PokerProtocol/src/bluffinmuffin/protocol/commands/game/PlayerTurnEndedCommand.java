package bluffinmuffin.protocol.commands.game;

import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.PlayerActionType;
import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;


public class PlayerTurnEndedCommand implements ICommand
{
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
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(PlayerTurnEndedCommand.COMMAND_NAME);
        sb.append(Command.G_DELIMITER);
        sb.append(m_playerPos);
        sb.append(Command.G_DELIMITER);
        sb.append(m_playerBet);
        sb.append(Command.G_DELIMITER);
        sb.append(m_playerMoney);
        sb.append(Command.G_DELIMITER);
        sb.append(m_totalPot);
        sb.append(Command.G_DELIMITER);
        sb.append(m_actionType.ordinal());
        sb.append(Command.G_DELIMITER);
        sb.append(m_actionAmount);
        sb.append(Command.G_DELIMITER);
        sb.append(m_isPlaying);
        return sb.toString();
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
