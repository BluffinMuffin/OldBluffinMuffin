package protocol.lobby.commands;

import java.util.StringTokenizer;

import protocol.IPokerCommand;
import protocol.PokerCommand;

public class LobbyIdentifyResponseCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "lobbyIDENTIFY_RESPONSE";
    
    private final String m_playerName;
    private final boolean m_isOK;
    
    public LobbyIdentifyResponseCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
        m_isOK = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public LobbyIdentifyResponseCommand(String name, boolean ok)
    {
        m_playerName = name;
        m_isOK = ok;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyIdentifyResponseCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerName);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_isOK);
        sb.append(PokerCommand.DELIMITER);
        return sb.toString();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public boolean isOK()
    {
        return m_isOK;
    }
}
