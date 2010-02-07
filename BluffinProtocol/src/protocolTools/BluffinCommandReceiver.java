package protocolTools;

import java.lang.reflect.ParameterizedType;

import javax.swing.event.EventListenerList;

public class BluffinCommandReceiver<T extends BluffinCommandListener>
{
    private final EventListenerList listeners = new EventListenerList();
    
    @SuppressWarnings("unchecked")
    private Class<T> getTClass()
    {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public void subscribe(T listener)
    {
        listeners.add(getTClass(), listener);
    }
    
    public void unsubscribe(T listener)
    {
        listeners.remove(getTClass(), listener);
    }
    
    protected T[] getSubscribers()
    {
        return listeners.getListeners(getTClass());
    }
    
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
