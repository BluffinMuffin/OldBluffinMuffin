package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;

public class GetUserCommand implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_GET_USER";
    
    private final String m_username;
    
    public GetUserCommand(StringTokenizer argsToken)
    {
        m_username = argsToken.nextToken();
    }
    
    public GetUserCommand(String p_Username)
    {
        m_username = p_Username;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GetUserCommand.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(m_username);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(String mail, String display, double money)
    {
        return new GetUserResponse(m_username, mail, display, money).encodeCommand();
    }
    
    public String getUsername()
    {
        return m_username;
    }
}
