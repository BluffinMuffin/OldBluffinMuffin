package protocolGame;

import java.util.StringTokenizer;

import protocolLogic.IBluffinCommand;
import utility.Constants;

public class GamePlayerTurnBeganCommand implements IBluffinCommand
{
    private final int m_playerPos;
    public static String COMMAND_NAME = "gamePLAYER_TURN_BEGAN";
    
    public GamePlayerTurnBeganCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
    }
    
    public GamePlayerTurnBeganCommand(int pos)
    {
        m_playerPos = pos;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GamePlayerTurnBeganCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_playerPos);
        return sb.toString();
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
}
