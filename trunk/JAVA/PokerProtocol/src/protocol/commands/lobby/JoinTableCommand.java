package protocol.commands.lobby;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.ICommand;
import protocol.commands.lobby.response.JoinTableResponse;

public class JoinTableCommand implements ICommand
{
    private final String m_tableName;
    private final String m_playerName;
    public static String COMMAND_NAME = "lobbyJOIN_TABLE";
    
    public JoinTableCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
        m_tableName = argsToken.nextToken();
    }
    
    public JoinTableCommand(String pName, String tName)
    {
        m_playerName = pName;
        m_tableName = tName;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(JoinTableCommand.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        sb.append(m_playerName);
        sb.append(Command.DELIMITER);
        sb.append(m_tableName);
        sb.append(Command.DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(int noSeat)
    {
        return new JoinTableResponse(m_playerName, m_tableName, noSeat).encodeCommand();
    }
    
    public String encodeErrorResponse()
    {
        return new JoinTableResponse(m_playerName, m_tableName, -1).encodeCommand();
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
