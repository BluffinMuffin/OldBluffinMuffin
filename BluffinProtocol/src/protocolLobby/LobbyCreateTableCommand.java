package protocolLobby;

import java.util.StringTokenizer;

import pokerLogic.TypePokerGame;
import protocolLogic.IBluffinCommand;
import utility.Constants;

public class LobbyCreateTableCommand implements IBluffinCommand
{
    public static String COMMAND_NAME = "lobbyCREATE_TABLE";
    
    private final String m_tableName;
    private final TypePokerGame m_gameType;
    private final int m_bigBlind;
    private final int m_maxPlayers;
    private final String m_playerName;
    
    public LobbyCreateTableCommand(StringTokenizer argsToken)
    {
        m_tableName = argsToken.nextToken();
        m_gameType = TypePokerGame.valueOf(argsToken.nextToken());
        m_bigBlind = Integer.parseInt(argsToken.nextToken());
        m_maxPlayers = Integer.parseInt(argsToken.nextToken());
        m_playerName = argsToken.nextToken();
    }
    
    public LobbyCreateTableCommand(String p_tableName, TypePokerGame p_gameType, int p_bigBlind, int p_maxPlayers, String p_playerName)
    {
        m_tableName = p_tableName;
        m_gameType = p_gameType;
        m_bigBlind = p_bigBlind;
        m_maxPlayers = p_maxPlayers;
        m_playerName = p_playerName;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyCreateTableCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_tableName);
        sb.append(Constants.DELIMITER);
        sb.append(m_gameType.name());
        sb.append(Constants.DELIMITER);
        sb.append(m_bigBlind);
        sb.append(Constants.DELIMITER);
        sb.append(m_maxPlayers);
        sb.append(Constants.DELIMITER);
        sb.append(m_playerName);
        sb.append(Constants.DELIMITER);
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
    
    public TypePokerGame getGameType()
    {
        return m_gameType;
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
    
}
