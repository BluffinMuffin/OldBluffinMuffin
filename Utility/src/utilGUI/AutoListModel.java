package utilGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * @author Hocus
 *         This class represents a generic list that notifies observers when it
 *         is modified.
 *         This class is supposed to be <b>Thread-safe</b> because it uses
 *         Collections.synchronizedList.
 */
@SuppressWarnings("unchecked")
public class AutoListModel<T> implements List<T>, ListModel
{
    private static final long serialVersionUID = -5219487687439369159L;
    
    private final List<T> m_syncList = Collections.synchronizedList(new ArrayList<T>());
    private final ArrayList<ListDataListener> m_listDataListeners = new ArrayList<ListDataListener>();
    private final ArrayList<ListListener<T>> m_listListeners = new ArrayList<ListListener<T>>();
    
    @Override
    public void add(int index, T element)
    {
        m_syncList.add(index, element);
        notifyListDataListeners_IntervalAdded(index, index);
        notifyListListeners_ItemsAdded(Arrays.asList(element));
    }
    
    @Override
    public boolean add(T e)
    {
        synchronized (m_syncList)
        {
            final boolean result = m_syncList.add(e);
            final int index = m_syncList.size();
            
            if (result)
            {
                notifyListDataListeners_IntervalAdded(index, index);
                notifyListListeners_ItemsAdded(Arrays.asList(e));
            }
            
            return result;
        }
    }
    
    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        synchronized (m_syncList)
        {
            final int index = m_syncList.size();
            final boolean result = m_syncList.addAll(c);
            
            if (result)
            {
                notifyListDataListeners_IntervalAdded(index, index + c.size());
                notifyListListeners_ItemsAdded(new ArrayList<T>(c));
            }
            
            return result;
        }
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends T> c)
    {
        synchronized (m_syncList)
        {
            final boolean result = m_syncList.addAll(c);
            
            if (result)
            {
                notifyListDataListeners_IntervalAdded(index, index + c.size());
                notifyListListeners_ItemsAdded(new ArrayList<T>(c));
            }
            
            return result;
        }
    }
    
    @Override
    public void addListDataListener(ListDataListener l)
    {
        m_listDataListeners.add(l);
    }
    
    public void addListListener(ListListener<T> l)
    {
        m_listListeners.add(l);
    }
    
    @Override
    public void clear()
    {
        synchronized (m_syncList)
        {
            final int size = m_syncList.size();
            final ArrayList<T> items = new ArrayList<T>(m_syncList);
            m_syncList.clear();
            
            notifyListDataListeners_IntervalRemoved(0, size);
            notifyListListeners_ItemsRemoved(items);
        }
    }
    
    @Override
    public boolean contains(Object o)
    {
        return m_syncList.contains(o);
    }
    
    @Override
    public boolean containsAll(Collection<?> c)
    {
        return m_syncList.containsAll(c);
    }
    
    @Override
    public T get(int index)
    {
        return m_syncList.get(index);
    }
    
    @Override
    public Object getElementAt(int index)
    {
        return m_syncList.get(index);
    }
    
    @Override
    public int getSize()
    {
        return m_syncList.size();
    }
    
    @Override
    public int indexOf(Object o)
    {
        return m_syncList.indexOf(o);
    }
    
    @Override
    public boolean isEmpty()
    {
        return m_syncList.isEmpty();
    }
    
    @Override
    public Iterator<T> iterator()
    {
        return m_syncList.iterator();
    }
    
    @Override
    public int lastIndexOf(Object o)
    {
        return m_syncList.lastIndexOf(o);
    }
    
    @Override
    public ListIterator<T> listIterator()
    {
        return m_syncList.listIterator();
    }
    
    @Override
    public ListIterator<T> listIterator(int index)
    {
        return m_syncList.listIterator(index);
    }
    
    private void notifyListDataListeners_ContentsChanged(int p_start, int p_end)
    {
        final ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, p_start, p_end);
        for (final ListDataListener listener : m_listDataListeners)
        {
            listener.contentsChanged(event);
        }
    }
    
    private void notifyListDataListeners_IntervalAdded(int p_start, int p_end)
    {
        final ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, p_start, p_end);
        for (final ListDataListener listener : m_listDataListeners)
        {
            listener.intervalAdded(event);
        }
    }
    
    private void notifyListDataListeners_IntervalRemoved(int p_start, int p_end)
    {
        final ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, p_start, p_end);
        for (final ListDataListener listener : m_listDataListeners)
        {
            listener.intervalRemoved(event);
        }
    }
    
    private void notifyListListeners_ItemsAdded(List<T> p_items)
    {
        final ListEvent<T> event = new ListEvent<T>(this, ListEvent.ITEMS_ADDED, p_items);
        for (final ListListener<T> listener : m_listListeners)
        {
            listener.itemsAdded(event);
        }
    }
    
    private void notifyListListeners_ItemsChanged(List<T> p_items)
    {
        final ListEvent<T> event = new ListEvent<T>(this, ListEvent.ITEMS_CHANGED, p_items);
        for (final ListListener<T> listener : m_listListeners)
        {
            listener.itemsChanged(event);
        }
    }
    
    private void notifyListListeners_ItemsRemoved(List<T> p_items)
    {
        final ListEvent<T> event = new ListEvent<T>(this, ListEvent.ITEMS_REMOVED, p_items);
        for (final ListListener<T> listener : m_listListeners)
        {
            listener.itemsRemoved(event);
        }
    }
    
    @Override
    public T remove(int index)
    {
        synchronized (m_syncList)
        {
            final T e = m_syncList.remove(index);
            
            if (e != null)
            {
                notifyListDataListeners_IntervalRemoved(index, index);
                notifyListListeners_ItemsRemoved(Arrays.asList(e));
            }
            
            return e;
        }
    }
    
    @Override
    public boolean remove(Object o)
    {
        synchronized (m_syncList)
        {
            final int index = m_syncList.indexOf(o);
            
            if (index <= -1)
            {
                return false;
            }
            
            final T e = m_syncList.get(index);
            final boolean result = m_syncList.remove(o);
            
            if (result)
            {
                notifyListDataListeners_IntervalRemoved(index, index);
                notifyListListeners_ItemsRemoved(Arrays.asList(e));
            }
            
            return result;
        }
    }
    
    @Override
    public boolean removeAll(Collection<?> c)
    {
        synchronized (m_syncList)
        {
            final int size = m_syncList.size();
            final ArrayList<T> items = new ArrayList<T>();
            for (final Object o : c)
            {
                items.add(m_syncList.get(m_syncList.indexOf(o)));
            }
            
            final boolean result = m_syncList.removeAll(c);
            
            if (result)
            {
                notifyListDataListeners_ContentsChanged(0, size);
                notifyListListeners_ItemsRemoved(items);
            }
            
            return result;
        }
    }
    
    @Override
    public void removeListDataListener(ListDataListener l)
    {
        m_listDataListeners.remove(l);
    }
    
    public void removeListListener(ListListener<T> l)
    {
        m_listListeners.remove(l);
    }
    
    @Override
    public boolean retainAll(Collection<?> c)
    {
        synchronized (m_syncList)
        {
            final int size = m_syncList.size();
            final ArrayList<T> items = new ArrayList<T>();
            for (final T t : m_syncList)
            {
                if (c.contains(t))
                {
                    items.add(t);
                }
            }
            
            final boolean result = m_syncList.retainAll(c);
            
            if (result)
            {
                notifyListDataListeners_ContentsChanged(0, size);
                notifyListListeners_ItemsRemoved(items);
            }
            
            return result;
        }
    }
    
    @Override
    public T set(int index, T element)
    {
        synchronized (m_syncList)
        {
            final T e = m_syncList.set(index, element);
            
            if (e != null)
            {
                notifyListDataListeners_ContentsChanged(index, index);
                notifyListListeners_ItemsChanged(Arrays.asList(e, element));
            }
            
            return e;
        }
    }
    
    @Override
    public int size()
    {
        return m_syncList.size();
    }
    
    @Override
    public List<T> subList(int fromIndex, int toIndex)
    {
        return m_syncList.subList(fromIndex, toIndex);
    }
    
    @Override
    public Object[] toArray()
    {
        return m_syncList.toArray();
    }
    
    @Override
    public <T> T[] toArray(T[] a)
    {
        return m_syncList.toArray(a);
    }
}
