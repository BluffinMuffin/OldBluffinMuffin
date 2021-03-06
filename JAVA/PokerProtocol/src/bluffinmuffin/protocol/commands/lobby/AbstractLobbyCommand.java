package bluffinmuffin.protocol.commands.lobby;

import bluffinmuffin.protocol.commands.AbstractCommand;

public abstract class AbstractLobbyCommand extends AbstractCommand
{
    public static final char Delimitter = '|';
    
    @Override
    protected <T> void append(StringBuilder sb, T thing)
    {
        sb.append(thing);
        sb.append(AbstractLobbyCommand.Delimitter);
    }
}
