package protocolLobbyCommands;

import java.util.StringTokenizer;

import protocol.IPokerCommand;
import protocol.PokerCommand;

public class LobbyJoinTableResponseCommand implements IPokerCommand
{
    private final String m_tableName;
    private final String m_playerName;
    private final int m_noSeat;
    public static String COMMAND_NAME = "lobbyJOIN_TABLE_RESPONSE";
    
    public LobbyJoinTableResponseCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
        m_tableName = argsToken.nextToken();
        m_noSeat = Integer.parseInt(argsToken.nextToken());
    }
    
    public LobbyJoinTableResponseCommand(String pName, String tName, int seat)
    {
        m_playerName = pName;
        m_tableName = tName;
        m_noSeat = seat;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyJoinTableResponseCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerName);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_tableName);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_noSeat);
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
    
    public int getNoSeat()
    {
        return m_noSeat;
    }
}
