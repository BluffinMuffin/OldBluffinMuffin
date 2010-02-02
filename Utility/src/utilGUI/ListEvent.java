package utilGUI;

import java.util.EventObject;
import java.util.List;

/**
 * @author Hocus
 *         This class is a generic EvenObject.
 *         It can tell if an item has changed, has been added or has been
 *         removed.
 */
public class ListEvent<T> extends EventObject
{
    private static final long serialVersionUID = 1127707896326247037L;
    
    /** Identifies one or more changes in the lists contents. */
    public static final int ITEMS_CHANGED = 0;
    /** Identifies the addition of one or more items to the list */
    public static final int ITEMS_ADDED = 1;
    /** Identifies the removal of one or more items from the list */
    public static final int ITEMS_REMOVED = 2;
    
    private final int type;
    private final List<T> m_items;
    
    /**
     * Constructs a ListEvent object. If index0 is >
     * index1, index0 and index1 will be swapped such that
     * index0 will always be <= index1.
     * 
     * @param source
     *            the source Object (typically <code>this</code>)
     * @param type
     *            an int specifying {@link #ITEMS_CHANGED}, {@link #ITEMS_ADDED} , or {@link #ITEMS_REMOVED}
     * @param index0
     *            one end of the new interval
     * @param index1
     *            the other end of the new interval
     */
    public ListEvent(Object source, int type, List<T> p_elements)
    {
        super(source);
        this.type = type;
        this.m_items = p_elements;
    }
    
    /**
     * Returns the modified items.
     * 
     * @return a list representing the items
     */
    public List<T> getItems()
    {
        return m_items;
    }
    
    /**
     * Returns the event type. The possible values are:
     * <ul>
     * <li> {@link #ITEMS_CHANGED}
     * <li> {@link #ITEMS_ADDED}
     * <li> {@link #ITEMS_REMOVED}
     * </ul>
     * 
     * @return an int representing the type value
     */
    public int getType()
    {
        return type;
    }
    
    /**
     * Returns a string representation of this ListDataEvent. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     * 
     * @since 1.4
     * @return a string representation of this ListDataEvent.
     */
    @Override
    public String toString()
    {
        return getClass().getName() + "[type=" + type + ",elements=" + m_items.toString() + "]";
    }
}
