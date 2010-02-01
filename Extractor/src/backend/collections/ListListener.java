package backend.collections;

import java.util.EventListener;

public interface ListListener<T> extends EventListener
{
    void itemsAdded(ListEvent<T> e);
    
    void itemsChanged(ListEvent<T> e);
    
    void itemsRemoved(ListEvent<T> e);
}
