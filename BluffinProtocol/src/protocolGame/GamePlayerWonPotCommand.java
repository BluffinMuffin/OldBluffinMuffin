package protocolGame;

import java.util.StringTokenizer;

import protocolLogic.IBluffinCommand;
import utility.Constants;

public class GamePlayerWonPotCommand implements IBluffinCommand
{
    private final int m_playerPos;
    private final int m_potID;
    private final int m_shared;
    private final int m_playerMoney;
    public static String COMMAND_NAME = "gamePLAYER_WON_POT";
    
    public GamePlayerWonPotCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        m_potID = Integer.parseInt(argsToken.nextToken());
        m_shared = Integer.parseInt(argsToken.nextToken());
        m_playerMoney = Integer.parseInt(argsToken.nextToken());
    }
    
    public GamePlayerWonPotCommand(int pos, int potID, int shared, int money)
    {
        m_playerPos = pos;
        m_potID = potID;
        m_shared = shared;
        m_playerMoney = money;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GamePlayerWonPotCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_playerPos);
        sb.append(Constants.DELIMITER);
        sb.append(m_potID);
        sb.append(Constants.DELIMITER);
        sb.append(m_shared);
        sb.append(Constants.DELIMITER);
        sb.append(m_playerMoney);
        return sb.toString();
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
