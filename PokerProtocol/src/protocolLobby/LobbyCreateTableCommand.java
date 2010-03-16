package protocolLobby;

import java.util.StringTokenizer;

import pokerGameLogic.TypePokerGameLimits;
import protocolTools.IPokerCommand;
import protocolTools.PokerCommand;

public class LobbyCreateTableCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "lobbyCREATE_TABLE";
    
    private final String m_tableName;
    private final int m_bigBlind;
    private final int m_maxPlayers;
    private final String m_playerName;
    private final int m_WaitingTimeAfterPlayerAction;
    private final int m_WaitingTimeAfterBoardDealed;
    private final int m_WaitingTimeAfterPotWon;
    private final TypePokerGameLimits m_limit;
    
    public LobbyCreateTableCommand(StringTokenizer argsToken)
    {
        m_tableName = argsToken.nextToken();
        m_bigBlind = Integer.parseInt(argsToken.nextToken());
        m_maxPlayers = Integer.parseInt(argsToken.nextToken());
        m_playerName = argsToken.nextToken();
        m_WaitingTimeAfterPlayerAction = Integer.parseInt(argsToken.nextToken());
        m_WaitingTimeAfterBoardDealed = Integer.parseInt(argsToken.nextToken());
        m_WaitingTimeAfterPotWon = Integer.parseInt(argsToken.nextToken());
        m_limit = TypePokerGameLimits.valueOf(argsToken.nextToken());
    }
    
    public LobbyCreateTableCommand(String p_tableName, int p_bigBlind, int p_maxPlayers, String p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypePokerGameLimits limit)
    {
        m_tableName = p_tableName;
        m_bigBlind = p_bigBlind;
        m_maxPlayers = p_maxPlayers;
        m_playerName = p_playerName;
        m_WaitingTimeAfterPlayerAction = wtaPlayerAction;
        m_WaitingTimeAfterBoardDealed = wtaBoardDealed;
        m_WaitingTimeAfterPotWon = wtaPotWon;
        m_limit = limit;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyCreateTableCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_tableName);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_bigBlind);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_maxPlayers);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerName);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_WaitingTimeAfterPlayerAction);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_WaitingTimeAfterBoardDealed);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_WaitingTimeAfterPotWon);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_limit.name());
        sb.append(PokerCommand.DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(Integer noPort)
    {
        return noPort.toString();
    }
    
    public String getTableName()
    {
        return m_tableName;
    }
    
    public int getBigBlind()
    {
        return m_bigBlind;
    }
    
    public int getMaxPlayers()
    {
        return m_maxPlayers;
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public int getWaitingTimeAfterPlayerAction()
    {
        return m_WaitingTimeAfterPlayerAction;
    }
    
    public int getWaitingTimeAfterBoardDealed()
    {
        return m_WaitingTimeAfterBoardDealed;
    }
    
    public int getWaitingTimeAfterPotWon()
    {
        return m_WaitingTimeAfterPotWon;
    }
    
    public TypePokerGameLimits getLimit()
    {
        return m_limit;
    }
    
}
