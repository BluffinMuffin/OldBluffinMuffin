package protocol.commands.game;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.ICommand;

public class PlayerTurnBeganCommand implements ICommand
{
    private final int m_playerPos;
    public static String COMMAND_NAME = "gamePLAYER_TURN_BEGAN";
    
    public PlayerTurnBeganCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
    }
    
    public PlayerTurnBeganCommand(int pos)
    {
        m_playerPos = pos;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(PlayerTurnBeganCommand.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        sb.append(m_playerPos);
        return sb.toString();
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
}
