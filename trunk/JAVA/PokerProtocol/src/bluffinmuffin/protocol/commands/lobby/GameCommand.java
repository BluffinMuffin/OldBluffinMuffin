package bluffinmuffin.protocol.commands.lobby;

import java.util.StringTokenizer;

public class GameCommand extends AbstractLobbyCommand
{
    @Override
    protected String getCommandName()
    {
        return GameCommand.COMMAND_NAME;
    }
    
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
    public void encode(StringBuilder sb)
    {
        append(sb, m_tableID);
        append(sb, m_command);
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
