package protocol.commands.game;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.ICommand;

public class PlayerMoneyChangedCommand implements ICommand
{
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
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(PlayerMoneyChangedCommand.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        sb.append(m_playerPos);
        sb.append(Command.DELIMITER);
        sb.append(m_playerMoney);
        return sb.toString();
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
