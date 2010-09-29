package bluffinmuffin.protocol.commands2;

public abstract class AbstractCommandResponse<T extends AbstractCommand> extends AbstractCommand
{
    private final T m_command;
    
    public AbstractCommandResponse(T command)
    {
        m_command = command;
    }
    
    public T getCommand()
    {
        return m_command;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        m_command.encode(sb);
    }
}
