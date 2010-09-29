package bluffinmuffin.protocol.commands;

public abstract class AbstractCommand
{
    public static final char Delimitter = ';';
    
    protected abstract String getCommandName();
    
    public void encode(StringBuilder sb)
    {
    }
    
    protected <T> void append(StringBuilder sb, T thing)
    {
        sb.append(thing);
        sb.append(AbstractCommand.Delimitter);
    }
    
    public String encode()
    {
        final StringBuilder sb = new StringBuilder();
        append(sb, getCommandName());
        encode(sb);
        return sb.toString();
    }
}
