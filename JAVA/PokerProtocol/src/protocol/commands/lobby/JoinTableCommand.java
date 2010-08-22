package protocol.commands.lobby;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.ICommand;
import protocol.commands.lobby.response.JoinTableResponse;

public class JoinTableCommand implements ICommand
{
    private final int m_tableID;
    private final String m_playerName;
    public static String COMMAND_NAME = "lobbyJOIN_TABLE";
    
    public JoinTableCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
        m_tableID = Integer.parseInt(argsToken.nextToken());
    }
    
    public JoinTableCommand(String pName, int tName)
    {
        m_playerName = pName;
        m_tableID = tName;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(JoinTableCommand.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_playerName);
        sb.append(Command.L_DELIMITER);
        sb.append(m_tableID);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(int noSeat)
    {
        return new JoinTableResponse(m_playerName, m_tableID, noSeat).encodeCommand();
    }
    
    public String encodeErrorResponse()
    {
        return new JoinTableResponse(m_playerName, m_tableID, -1).encodeCommand();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public int getTableID()
    {
        return m_tableID;
    }
}
