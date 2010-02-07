package protocolTools;

import utility.EventReceiver;

public class BluffinCommandReceiver<T extends BluffinCommandListener> extends EventReceiver<T>
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
