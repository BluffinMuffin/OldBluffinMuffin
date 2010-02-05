package protocolGame;

import java.util.StringTokenizer;

import pokerLogic.TypePlayerAction;
import protocolLogic.IBluffinCommand;
import utility.Constants;

public class GamePlayerTurnEndedCommand implements IBluffinCommand
{
    private final int m_playerPos;
    private final int m_playerBet;
    private final int m_playerMoney;
    private final int m_totalPot;
    private final TypePlayerAction m_actionType;
    private final int m_actionAmount;
    public static String COMMAND_NAME = "gamePLAYER_TURN_ENDED";
    
    public GamePlayerTurnEndedCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        m_playerBet = Integer.parseInt(argsToken.nextToken());
        m_playerMoney = Integer.parseInt(argsToken.nextToken());
        m_totalPot = Integer.parseInt(argsToken.nextToken());
        m_actionType = TypePlayerAction.valueOf(argsToken.nextToken());
        m_actionAmount = Integer.parseInt(argsToken.nextToken());
    }
    
    public GamePlayerTurnEndedCommand(int pos, int bet, int money, int totalPot, TypePlayerAction actionType, int actionAmount)
    {
        m_playerPos = pos;
        m_playerBet = bet;
        m_playerMoney = money;
        m_totalPot = totalPot;
        m_actionType = actionType;
        m_actionAmount = actionAmount;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GamePlayerTurnEndedCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_playerPos);
        sb.append(Constants.DELIMITER);
        sb.append(m_playerBet);
        sb.append(Constants.DELIMITER);
        sb.append(m_playerMoney);
        sb.append(Constants.DELIMITER);
        sb.append(m_totalPot);
        sb.append(Constants.DELIMITER);
        sb.append(m_actionType.name());
        sb.append(Constants.DELIMITER);
        sb.append(m_actionAmount);
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
    
    public TypePlayerAction getActionType()
    {
        return m_actionType;
    }
    
    public int getActionAmount()
    {
        return m_actionAmount;
    }
}
