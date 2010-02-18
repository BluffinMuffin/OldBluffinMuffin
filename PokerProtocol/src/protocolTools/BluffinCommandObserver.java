package protocolTools;

import utility.EventObserver;

public class BluffinCommandObserver<T extends BluffinCommandListener> extends EventObserver<T>
{
    public void receiveSomething(String line)
    {
        if (line == null)
        {
            return;
        }
        baseReceive(line);
        onLineReceived(line);
    }
    
    protected void onLineReceived(String line)
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
