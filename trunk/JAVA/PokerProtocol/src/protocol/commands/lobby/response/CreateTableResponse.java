package protocol.commands.lobby.response;

import java.util.StringTokenizer;

import poker.game.TypeBet;
import protocol.commands.Command;
import protocol.commands.ICommand;

public class CreateTableResponse implements ICommand
{
    public static String COMMAND_NAME = "lobbyCREATE_TABLE_RESPONSE";
    
    private final String m_tableName;
    private final int m_bigBlind;
    private final int m_maxPlayers;
    private final String m_playerName;
    private final int m_WaitingTimeAfterPlayerAction;
    private final int m_WaitingTimeAfterBoardDealed;
    private final int m_WaitingTimeAfterPotWon;
    private final TypeBet m_limit;
    private final int m_ResponsePort;
    
    public CreateTableResponse(StringTokenizer argsToken)
    {
        m_tableName = argsToken.nextToken();
        m_bigBlind = Integer.parseInt(argsToken.nextToken());
        m_maxPlayers = Integer.parseInt(argsToken.nextToken());
        m_playerName = argsToken.nextToken();
        m_WaitingTimeAfterPlayerAction = Integer.parseInt(argsToken.nextToken());
        m_WaitingTimeAfterBoardDealed = Integer.parseInt(argsToken.nextToken());
        m_WaitingTimeAfterPotWon = Integer.parseInt(argsToken.nextToken());
        m_limit = TypeBet.values()[Integer.parseInt(argsToken.nextToken())];
        m_ResponsePort = Integer.parseInt(argsToken.nextToken());
    }
    
    public CreateTableResponse(String p_tableName, int p_bigBlind, int p_maxPlayers, String p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypeBet limit, int responsePort)
    {
        m_tableName = p_tableName;
        m_bigBlind = p_bigBlind;
        m_maxPlayers = p_maxPlayers;
        m_playerName = p_playerName;
        m_WaitingTimeAfterPlayerAction = wtaPlayerAction;
        m_WaitingTimeAfterBoardDealed = wtaBoardDealed;
        m_WaitingTimeAfterPotWon = wtaPotWon;
        m_limit = limit;
        m_ResponsePort = responsePort;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(CreateTableResponse.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        sb.append(m_tableName);
        sb.append(Command.DELIMITER);
        sb.append(m_bigBlind);
        sb.append(Command.DELIMITER);
        sb.append(m_maxPlayers);
        sb.append(Command.DELIMITER);
        sb.append(m_playerName);
        sb.append(Command.DELIMITER);
        sb.append(m_WaitingTimeAfterPlayerAction);
        sb.append(Command.DELIMITER);
        sb.append(m_WaitingTimeAfterBoardDealed);
        sb.append(Command.DELIMITER);
        sb.append(m_WaitingTimeAfterPotWon);
        sb.append(Command.DELIMITER);
        sb.append(m_limit.ordinal());
        sb.append(Command.DELIMITER);
        sb.append(m_ResponsePort);
        sb.append(Command.DELIMITER);
        return sb.toString();
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
    
    public TypeBet getLimit()
    {
        return m_limit;
    }
    
    public int getResponsePort()
    {
        return m_ResponsePort;
    }
    
}
