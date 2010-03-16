package protocolLobbyCommands;

import java.util.StringTokenizer;

import protocol.IPokerCommand;
import protocol.PokerCommand;

public class LobbyJoinTableCommand implements IPokerCommand
{
    private final String m_tableName;
    private final String m_playerName;
    public static String COMMAND_NAME = "lobbyJOIN_TABLE";
    
    public LobbyJoinTableCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
        m_tableName = argsToken.nextToken();
    }
    
    public LobbyJoinTableCommand(String pName, String tName)
    {
        m_playerName = pName;
        m_tableName = tName;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyJoinTableCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerName);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_tableName);
        sb.append(PokerCommand.DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(int noSeat)
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(noSeat);
        sb.append(PokerCommand.DELIMITER);
        return sb.toString();
    }
    
    public String encodeErrorResponse()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("-1");
        sb.append(PokerCommand.DELIMITER);
        return sb.toString();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public String getTableName()
    {
        return m_tableName;
    }
}
