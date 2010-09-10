package bluffinmuffin.protocol.commands.lobby;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;


public class GameCommand implements ICommand
{
    private final int m_tableID;
    private final String m_command;
    public static String COMMAND_NAME = "lobbyGAME_COMMAND";
    
    public GameCommand(StringTokenizer argsToken)
    {
        m_tableID = Integer.parseInt(argsToken.nextToken());
        m_command = argsToken.nextToken();
    }
    
    public GameCommand(int pID, String pCommand)
    {
        m_tableID = pID;
        m_command = pCommand;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameCommand.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_tableID);
        sb.append(Command.L_DELIMITER);
        sb.append(m_command);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public int getTableId()
    {
        return m_tableID;
    }
    
    public String getCommand()
    {
        return m_command;
    }
}
