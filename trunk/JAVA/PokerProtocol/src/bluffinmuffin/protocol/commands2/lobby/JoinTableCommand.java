package bluffinmuffin.protocol.commands2.lobby;

import java.util.StringTokenizer;

public class JoinTableCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return JoinTableCommand.COMMAND_NAME;
    }
    
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
    public void encode(StringBuilder sb)
    {
        append(sb, m_playerName);
        append(sb, m_tableID);
    }
    
    public String encodeResponse(int noSeat)
    {
        return new JoinTableResponse(this, noSeat).encode();
    }
    
    public String encodeErrorResponse()
    {
        return new JoinTableResponse(this, -1).encode();
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
