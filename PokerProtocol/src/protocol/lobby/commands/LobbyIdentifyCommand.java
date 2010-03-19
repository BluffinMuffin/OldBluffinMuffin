package protocol.lobby.commands;

import java.util.StringTokenizer;

import protocol.IPokerCommand;
import protocol.PokerCommand;

public class LobbyIdentifyCommand implements IPokerCommand
{
    public static String COMMAND_NAME = "lobbyIDENTIFY";
    
    private final String m_playerName;
    
    public LobbyIdentifyCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
    }
    
    public LobbyIdentifyCommand(String name)
    {
        m_playerName = name;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(LobbyIdentifyCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerName);
        sb.append(PokerCommand.DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(Boolean success)
    {
        return new LobbyIdentifyResponseCommand(m_playerName, success).encodeCommand();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
}
