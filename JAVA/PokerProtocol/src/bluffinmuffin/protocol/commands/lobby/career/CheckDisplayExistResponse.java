package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.lobby.AbstractLobbyResponse;

public class CheckDisplayExistResponse extends AbstractLobbyResponse<CheckDisplayExistCommand>
{
    @Override
    protected String getCommandName()
    {
        return CheckDisplayExistResponse.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyCAREER_CHECK_DISPLAY_EXIST_RESPONSE";
    
    private final boolean m_isExist;
    
    public CheckDisplayExistResponse(StringTokenizer argsToken)
    {
        super(new CheckDisplayExistCommand(argsToken));
        m_isExist = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public CheckDisplayExistResponse(CheckDisplayExistCommand command, boolean exist)
    {
        super(command);
        m_isExist = exist;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        super.encode(sb);
        append(sb, m_isExist);
    }
    
    public boolean isExist()
    {
        return m_isExist;
    }
}
