package protocolGame;

import java.util.StringTokenizer;

import protocolTools.IPokerCommand;
import utility.Constants;

public class GameAskActionCommand implements IPokerCommand
{
    
    private final boolean m_canCheck;
    private final boolean m_canFold;
    private final boolean m_canCall;
    private final int m_callAmnt;
    private final boolean m_canRaise;
    private final int m_raiseMin;
    private final int m_raiseMax;
    public static String COMMAND_NAME = "gameASK_ACTION";
    
    public GameAskActionCommand(StringTokenizer argsToken)
    {
        m_canCheck = Boolean.parseBoolean(argsToken.nextToken());
        m_canFold = Boolean.parseBoolean(argsToken.nextToken());
        m_canCall = Boolean.parseBoolean(argsToken.nextToken());
        m_callAmnt = Integer.parseInt(argsToken.nextToken());
        m_canRaise = Boolean.parseBoolean(argsToken.nextToken());
        m_raiseMin = Integer.parseInt(argsToken.nextToken());
        m_raiseMax = Integer.parseInt(argsToken.nextToken());
    }
    
    public GameAskActionCommand(boolean canCheck, boolean canFold, boolean canCall, int callAmnt, boolean canRaise, int raiseMin, int raiseMax)
    {
        m_canCheck = canCheck;
        m_canFold = canFold;
        m_canCall = canCall;
        m_callAmnt = callAmnt;
        m_canRaise = canRaise;
        m_raiseMin = raiseMin;
        m_raiseMax = raiseMax;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameAskActionCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_canCheck);
        sb.append(Constants.DELIMITER);
        sb.append(m_canFold);
        sb.append(Constants.DELIMITER);
        sb.append(m_canCall);
        sb.append(Constants.DELIMITER);
        sb.append(m_callAmnt);
        sb.append(Constants.DELIMITER);
        sb.append(m_canRaise);
        sb.append(Constants.DELIMITER);
        sb.append(m_raiseMin);
        sb.append(Constants.DELIMITER);
        sb.append(m_raiseMax);
        return sb.toString();
    }
    
    public boolean canCheck()
    {
        return m_canCheck;
    }
    
    public boolean canFold()
    {
        return m_canFold;
    }
    
    public boolean canCall()
    {
        return m_canCall;
    }
    
    public int getCallAmnt()
    {
        return m_callAmnt;
    }
    
    public boolean canRaise()
    {
        return m_canRaise;
    }
    
    public int getRaiseMin()
    {
        return m_raiseMin;
    }
    
    public int getRaiseMax()
    {
        return m_raiseMax;
    }
}
