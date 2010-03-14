package protocolTools;

import utility.EventObserver;

public class PokerCommandObserver<T extends PokerCommandListener> extends EventObserver<T>
{
    public void receiveSomething(String line)
    {
        if (line == null)
        {
            return;
        }
        baseReceive(line);
        commandReceived(line);
    }
    
    protected void commandReceived(String line)
    {
        
    }
    
    protected void baseReceive(String line)
    {
        for (final T listener : getSubscribers())
        {
            listener.commandReceived(line);
        }
    }
}
