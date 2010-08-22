package protocol.commands.lobby.response;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.ICommand;

public class JoinTableResponse implements ICommand
{
    private final int m_tableID;
    private final String m_playerName;
    private final int m_noSeat;
    public static String COMMAND_NAME = "lobbyJOIN_TABLE_RESPONSE";
    
    public JoinTableResponse(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
        m_tableID = Integer.parseInt(argsToken.nextToken());
        m_noSeat = Integer.parseInt(argsToken.nextToken());
    }
    
    public JoinTableResponse(String pName, int tName, int seat)
    {
        m_playerName = pName;
        m_tableID = tName;
        m_noSeat = seat;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(JoinTableResponse.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_playerName);
        sb.append(Command.L_DELIMITER);
        sb.append(m_tableID);
        sb.append(Command.L_DELIMITER);
        sb.append(m_noSeat);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public int getTableID()
    {
        return m_tableID;
    }
    
    public int getNoSeat()
    {
        return m_noSeat;
    }
}
