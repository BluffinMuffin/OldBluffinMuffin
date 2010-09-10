package bluffinmuffin.protocol.commands.game;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;


public class PlayerLeftCommand implements ICommand
{
    private final int m_playerPos;
    public static String COMMAND_NAME = "gamePLAYER_LEFT";
    
    public PlayerLeftCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
    }
    
    public PlayerLeftCommand(int pos)
    {
        m_playerPos = pos;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(PlayerLeftCommand.COMMAND_NAME);
        sb.append(Command.G_DELIMITER);
        sb.append(m_playerPos);
        return sb.toString();
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
}
