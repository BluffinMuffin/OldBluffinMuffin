package bluffinmuffin.protocol.commands2.lobby;

import bluffinmuffin.protocol.commands2.AbstractCommandResponse;

public abstract class AbstractLobbyResponse<T extends AbstractLobbyCommand> extends AbstractCommandResponse<T>
{
    
    public AbstractLobbyResponse(T command)
    {
        super(command);
    }
    
    @Override
    protected <T2> void append(StringBuilder sb, T2 thing)
    {
        sb.append(thing);
        sb.append(AbstractLobbyCommand.Delimitter);
    }
    
}
