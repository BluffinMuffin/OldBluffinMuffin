package protocolGameCommands;

import java.util.StringTokenizer;

import protocol.IPokerCommand;
import protocol.PokerCommand;

public class GamePlayerJoinedCommand implements IPokerCommand
{
    private final int m_playerPos;
    private final String m_playerName;
    private final int m_playerMoney;
    public static String COMMAND_NAME = "gamePLAYER_JOINED";
    
    public GamePlayerJoinedCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        m_playerName = argsToken.nextToken();
        m_playerMoney = Integer.parseInt(argsToken.nextToken());
    }
    
    public GamePlayerJoinedCommand(int pos, String name, int money)
    {
        m_playerPos = pos;
        m_playerName = name;
        m_playerMoney = money;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GamePlayerJoinedCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerPos);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerName);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerMoney);
        return sb.toString();
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