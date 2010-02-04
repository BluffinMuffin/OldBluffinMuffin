package protocolLogic;

import java.util.StringTokenizer;

import utility.Constants;

public class BluffinAuthentificationCommand implements IBluffinCommand
{
    public static String COMMAND_NAME = "bluffinCONNECT";
    
    private final String m_playerName;
    
    public BluffinAuthentificationCommand(StringTokenizer argsToken)
    {
        m_playerName = argsToken.nextToken();
    }
    
    public BluffinAuthentificationCommand(String name)
    {
        m_playerName = name;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(BluffinAuthentificationCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_playerName);
        sb.append(Constants.DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(Boolean success)
    {
        return success.toString();
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
}
