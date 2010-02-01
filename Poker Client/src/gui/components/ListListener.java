package gui.components;

import java.util.EventListener;

/**
 * @author Hocus
 *         This interface is a generic EvenListener.
 *         It will be notified if an item has changed, has been added or has
 *         been removed.
 */
public interface ListListener<T> extends EventListener
{
    void itemsAdded(ListEvent<T> e);
    
    void itemsChanged(ListEvent<T> e);
    
    void itemsRemoved(ListEvent<T> e);
}
