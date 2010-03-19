package protocol.game.commands;

import java.util.StringTokenizer;

import poker.game.TypeAction;
import protocol.IPokerCommand;
import protocol.PokerCommand;

public class GamePlayerTurnEndedCommand implements IPokerCommand
{
    private final int m_playerPos;
    private final int m_playerBet;
    private final int m_playerMoney;
    private final int m_totalPot;
    private final TypeAction m_actionType;
    private final int m_actionAmount;
    private final boolean m_isPlaying;
    public static String COMMAND_NAME = "gamePLAYER_TURN_ENDED";
    
    public GamePlayerTurnEndedCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        m_playerBet = Integer.parseInt(argsToken.nextToken());
        m_playerMoney = Integer.parseInt(argsToken.nextToken());
        m_totalPot = Integer.parseInt(argsToken.nextToken());
        m_actionType = TypeAction.valueOf(argsToken.nextToken());
        m_actionAmount = Integer.parseInt(argsToken.nextToken());
        m_isPlaying = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public GamePlayerTurnEndedCommand(int pos, int bet, int money, int totalPot, TypeAction actionType, int actionAmount, boolean isPlaying)
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
        sb.append(GamePlayerTurnEndedCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerPos);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerBet);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerMoney);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_totalPot);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_actionType.name());
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_actionAmount);
        sb.append(PokerCommand.DELIMITER);
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
    
    public TypeAction getActionType()
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
