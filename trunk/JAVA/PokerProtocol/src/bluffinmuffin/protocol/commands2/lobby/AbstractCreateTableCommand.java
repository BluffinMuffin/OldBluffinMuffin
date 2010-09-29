package bluffinmuffin.protocol.commands2.lobby;

import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.GameBetLimitType;

public abstract class AbstractCreateTableCommand extends AbstractLobbyCommand
{
    protected final String m_tableName;
    protected final int m_bigBlind;
    protected final int m_maxPlayers;
    protected final String m_playerName;
    protected final int m_WaitingTimeAfterPlayerAction;
    protected final int m_WaitingTimeAfterBoardDealed;
    protected final int m_WaitingTimeAfterPotWon;
    protected final GameBetLimitType m_limit;
    
    public AbstractCreateTableCommand(StringTokenizer argsToken)
    {
        m_tableName = argsToken.nextToken();
        m_bigBlind = Integer.parseInt(argsToken.nextToken());
        m_maxPlayers = Integer.parseInt(argsToken.nextToken());
        m_playerName = argsToken.nextToken();
        m_WaitingTimeAfterPlayerAction = Integer.parseInt(argsToken.nextToken());
        m_WaitingTimeAfterBoardDealed = Integer.parseInt(argsToken.nextToken());
        m_WaitingTimeAfterPotWon = Integer.parseInt(argsToken.nextToken());
        m_limit = GameBetLimitType.values()[Integer.parseInt(argsToken.nextToken())];
    }
    
    public AbstractCreateTableCommand(String p_tableName, int p_bigBlind, int p_maxPlayers, String p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, GameBetLimitType limit)
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
    public void encode(StringBuilder sb)
    {
        append(sb, m_tableName);
        append(sb, m_bigBlind);
        append(sb, m_maxPlayers);
        append(sb, m_playerName);
        append(sb, m_WaitingTimeAfterPlayerAction);
        append(sb, m_WaitingTimeAfterBoardDealed);
        append(sb, m_WaitingTimeAfterPotWon);
        append(sb, m_limit.ordinal());
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
    
    public GameBetLimitType getLimit()
    {
        return m_limit;
    }
    
}
